package com.mdsujan.restPostgres.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.service.ItemService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = ItemController.class)
@SpringBootTest(classes = ItemController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc; // inject the mock mvc

    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService mockItemService;
    TestUtils testUtils = new TestUtils();

    @DisplayName("get all returns a list + test web layer")
    @Test
    public void test_getAllItems() throws Exception {

        // setup
        List<Item> testItemList = List.of(testUtils.getTestItem());
        when(mockItemService.getAllItems()).thenReturn(testItemList);

        // execute
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJsonString = objectMapper.writeValueAsString(testItemList);

        MvcResult mvcResult = mockMvc.perform(get("/inventory/items/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonString = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonString, actualJsonString);
    }

    @DisplayName("get the correct error code when api endpoint is wrong - 404")
    @Test
    public void test_getAllItems_wrong_endpoint() throws Exception {

        // setup, execute and assert
        MvcResult mvcResult = mockMvc.perform(get("/wrongEndpoint")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // error 404
                .andReturn();

    }

    @DisplayName("createItem returns the itemCreated")
    @Test
    public void test_createItem() throws Throwable {

        // setup
        ObjectMapper objectMapper = new ObjectMapper();

        Item testItem = testUtils.getTestItem();

        // stub
        Mockito.when(mockItemService.createItem(any(Item.class))).thenReturn(testItem);
        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testItem);

        MvcResult mvcResult = mockMvc.perform(post("/inventory/items/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJsonResponse).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }
}
