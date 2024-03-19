package com.bottomline.auto.complete.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

public class ResourceUtils {
	
    public static List<String> readResourceTextLines(ResourceLoader resourceLoader, String resourcePath) throws IOException {
        //load local resource as data source
    	ClassPathResource resource = new ClassPathResource(resourcePath);
        List<String> result = null;
        if(resource != null && resource.exists())
        {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            	result = reader.lines().collect(Collectors.toList());
            }
        }
        return result;
    }

}
