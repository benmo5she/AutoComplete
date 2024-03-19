package com.bottomline.auto.complete.data;

import java.util.List;

import com.bottomline.auto.complete.model.Node;

/*
 * Used when attempting to match strings by the implementation criteria
 */
public interface StringsMatcher {
	
	List<String> matchingWords(Node root, String word);
	
}
