package com.mdsujan.restPostgres.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import com.mdsujan.restPostgres.request.UpdateThresholdRequest;
import com.mdsujan.restPostgres.response.ThresholdResponse;
import com.mdsujan.restPostgres.service.ThresholdService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = ThresholdController.class)
@SpringBootTest(classes = ThresholdController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class ThresholdControllerTest {
    public static final String base_url = "/inventory/atpThresholds/";
    @Autowired
    private MockMvc mockMvc; // inject the mock mvc

    @Autowired
    private ThresholdController thresholdController;

    @MockBean
    private ThresholdService mockThresholdService;
    TestUtils testUtils = new TestUtils();

    ObjectMapper objectMapper = new ObjectMapper();


    @DisplayName("get all returns a list + test web layer")
    @Test
    public void test_getAllThresholds() throws Exception {

        // setup

        List<Threshold> testThresholdList = List.of(testUtils.getTestThreshold());
        List<ThresholdResponse> testThresholdResponseList = List.of(testUtils.getTestThresholdResponse());

        when(mockThresholdService.getAllThresholds()).thenReturn(testThresholdList);

        // execute
        String expectedJsonString = objectMapper.writeValueAsString(testThresholdResponseList);

        MvcResult mvcResult = mockMvc.perform(get(base_url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonString = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonString, actualJsonString);
    }

    @DisplayName("get the correct error code when api endpoint is wrong - 404")
    @Test
    public void test_getAllThresholds_wrong_endpoint() throws Exception {

        // setup, execute and assert
        MvcResult mvcResult = mockMvc.perform(get("/wrongEndpoint"))
                .andExpect(status().isNotFound()) // error 404
                .andReturn();

    }

    @DisplayName("getThreshold() - get threshold by id when id is valid - returns an threshold as response")
    @Test
    public void testGetThresholdById() throws Throwable {
        // setup
        Threshold testThreshold = testUtils.getTestThreshold();
        ThresholdResponse testThresholdResponse = testUtils.getTestThresholdResponse();
        Long testId = 1L;

        // stub
        Mockito.when(mockThresholdService.getThreshold(testId)).thenReturn(testThreshold);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testThresholdResponse);

        MvcResult mvcResult = mockMvc.perform(get(base_url + testId))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);


    }

    @DisplayName("createThreshold returns the thresholdCreated")
    @Test
    public void test_createThreshold() throws Throwable {

        // setup
        // for mock service
        Threshold testThreshold = testUtils.getTestThreshold();
        // controller takes CreateThresholdRequest obj as RequestBody
        CreateThresholdRequest testCreateThresholdRequest = testUtils.getTestCreateThresholdRequest();
        // controller returns the created threshold as a ThresholdResponse obj
        ThresholdResponse testThresholdResponse = testUtils.getTestThresholdResponse();

        // stub
        Mockito.when(mockThresholdService.createThreshold(any(CreateThresholdRequest.class)))
                .thenReturn(testThreshold);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testThresholdResponse);

        MvcResult mvcResult = mockMvc.perform(post(base_url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateThresholdRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("createThresholds (batch) returns the thresholds created as a list")
    @Test
    public void test_createThresholds() throws Throwable {

        // setup
        List<ThresholdResponse> testThresholdList = List.of(testUtils.getTestThresholdResponse());

        // stub
        Mockito.when(mockThresholdService.createThresholds(anyList())).thenReturn(testThresholdList);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testThresholdList);

        MvcResult mvcResult = mockMvc.perform(post(base_url + "/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJsonResponse))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("delete an threshold with correct threshold id - status ok is received")
    @Test
    public void testDeleteThreshold_valid_id() throws Throwable {
        // setup
        Long testId = 1L;
        // stub
        Mockito.when(mockThresholdService.deleteThresholdById(testId)).thenReturn(anyString());

        // execute and assert
        MvcResult mvcResult = mockMvc.perform(delete(base_url + "/" + testId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("delete an threshold with correct threshold id - status ok is received")
    @Test
    public void testDeleteThreshold_invalid_id() throws Throwable {
        // setup
        String invalidIdString = "123abc";

        // execute and assert
        MvcResult mvcResultString = mockMvc.perform(delete(base_url + "/" + invalidIdString))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @DisplayName("updateThreshold_put with valid thresholdId in body & request param - should return updated threshold")
    @Test
    public void test_updateThreshold_put_valid_inputs() throws Throwable {
        // setup
        Long testId = 1L;
        // for mock service - method returns the updated Threshold obj
        Threshold testUpdatedThresholdPut = testUtils.getTestUpdatedThreshold();
        // for controller - method takes an UpdateThresholdRequest obj
        UpdateThresholdRequest testUpdateThresholdRequestPut = testUtils.getTestUpdateThresholdRequestPut();
        // for controller - returns a ThresholdResponse obj of the updated Threshold
        ThresholdResponse testUpdatedThresholdResponse = testUtils.getTestUpdatedThresholdResponse();

        // stub
        when(mockThresholdService.updateThresholdPut(any(Long.class), any(UpdateThresholdRequest.class)))
                .thenReturn(testUpdatedThresholdPut);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testUpdatedThresholdResponse);
        System.out.println(testUpdateThresholdRequestPut);
        MvcResult mvcResult = mockMvc.perform(put(base_url + testId)
                        .content(objectMapper.writeValueAsString(testUpdateThresholdRequestPut))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("updateThreshold_put with invalid thresholdId in body & request param - should return status as bad request")
    @Test
    public void updateThreshold_put_invalid_inputs() throws Throwable {
        // setup
        String wrongId = "123WrongId";
        // for mock service - method returns the updated Threshold obj
        Threshold testUpdatedThresholdPut = testUtils.getTestUpdatedThreshold();
        // for controller - method takes an UpdateThresholdRequest obj
        // has a different ID
        UpdateThresholdRequest testUpdateThresholdRequestPut = testUtils.getTestUpdateThresholdRequestPut();

//        // stub
//        when(mockThresholdService.updateThresholdPut(any(Long.class), any(UpdateThresholdRequest.class)))
//                .thenReturn(testUpdatedThresholdPut);

        // execute
        mockMvc.perform(put(base_url + wrongId)
                        .content(objectMapper.writeValueAsString(testUpdateThresholdRequestPut))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // since the IDs don't match
                .andReturn();

    }

    @DisplayName("updateThreshold_patch with valid thresholdId in body & request param - should return updated threshold")
    @Test
    public void updateThreshold_patch_valid_inputs() throws Throwable {
        // setup
        Long testId = 1L;
        // for mock service - method returns the updated Threshold obj
        Threshold testUpdatedThresholdPatch = testUtils.getTestUpdatedThreshold();
        // for controller - method takes an UpdateThresholdRequest obj
        UpdateThresholdRequest testUpdateThresholdRequestPatch = testUtils.getTestUpdateThresholdRequestPatch();
        // for controller - returns a ThresholdResponse obj of the updated Threshold
        ThresholdResponse testUpdatedThresholdResponse = testUtils.getTestUpdatedThresholdResponse();

        // stub
        when(mockThresholdService.updateThresholdPatch(any(Long.class), any(UpdateThresholdRequest.class)))
                .thenReturn(testUpdatedThresholdPatch);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testUpdatedThresholdResponse);

        MvcResult mvcResult = mockMvc.perform(patch(base_url + testId)
                        .content(objectMapper.writeValueAsString(testUpdateThresholdRequestPatch))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("updateThreshold_put with invalid thresholdId in body & request param - should return status as bad request")
    @Test
    public void updateThreshold_patch_invalid_inputs() throws Throwable {
        // setup
        String wrongId = "123WrongId";
        // for mock service - method returns the updated Threshold obj
        Threshold testUpdatedThresholdPut = testUtils.getTestUpdatedThreshold();
        // for controller - method takes an UpdateThresholdRequest obj
        // has a different ID
        UpdateThresholdRequest testUpdateThresholdRequestPut = testUtils.getTestUpdateThresholdRequestPut();

//        // stub
//        when(mockThresholdService.updateThresholdPut(any(Long.class), any(UpdateThresholdRequest.class)))
//                .thenReturn(testUpdatedThresholdPut);

        // execute
        mockMvc.perform(put(base_url + wrongId)
                        .content(objectMapper.writeValueAsString(testUpdateThresholdRequestPut))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // since the IDs don't match
                .andReturn();
    }
}
