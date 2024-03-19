package com.bottomline.auto.complete.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.bottomline.auto.complete.data.NamesMatcher;
import com.bottomline.auto.complete.model.Name;
import com.bottomline.auto.complete.model.Node;
import com.bottomline.auto.complete.repository.NameRepository;

/*
 * This service provides functionality for module of auto-completion of words using pre define data structure.
 */
@Service
public class AutoCompleteService extends BaseService
{
    private final NameRepository repository;
    private final Node root = new Node();
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public AutoCompleteService(NameRepository nameRepository) {
    	repository = nameRepository;
    }

    /*
     * This function will return all names that start with the given prefix.
     * Since the data was stored in the data structure in lower case,
     * It will search the DB for the matching name that contains the original characters.
     */
    public List<String> autoCompleteSearch(String prefix)
    {
        List<String> result = null;        
        NamesMatcher matcher = new NamesMatcher();
        //Make sure we work only with alpha characters
        if(!isValidWord(prefix)) {
        	logger.info("Attempt to auto complete invalid word with prefix: " + prefix);
        	return null;
        }
        try {        	
            List<String> completeWords = matcher.matchingWords(root, prefix);
            if(!CollectionUtils.isEmpty(completeWords))
            {
                result = new ArrayList<>();
                for(String word : completeWords)
                {
                    //Find the word from the data structure to find the original word.
                    Name wordInDB = repository.findByNameIgnoreCase(word);
                    if(wordInDB != null && StringUtils.isNotBlank(wordInDB.getName()))
                    {
                        result.add(wordInDB.getName());
                    }
                }
            }	
        }
        catch (Exception ex) {
        	logger.info("Failed to handle AutoComplete request, check error logs for further details");
        	throw ex; //Let the REST error handler continue with handling the response
        }
        return result;
    }

    /*
     * This function will add a new word to the data structure.
     * It uses Nodes that contain a character, and array of next characters possible that represents the word.
     * @Param word - String to be added to the data structure
     */
    public void addNewWordToDS(String word)
    {
        if(!isValidWord(word)) {
        	logger.info("attempt to insert invalid word to memory: " + word);
        	return;
        }
    	Node currentNode = root;
    	try {
            //If the prefix is empty or not alphabetic, reject and return;
            if(!StringUtils.isAlpha(word))
            {
                logger.error("Invalid word was rejected to be inserted to DS: " + word);
                currentNode.setWordComplete(true);
                return;
            }
            /*
                Traverse the word characters and attempt to find matching Node.
                If character was not found it that a new character should be added to the data structure (create new node for it)
             */
            for(int i = 0; i <= word.length() -1; i++)
            {
                Node newChar;
                int nextCharIndex = Character.toLowerCase(word.charAt(i)) - 'a';
                Node[] nextChars = currentNode.getNextChars();

                if(nextChars[nextCharIndex] == null)
                {
                    newChar = new Node(Character.toLowerCase(word.charAt(i)));
                    nextChars[nextCharIndex] = newChar;
                }
                else
                {
                    newChar = nextChars[nextCharIndex];
                }
                currentNode = newChar;
            }
            currentNode.setWordComplete(true);
    	} catch (Exception ex) {
    		logger.info("Encountered an error when trying to add the word: " + word + " to the memory, check error logs for further details", ex);
    		throw ex;
    	}
    }

    public List<String> getAll()
    {
        return findAllNames();
    }


    /*
    Get all the names from the database.
    */
    //Optimization note: Possible to optimize with spring caching, but we do not know how often the DB and DS are expected to change.
    //Considering this is the case, we will provide current data from the database.
    private List<String> findAllNames()
    {
        Assert.notNull(repository, "Repository was not initialized");
        return repository.findAll().stream().map(Name::getName).toList();
    }
    
    private boolean isValidWord(String word) {
    	return !StringUtils.isBlank(word) && StringUtils.isAlpha(word);
    }

	public Node getRoot() {
		return root;
	}
}