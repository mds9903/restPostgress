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
import static org.mockito.ArgumentMatchers.anyList;
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
    public static final String base_url = "/inventory/items/";
    @Autowired
    private MockMvc mockMvc; // inject the mock mvc

    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService mockItemService;
    TestUtils testUtils = new TestUtils();

    ObjectMapper objectMapper = new ObjectMapper();


    @DisplayName("get all returns a list + test web layer")
    @Test
    public void test_getAllItems() throws Exception {

        // setup
        List<Item> testItemList = List.of(testUtils.getTestItem());
        when(mockItemService.getAllItems()).thenReturn(testItemList);

        // execute
        String expectedJsonString = objectMapper.writeValueAsString(testItemList);

        MvcResult mvcResult = mockMvc.perform(get(base_url)
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

    @DisplayName("getItem() - get item by id when id is valid - returns an item as response")
    @Test
    public void testGetItemById() throws Throwable {
        // setup
        Item testItem = testUtils.getTestItem();
        Long testId = 1L;

        // stub
        Mockito.when(mockItemService.getItemById(testId)).thenReturn(testItem);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testItem);

        MvcResult mvcResult = mockMvc.perform(get(base_url + testId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);


    }

    @DisplayName("createItem returns the itemCreated")
    @Test
    public void test_createItem() throws Throwable {

        // setup
        Item testItem = testUtils.getTestItem();

        // stub
        Mockito.when(mockItemService.createItem(any(Item.class))).thenReturn(testItem);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testItem);

        MvcResult mvcResult = mockMvc.perform(post(base_url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJsonResponse).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("createItems (batch) returns the items created as a list")
    @Test
    public void test_createItems() throws Throwable {

        // setup
        List<Item> testItemList = List.of(testUtils.getTestItem());

        // stub
        Mockito.when(mockItemService.createItems(anyList())).thenReturn(testItemList);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testItemList);

        MvcResult mvcResult = mockMvc.perform(post(base_url + "/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJsonResponse).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }


}
