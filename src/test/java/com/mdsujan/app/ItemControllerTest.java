package com.mdsujan.app;

import com.mdsujan.restPostgres.app.MyApp;
import com.mdsujan.restPostgres.controller.ItemController;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.service.ItemService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestPropertySource("/application.properties")
//@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = MyApp.class)
public class ItemControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc; // our mock

    //    @Mock
    @MockBean
    private ItemService itemService;


    @BeforeEach
    public void addTestDbData() {
//        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.execute("insert into items " +
                "values(123456, 'testDesc', 'testCategory', 'testType'," +
                " 'testStatus', 99.99, false, false, false)");
    }

    @AfterEach
    public void cleanTestDbData() {
        jdbcTemplate.execute("delete from items where itemId=123456");
    }

    @Test
    public void getItemHttpRequestTest() throws Throwable {
        // perform the web request
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/items/123456"))
                // define the expectations
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView(); // get the result model and view

//        assert mav != null;
        ModelAndViewAssert.assertViewName(mav, itemService.getAllItems().toString());
    }
}
