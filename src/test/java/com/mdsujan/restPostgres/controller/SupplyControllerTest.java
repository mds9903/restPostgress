package com.mdsujan.restPostgres.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.SupplyService;
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
@SpringBootTest(classes = SupplyController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class SupplyControllerTest {
    public static final String base_url = "/inventory/supply/";
    @Autowired
    private MockMvc mockMvc; // inject the mock mvc

    @Autowired
    private SupplyController supplyController;

    @MockBean
    private SupplyService mockSupplyService;
    TestUtils testUtils = new TestUtils();

    ObjectMapper objectMapper = new ObjectMapper();

    //
    @DisplayName("get all supply - returns a list of supply test web layer")
    @Test
    public void test_getAllSupplies() throws Exception {

        // stub
        when(mockSupplyService.getAllSupplies()).thenReturn(List.of(testUtils.getTestSupply()));

        // execute
        String expectedJsonString = objectMapper.writeValueAsString(List.of(testUtils.getTestSupplyResponse()));

        MvcResult mvcResult = mockMvc.perform(get(base_url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonString = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonString, actualJsonString);
    }

    @DisplayName("get the correct error code when api endpoint is wrong - 404")
    @Test
    public void test_getAllSupplies_wrong_endpoint() throws Exception {

        // setup, execute and assert
        mockMvc.perform(get("/wrongEndpoint"))
                .andExpect(status().isNotFound()) // error 404
                .andReturn();

    }

    @DisplayName("getSupply() - get supply by id when id is valid - returns an supply as response")
    @Test
    public void testGetSupplyById() throws Throwable {
        // setup
        Long testId = 1L;

        // stub
        Mockito.when(mockSupplyService.getSupplyById(testId))
                .thenReturn(testUtils.getTestSupply());

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(
                testUtils.getTestSupplyResponse());

        MvcResult mvcResult = mockMvc.perform(get(base_url + testId))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("createSupply returns the supplyCreated")
    @Test
    public void test_createSupply() throws Throwable {
        // stub
        Mockito.when(mockSupplyService.createNewSupply(any(CreateSupplyRequest.class)))
                .thenReturn(testUtils.getTestSupply());

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testUtils.getTestSupplyResponse());
        MvcResult mvcResult = mockMvc.perform(post(base_url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUtils.getTestCreateSupplyRequest())))
                .andExpect(status().isOk())
                .andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("createSupplies (batch) returns the supply created as a list")
    @Test
    public void test_createSupplies() throws Throwable {

        // setup
        List<CreateSupplyRequest> testCreateSupplyRequestList = List.of(
                testUtils.getTestCreateSupplyRequest());

        List<SupplyResponse> testSupplyResponseList = List.of(
                testUtils.getTestSupplyResponse());

        // stub
        Mockito.when(mockSupplyService.createNewSupplies(anyList()))
                .thenReturn(testSupplyResponseList);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testSupplyResponseList);

        MvcResult mvcResult = mockMvc.perform(post(base_url + "/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreateSupplyRequestList)))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("delete an supply with correct supply id - status ok is received")
    @Test
    public void testDeleteSupply_valid_id() throws Throwable {
        // setup
        Long testId = 1L;
        // stub
        Mockito.when(mockSupplyService.deleteSupply(testId)).thenReturn(anyString());

        // execute and assert
        mockMvc.perform(delete(base_url + "/" + testId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("delete an supply with invalid supply id - status bad request is received")
    @Test
    public void testDeleteSupply_invalid_id() throws Throwable {
        // setup
        String invalidIdString = "123abc";

        // execute and assert
        mockMvc.perform(delete(base_url + "/" + invalidIdString))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @DisplayName("updateSupply_put with valid supplyId in body & request param - should return updated supply")
    @Test
    public void test_updateSupply_put_valid_inputs() throws Throwable {
        // setup
        Long testId = 1L;
        UpdateSupplyRequest testUpdateSupplyRequest = testUtils.getTestUpdateSupplyRequestPut();
        Supply testUpdatedSupply = testUtils.getTestSupply();
        SupplyResponse testSupplyResponse = testUtils.getTestSupplyResponse();

        // stub
        when(mockSupplyService.updateSupplyPut(any(Long.class), any(UpdateSupplyRequest.class)))
                .thenReturn(testUpdatedSupply);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testSupplyResponse);

        MvcResult mvcResult = mockMvc.perform(put(base_url + testId)
                        .content(objectMapper.writeValueAsString(testUpdateSupplyRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("updateSupply_put with invalid supplyId in body & request param - should return status as bad request")
    @Test
    public void updateSupply_put_invalid_inputs() throws Throwable {
        // setup
        String testId = "abc123";

        Supply testSupply = testUtils.getTestSupply();

        // stub
        when(mockSupplyService.updateSupplyPut(any(Long.class), any(UpdateSupplyRequest.class)))
                .thenReturn(testSupply);

        // execute and assertion
        mockMvc.perform(put(base_url + testId)
                        .content(objectMapper.writeValueAsString(testSupply))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @DisplayName("updateSupply_patch with valid supplyId in body & request param - should return updated supply")
    @Test
    public void updateSupply_patch_valid_inputs() throws Throwable {
        // setup
        Long testId = 1L;
        // UpdateSupplyRequest obj - for request body json
        UpdateSupplyRequest testSupplyPatchUpdateSupplyRequest = testUtils.getTestUpdateSupplyRequestPatch();
        // updated supply obj - for service mock
        Supply mockUpdatedSupply = testUtils.getTestSupplyUpdated();
        // SupplyResponse obj - for response body json
        SupplyResponse testSupplyResponsePatchUpdated = testUtils.getTestSupplyResponsePatchUpdated();

        // stub
        when(mockSupplyService.updateSupplyPatch(any(Long.class), any(UpdateSupplyRequest.class)))
                .thenReturn(mockUpdatedSupply);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testSupplyResponsePatchUpdated);

        MvcResult mvcResult = mockMvc.perform(patch(base_url + testId)
                        .content(objectMapper.writeValueAsString(testSupplyPatchUpdateSupplyRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("updateSupply_put with invalid supplyId in body & request param - should return status as bad request")
    @Test
    public void updateSupply_patch_invalid_inputs() throws Throwable {
        // setup
        String testId = "abc123";

        Supply mockUpdatedSupply = testUtils.getTestSupplyUpdated(); // supply after updated by patch

        // stub
        when(mockSupplyService.updateSupplyPatch(any(Long.class), any(UpdateSupplyRequest.class)))
                .thenReturn(mockUpdatedSupply);

        // execute
        MvcResult mvcResult = mockMvc.perform(patch(base_url + testId)
                        .content(objectMapper.writeValueAsString(mockUpdatedSupply))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
