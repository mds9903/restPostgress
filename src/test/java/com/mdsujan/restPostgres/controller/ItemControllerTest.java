package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.service.ItemService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = ItemController.class)
@SpringBootTest(classes = ItemController.class)
@AutoConfigureMockMvc
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc; // inject the mock mvc
    @MockBean
    private ItemService mockItemService;
    TestUtils testUtils = new TestUtils();

//    @DisplayName("testing getAll items to return a list of items object")
//    @Test
//    public void testGetAllItems_returns_a_list() throws Exception {
//        // setup
//        List<Item> actualResult = testUtils.getItemsList();
//
//        // mock the service response
//        when(mockItemService.getAllItems()).thenReturn(actualResult);
//
//        // execute
//        List<Item> expected = itemController.getAllItems();
////
//        // assert
//        assertThat(expected.size()).isEqualTo(actualResult.size());
//    }

    @DisplayName("get all returns a list + test web layer")
    @Test
    public void test_getAllItems() throws Exception {

        // setup
        List<Item> testData = testUtils.getItemsList();

        // mock
        when(mockItemService.getAllItems()).thenReturn(testData);

        // invoke controller
        mockMvc.perform(get("/inventory/items/"))
                .andExpect(status().isOk())
                .andReturn();

        // verify service method was called
        verify(mockItemService, times(1)).getAllItems();
    }


}
