package com.mdsujan.restPostgres.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.service.LocationService;
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
//@WebMvcTest(controllers = LocationController.class)
@SpringBootTest(classes = LocationController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class LocationControllerTest {
    public static final String base_url = "/inventory/locations/";
    @Autowired
    private MockMvc mockMvc; // inject the mock mvc

    @Autowired
    private LocationController locationController;

    @MockBean
    private LocationService mockLocationService;
    TestUtils testUtils = new TestUtils();

    ObjectMapper objectMapper = new ObjectMapper();

    //
    @DisplayName("get all locations - returns a list of locations test web layer")
    @Test
    public void test_getAllLocations() throws Exception {

        // setup
        List<Location> testLocationList = List.of(testUtils.getTestLocation());
        when(mockLocationService.getAllLocations()).thenReturn(testLocationList);

        // execute
        String expectedJsonString = objectMapper.writeValueAsString(testLocationList);

        MvcResult mvcResult = mockMvc.perform(get(base_url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonString = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonString, actualJsonString);
    }

    @DisplayName("get the correct error code when api endpoint is wrong - 404")
    @Test
    public void test_getAllLocations_wrong_endpoint() throws Exception {

        // setup, execute and assert
        MvcResult mvcResult = mockMvc.perform(get("/wrongEndpoint"))
                .andExpect(status().isNotFound()) // error 404
                .andReturn();

    }

    @DisplayName("getLocation() - get location by id when id is valid - returns an location as response")
    @Test
    public void testGetLocationById() throws Throwable {
        // setup
        Location testLocation = testUtils.getTestLocation();
        Long testId = 1L;

        // stub
        Mockito.when(mockLocationService.getLocationById(testId)).thenReturn(testLocation);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testLocation);

        MvcResult mvcResult = mockMvc.perform(get(base_url + testId))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);


    }

    @DisplayName("createLocation returns the locationCreated")
    @Test
    public void test_createLocation() throws Throwable {

        // setup
        Location testLocation = testUtils.getTestLocation();

        // stub
        Mockito.when(mockLocationService.createLocation(any(Location.class))).thenReturn(testLocation);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testLocation);

        MvcResult mvcResult = mockMvc.perform(post(base_url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJsonResponse))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("createLocations (batch) returns the locations created as a list")
    @Test
    public void test_createLocations() throws Throwable {

        // setup
        List<Location> testLocationList = List.of(testUtils.getTestLocation());

        // stub
        when(mockLocationService.createLocations(anyList())).thenReturn(testLocationList);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testLocationList);

        MvcResult mvcResult = mockMvc.perform(post(base_url + "/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJsonResponse))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assert
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("delete an location with correct location id - status ok is received")
    @Test
    public void testDeleteLocation_valid_id() throws Throwable {
        // setup
        Long testId = 1L;
        // stub
        Mockito.when(mockLocationService.deleteLocationById(testId)).thenReturn(anyString());

        // execute and assert
        MvcResult mvcResult = mockMvc.perform(delete(base_url + "/" + testId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("delete an location with invalid location id - status bad request is received")
    @Test
    public void testDeleteLocation_invalid_id() throws Throwable {
        // setup
        String invalidIdString = "123abc";

        // execute and assert
        MvcResult mvcResultString = mockMvc.perform(delete(base_url + "/" + invalidIdString))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @DisplayName("updateLocation_put with valid locationId in body & request param - should return updated location")
    @Test
    public void test_updateLocation_put_valid_inputs() throws Throwable {
        // setup
        Long testId = 1L;

        Location testLocation = testUtils.getTestLocation();

        // stub
        when(mockLocationService.updateLocationPut(any(Long.class), any(Location.class)))
                .thenReturn(testLocation);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testLocation);

        MvcResult mvcResult = mockMvc.perform(put(base_url + testId)
                        .content(objectMapper.writeValueAsString(testLocation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("updateLocation_put with invalid locationId in body & request param - should return status as bad request")
    @Test
    public void updateLocation_put_invalid_inputs() throws Throwable {
        // setup
        String testId = "abc123";

        Location testLocation = testUtils.getTestLocation();

        // stub
        when(mockLocationService.updateLocationPut(any(Long.class), any(Location.class)))
                .thenReturn(testLocation);

        // execute and assertion
        MvcResult mvcResult = mockMvc.perform(put(base_url + testId)
                        .content(objectMapper.writeValueAsString(testLocation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @DisplayName("updateLocation_patch with valid locationId in body & request param - should return updated location")
    @Test
    public void updateLocation_patch_valid_inputs() throws Throwable {
        // setup
        Long testId = 1L;

        Location testLocation = testUtils.getTestLocation();
        Location testLocationPatchUpdate = testUtils.getTestLocationPatchUpdate(); // patch update location, some fields null
        Location testLocationPatchUpdated = testUtils.getTestLocationPatchUpdated(); // location after updated by patch

        // stub
        when(mockLocationService.updateLocationPatch(any(Long.class), any(Location.class)))
                .thenReturn(testLocationPatchUpdated);

        // execute
        String expectedJsonResponse = objectMapper.writeValueAsString(testLocationPatchUpdated);

        MvcResult mvcResult = mockMvc.perform(patch(base_url + testId)
                        .content(objectMapper.writeValueAsString(testLocationPatchUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        // assertions
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @DisplayName("updateLocation_put with invalid locationId in body & request param - should return status as bad request")
    @Test
    public void updateLocation_patch_invalid_inputs() throws Throwable {
        // setup
        String testId = "abc123";

        Location testLocationPatchUpdated = testUtils.getTestLocationPatchUpdated(); // location after updated by patch

        // stub
        when(mockLocationService.updateLocationPatch(any(Long.class), any(Location.class)))
                .thenReturn(testLocationPatchUpdated);

        // execute
        MvcResult mvcResult = mockMvc.perform(patch(base_url + testId)
                        .content(objectMapper.writeValueAsString(testLocationPatchUpdated))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    
}
