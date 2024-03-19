package com.bottomline.auto.complete.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bottomline.auto.complete.model.Name;
import com.bottomline.auto.complete.repository.NameRepository;
import com.bottomline.auto.complete.service.AutoCompleteService;
import com.bottomline.auto.complete.utils.ResourceUtils;


/*
This configuration class is responsible for loading the data from the
data source defined in application configuration
if the data source was not configured, the class will use the default datasource
 */
@Configuration
@EnableWebMvc
public class AppConfiguration {
	
	private final ResourceLoader resourceLoader;

    private final NameRepository repository;

    private final AutoCompleteService service;

    @Value("${store.file.path:}")
    String storeFilePath;
    
    final String NAMES_LIST_FILE_PATH = "static/BoyNames.txt";
    
    private static final Logger logger = LoggerFactory.getLogger(AppConfiguration.class);
    
    public AppConfiguration(ResourceLoader resourceLoader, NameRepository nameRepository, AutoCompleteService autoCompleteService) {
    	this.resourceLoader = resourceLoader;
    	this.repository = nameRepository;
    	this.service = autoCompleteService;
    }

    @PostConstruct
    private void init() throws IOException
    {
    	try {
    		fillDS();	
    	} catch (Exception ex) {
    		logger.error("Encountered an error while loading data to configuration, "
    				+ "will operate with empty data", ex);
    	}
        
    }

    /**
     * This function loads the names from a file into the data structure.
     * The file path is obtained from the configuration file. If the file path is not specified,
     * the default file path is used.
     * The function reads the file line by line and adds each name to the data structure.
     * If the data structure is not empty, the function also saves the names to the database.
     * @throws IOException if there is an error reading the file
     */
    private void fillDS() throws IOException {
        List<String> namesFromFile=null;
        File fileOfNames = null;
        //No file path specified, use a default file path
        if(Strings.isBlank(storeFilePath))
        {
        	namesFromFile = ResourceUtils.readResourceTextLines(resourceLoader, NAMES_LIST_FILE_PATH);
        }
        //Datasource path is defined in properties
        else
        {
           fileOfNames = new File(storeFilePath);
           if(fileOfNames != null)
                namesFromFile = Files.readAllLines(fileOfNames.toPath());
        }
        //If the file reading was successful, fill the data structure with the words found.
        if(!CollectionUtils.isEmpty(namesFromFile))
        {
            for(String name : namesFromFile)
            {
                service.addNewWordToDS(name);
            }
            fillDB(namesFromFile);
        }
    }


    //Fill the DB configured to include the names from the file
    private void fillDB(List<String> names) {
        if(!CollectionUtils.isEmpty(names))
        {
            List<Name> nameObjects = names.stream()
                    .map(Name::new).collect(Collectors.toList());
            repository.saveAll(nameObjects);
        }
    }

}
