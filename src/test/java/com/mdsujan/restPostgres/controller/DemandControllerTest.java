package com.mdsujan.restPostgres.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.UpdateDemandRequest;
import com.mdsujan.restPostgres.response.DemandResponse;
import com.mdsujan.restPostgres.service.DemandService;
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
@SpringBootTest(classes = DemandController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class DemandControllerTest {
    public static final String base_url = "/inventory/demand/";
    @Autowired
    private MockMvc mockMvc; // inject the mock mvc

    @Autowired
    private DemandController demandController;

    @MockBean
    private DemandService mockDemandService;
    TestUtils testUtils = new TestUtils();

    ObjectMapper objectMapper = new ObjectMapper();

    //
    @DisplayName("get all demand - returns a list of demand test web layer")
    @Test
    public void test_getAllDemands() throws Exception {

        // stub
        when(mockDemandService.getAll()).thenReturn(List.of(testUtils.getTestDemand()));

        // execute
        String expectedJsonString = objectMapper.writeValueAsString(List.of(testUtils.getTestDemandResponse()));

        MvcResult mvcResult = mockMvc.perform(get(base_url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonString = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonString, actualJsonString);
    }

    @DisplayName("get the correct error code when api endpoint is wrong - 404")
    @Test
    public void test_getAllDemands_wrong_endpoint() throws Exception {

        // setup, execute and assert
        mockMvc.perform(get("/wrongEndpoint"))
                .andExpect(status().isNotFound()) // error 404
                .andReturn();

    }

    @DisplayName("getDemand() - get demand by id when id is valid - returns an demand as response")
    @Test
    public void testGetDemandById() throws Throwable {
        // setup
        Long testId = 1L;

        // stub
        Mockito.when(mockDemandService.getDemandById(testId))
                .thenReturn(testUtils.getTestDemand());

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(
                testUtils.getTestDemandResponse());

        MvcResult mvcResult = mockMvc.perform(get(base_url + testId))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("createDemand returns the demandCreated")
    @Test
    public void test_createDemand() throws Throwable {
        // stub
        Mockito.when(mockDemandService.createNewDemand(any(CreateDemandRequest.class)))
                .thenReturn(testUtils.getTestDemand());

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testUtils.getTestDemandResponse());
        MvcResult mvcResult = mockMvc.perform(post(base_url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUtils.getCreateDemandRequest())))
                .andExpect(status().isOk())
                .andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("createDemands (batch) returns the demand created as a list")
    @Test
    public void test_createDemands() throws Throwable {

        // setup
        List<CreateDemandRequest> testCreateDemandRequestList = List.of(
                testUtils.getCreateDemandRequest());

        List<DemandResponse> testDemandResponseList = List.of(
                testUtils.getTestDemandResponse());

        // stub
        Mockito.when(mockDemandService.createNewDemands(anyList()))
                .thenReturn(testDemandResponseList);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testDemandResponseList);

        MvcResult mvcResult = mockMvc.perform(post(base_url + "/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateDemandRequestList)))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("delete an demand with correct demand id - status ok is received")
    @Test
    public void testDeleteDemand_valid_id() throws Throwable {
        // setup
        Long testId = 1L;
        // stub
        Mockito.when(mockDemandService.deleteDemand(testId)).thenReturn(anyString());

        // execute and assert
        mockMvc.perform(delete(base_url + "/" + testId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("delete an demand with invalid demand id - status bad request is received")
    @Test
    public void testDeleteDemand_invalid_id() throws Throwable {
        // setup
        String invalidIdString = "123abc";

        // execute and assert
        mockMvc.perform(delete(base_url + "/" + invalidIdString))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @DisplayName("updateDemand_put with valid demandId in body & request param - should return updated demand")
    @Test
    public void test_updateDemand_put_valid_inputs() throws Throwable {
        // setup
        Long testId = 1L;
        UpdateDemandRequest testUpdateDemandRequest = testUtils.getTestUpdateDemandRequestPut();
        Demand testUpdatedDemand = testUtils.getTestDemand();
        DemandResponse testDemandResponse = testUtils.getTestDemandResponse();

        // stub
        when(mockDemandService.updateDemandPut(any(Long.class), any(UpdateDemandRequest.class)))
                .thenReturn(testUpdatedDemand);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testDemandResponse);

        MvcResult mvcResult = mockMvc.perform(put(base_url + testId)
                        .content(objectMapper.writeValueAsString(testUpdateDemandRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("updateDemand_put with invalid demandId in body & request param - should return status as bad request")
    @Test
    public void updateDemand_put_invalid_inputs() throws Throwable {
        // setup
        String testId = "abc123";

        Demand testDemand = testUtils.getTestDemand();

        // stub
        when(mockDemandService.updateDemandPut(any(Long.class), any(UpdateDemandRequest.class)))
                .thenReturn(testDemand);

        // execute and assertion
        mockMvc.perform(put(base_url + testId)
                        .content(objectMapper.writeValueAsString(testDemand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @DisplayName("updateDemand_patch with valid demandId in body & request param - should return updated demand")
    @Test
    public void updateDemand_patch_valid_inputs() throws Throwable {
        // setup
        Long testId = 1L;
        // UpdateDemandRequest obj - for request body json
        UpdateDemandRequest testDemandPatchUpdateDemandRequest = testUtils.getTestUpdateDemandRequestPatch();
        // updated demand obj - for service mock
        Demand mockUpdatedDemand = testUtils.getTestDemandUpdatedPatch();
        // DemandResponse obj - for response body json
        DemandResponse testDemandResponsePatchUpdated = testUtils.getTestDemandResponseUpdatedPatch();

        // stub
        when(mockDemandService.updateDemandPatch(any(Long.class), any(UpdateDemandRequest.class)))
                .thenReturn(mockUpdatedDemand);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testDemandResponsePatchUpdated);

        MvcResult mvcResult = mockMvc.perform(patch(base_url + testId)
                        .content(objectMapper.writeValueAsString(testDemandPatchUpdateDemandRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("updateDemand_put with invalid demandId in body & request param - should return status as bad request")
    @Test
    public void updateDemand_patch_invalid_inputs() throws Throwable {
        // setup
        String testId = "abc123";

        Demand mockUpdatedDemand = testUtils.getTestDemandUpdatedPatch(); // demand after updated by patch

        // stub
        when(mockDemandService.updateDemandPatch(any(Long.class), any(UpdateDemandRequest.class)))
                .thenReturn(mockUpdatedDemand);

        // execute
        MvcResult mvcResult = mockMvc.perform(patch(base_url + testId)
                        .content(objectMapper.writeValueAsString(mockUpdatedDemand))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
