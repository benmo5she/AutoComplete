package com.bottomline.auto.complete.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bottomline.auto.complete.service.AutoCompleteService;

@Validated
@RestController
@RequestMapping("/find")
public class AutoCompleteController {

    private final AutoCompleteService autoCompleteService;
    private final List<String> NO_RESULTS_FOUND = Arrays.asList("No results found for the requested query");
    
    public AutoCompleteController(AutoCompleteService autoCompleteService) {
    	this.autoCompleteService = autoCompleteService;
    }

    /*
     * This function returns all the names in the data base.
     */
    @GetMapping("/all")
    public List<String> getAllNames() {
        return autoCompleteService.getAll();
    }

    /*
     * This function will return all the names that starts with the given prefix.
     * @Parameter prefix - string to used for the search of matching names
     */
    @GetMapping("/auto/complete/{prefix}")
    public List<String> getNames(@PathVariable @Pattern(regexp = "^[a-zA-Z]+$", message="Prefix must be alphabetic word") String prefix)
    {
    	List<String> result = autoCompleteService.autoCompleteSearch(prefix);
        return CollectionUtils.isEmpty(result) ? NO_RESULTS_FOUND : result;
    }

}
