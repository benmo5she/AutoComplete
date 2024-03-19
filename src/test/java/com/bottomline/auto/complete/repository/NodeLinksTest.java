package com.bottomline.auto.complete.repository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bottomline.auto.complete.data.NamesMatcher;
import com.bottomline.auto.complete.model.Node;

import java.util.List;

@SpringBootTest
public class NodeLinksTest {

    @Test
    public void testNodeAutoComplete()
    {
        Node startNode = new Node();
        Node currentNode = startNode;
        Node[] nextChars = currentNode.getNextChars();
        nextChars[0] = new Node('a');
        currentNode = nextChars[0];
        nextChars = currentNode.getNextChars();
        currentNode.getNextChars()[1] = new Node('B'); //Check lower case conversion
        currentNode = nextChars[1];
        nextChars = currentNode.getNextChars();
        nextChars[2] = new Node('c');
        nextChars[2].setWordComplete(true);
        NamesMatcher matcher = new NamesMatcher();
        List<String> autoComplete = matcher.matchingWords(startNode, "a");//we expect 3 links (3 char word)
        Assert.assertTrue(autoComplete != null && autoComplete.size() == 1);
        Assert.assertTrue(autoComplete.contains("abc"));

    }

}
