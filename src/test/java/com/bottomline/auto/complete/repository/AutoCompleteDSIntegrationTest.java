package com.bottomline.auto.complete.repository;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.util.List;
import java.util.Objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.bottomline.auto.complete.AutoCompleteApplication;
import com.bottomline.auto.complete.model.Node;
import com.bottomline.auto.complete.service.AutoCompleteService;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AutoCompleteApplication.class)
public class AutoCompleteDSIntegrationTest {

    @Autowired
    private AutoCompleteService service;

    @Test
    public void testIfDSIsNotEmpty() {
        Node root = service.getRoot();
        assertNotNull(root);
        Node[] nextChars = root.getNextChars();
        assertNotNull(nextChars);
        List<?> nextCharsList = CollectionUtils.arrayToList(nextChars);
        assertNotNull(nextCharsList);
        nextCharsList.stream().noneMatch(Objects::nonNull);
        assertTrue("DS is not empty", nextCharsList.size() > 0);
    }

}
