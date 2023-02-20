package com.mdsujan.restPostgres.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.response.AvailabilityV1Response;
import com.mdsujan.restPostgres.response.AvailabilityV2Response;
import com.mdsujan.restPostgres.response.AvailabilityV3Response;
import com.mdsujan.restPostgres.service.AvailabilityService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvailabilityController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class AvailabilityControllerTest {
    public static final String base_url = "/inventory";
    @Autowired
    private MockMvc mockMvc; // inject the mock mvc

    @Autowired
    private AvailabilityController availabilityController;

    @MockBean
    private AvailabilityService availabilityService;
    TestUtils testUtils = new TestUtils();

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("test get availability quantity by item and location IDs")
    @Test
    public void testGetAvbQtyByItemAndLocationV1() throws Throwable {

        // setup
        Long testItemId = 1L;
        Long testLocationId = 1L;
        AvailabilityV1Response testAvbResponseV1 = testUtils.getTestAvailabilityV1Response();
        // stub
        Mockito.when(availabilityService.getAvlQtyByItemAndLocationV1(testItemId, testLocationId))
                .thenReturn(testAvbResponseV1);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testAvbResponseV1);

        MvcResult mvcResult = mockMvc.perform(get(base_url
                        + "/v1" + "/availability" + "/" + testItemId + "/" + testLocationId))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertion
        assertEquals(expectedJsonResponse, actualJsonResponse);

    }

    @DisplayName("test get availability quantity of all locations")
    @Test
    public void testGetAllLocationsAvbQtyV1() throws Throwable {
        // setup
        Long testItemId = 1L;
        AvailabilityV1Response testAvbResponseV1 = testUtils.getTestAvailabilityV1Response();
        // stub
        Mockito.when(availabilityService.getAvlQtyByItemV1(testItemId))
                .thenReturn(testAvbResponseV1);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testAvbResponseV1);

        MvcResult mvcResult = mockMvc.perform(get(base_url
                        + "/v1" + "/availability" + "/" + testItemId))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertion
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("test get availability by item and location IDs")
    @Test
    public void testGetAvbStockLevelV2() throws Throwable {

        // setup
        Long testItemId = 1L;
        Long testLocationId = 1L;
        AvailabilityV2Response testAvbResponseV2 = testUtils.getTestAvailabilityV2Response();
        // stub
        Mockito.when(availabilityService.getStockLevelV2(testItemId, testLocationId))
                .thenReturn(testAvbResponseV2);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testAvbResponseV2);

        MvcResult mvcResult = mockMvc.perform(get(base_url
                        + "/v2" + "/availability" + "/" + testItemId + "/" + testLocationId))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertion
        assertEquals(expectedJsonResponse, actualJsonResponse);

    }

    @DisplayName("test get availability by item and location IDs")
    @Test
    public void testGetAvbStockLevelV3() throws Throwable {

        // setup
        Long testItemId = 1L;
        Long testLocationId = 1L;
        AvailabilityV3Response testAvbResponseV3 = testUtils.getTestAvailabilityV3Response();
        // stub
        Mockito.when(availabilityService.getStockLevelV3(testItemId, testLocationId))
                .thenReturn(testAvbResponseV3);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testAvbResponseV3);

        MvcResult mvcResult = mockMvc.perform(get(base_url
                        + "/v3" + "/availability" + "/" + testItemId + "/" + testLocationId))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertion
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }


}
