package com.bottomline.auto.complete.service;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bottomline.auto.complete.AutoCompleteApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AutoCompleteApplication.class)
public class AutoCompleteServiceIntegrationTest {

    @Autowired
    private AutoCompleteService service;

    @Test
    public void testAutoComplete()
    {
        List<String> namesFromDB = service.getAll();
        assertNotNull(namesFromDB);
        String firstName = namesFromDB.get(0);
        assertNotNull(firstName);
        char firstChar = firstName.charAt(0);
        List<String> autoCompleteNames = service.autoCompleteSearch("" + firstChar);
        assertNotNull(autoCompleteNames);
        assertTrue("DB is not empty", autoCompleteNames.size() > 0);
    }
}
