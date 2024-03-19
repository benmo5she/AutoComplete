package com.bottomline.auto.complete.data;

import java.util.ArrayList;
import java.util.List;

import com.bottomline.auto.complete.model.Node;

public class NamesMatcher implements StringsMatcher {

	@Override
	public List<String> matchingWords(Node root, String word) {
		if(root == null || word == null) {
			return null;
		}
		return namesStartWith(root, word);
	}
	

    /*
        This function will attempt to traverse the data structure and find all matching words that start with prefix argument.
        This function is based on parameter node representing the current character in the word
     */
    private List<String> namesStartWith(Node root, String prefixWord)
    {
        List<String> result = new ArrayList<>();
        Node currentNode = root;
        for(char currentChar : prefixWord.toCharArray())
        {
            //Change to a lower case as currently they are the only characters supported in Node class.
            int charIndex = Character.toLowerCase(currentChar) - 'a';
            Node[] remainder = currentNode.getNextChars();
            if(remainder[charIndex] == null)
            {
                return result;
            }
            currentNode = remainder[charIndex];
        }
        //add the rest of the words that match the prefix to the result list
        findWordsByPrefix(currentNode, prefixWord, result);
        return result;
    }

    /*
     * This function will traverse the data structure in the attempt to find all matching words that start with prefix argument.
     * @Parameter prefix - string to used for the search of matching words
     * @Parameter currentNode - current node in the data structure to traverse
     * @Parameter result - list to store all the matching words
     */
    private void findWordsByPrefix(Node currentNode, String prefix, List<String> result) {
        if (currentNode.isWordComplete()) {
            result.add(prefix);
        }
        
        Node[] linkedWords = currentNode.getNextChars();
        for (char currentChar = 'a'; currentChar <= 'z'; currentChar++) {
            int index = Character.toLowerCase(currentChar) - 'a';
            if (linkedWords[index] != null)
            {
                findWordsByPrefix(linkedWords[index], prefix + currentChar, result);
            }
        }
    }

}
