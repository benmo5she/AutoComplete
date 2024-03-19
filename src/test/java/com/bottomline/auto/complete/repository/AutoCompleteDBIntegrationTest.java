package com.bottomline.auto.complete.repository;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bottomline.auto.complete.AutoCompleteApplication;
import com.bottomline.auto.complete.service.AutoCompleteService;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AutoCompleteApplication.class)
public class AutoCompleteDBIntegrationTest {

	@Autowired
    NameRepository repository;

    @Test
    public void testIfDBIsNotEmpty() {
        AutoCompleteService autoCompleteService = new AutoCompleteService(repository);
        List<String> namesFromDB = autoCompleteService.getAll();
        assertNotNull(namesFromDB);
        String namesString = String.join(", ", namesFromDB);
        assertTrue("Auto Complete success with names: " + namesString, namesFromDB.size() > 0);
    }


}
