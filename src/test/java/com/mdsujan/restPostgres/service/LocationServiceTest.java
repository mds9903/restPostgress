package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.exceptionHandling.DuplicateResourceException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceConflictException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocationService.class)
public class LocationServiceTest {
    @Autowired
    LocationService locationService;

    @MockBean
    LocationRepository mockLocationRepository;

    @MockBean
    SupplyRepository mockSupplyRepository;

    @MockBean
    DemandRepository mockDemandRepository;

    TestUtils testUtils = new TestUtils();


    @Test
    public void testGetAllLocations() {

        // setup
        List<Location> expectedLocationList = List.of(testUtils.getTestLocation());

        // stub
        when(mockLocationRepository.findAll()).thenReturn(expectedLocationList);

        // execute
        Object actual = locationService.getAllLocations();

        // assertions
        assertEquals(expectedLocationList, actual);
    }


    @DisplayName("get location by id when id is valid")
    @Test
    public void testGetLocationById_valid_id() throws Throwable {
        // setup
        Location expected = testUtils.getTestLocation();

        // stub
        when(mockLocationRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(expected));

        // execute
        Object actual = locationService.getLocationById(expected.getLocationId());

        // assertions
        assertEquals(expected, actual);
    }

    @DisplayName("get location by id when id is invalid")
    @Test
    public void testGetLocationById_invalid_id() throws Throwable {
        // stub
        when(mockLocationRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            locationService.getLocationById(-999L);
        });
    }

    @Test
    public void testCreateLocation_valid_input() throws Throwable {
        // stub
        Location expectedLocation = testUtils.getTestLocation();
        when(mockLocationRepository.save(any(Location.class)))
                .thenReturn(expectedLocation);

        // execute
        Object actualLocation = locationService.createLocation(expectedLocation);

        // assertions
        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void testCreateLocation_invalid_input() {
        // stub
        when(mockLocationRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(testUtils.getTestLocation()));

        // assertions
        assertThrows(DuplicateResourceException.class, () -> {
            locationService.createLocation(testUtils.getTestLocation());
        });
    }

    @Test
    public void testCreateLocationBatch_valid_input() throws Throwable {

        // stub
        List<Location> expectedLocationList = List.of(testUtils.getTestLocation());
        when(mockLocationRepository.save(any(Location.class)))
                .thenReturn(expectedLocationList.get(0));

        // execute
        Object actual = locationService.createLocations(expectedLocationList);

        // assertions
        assertEquals(expectedLocationList, actual);
    }

    @Test
    public void testDeleteLocation_valid_id() throws Throwable {
        // stub
        Location testLocation = testUtils.getTestLocation();
        when(mockLocationRepository.findById(testLocation.getLocationId()))
                .thenReturn(Optional.of(testLocation));
        doNothing().when(mockLocationRepository).deleteById(anyLong());

        // execute
        Object expected = "".getClass();
        Object actual = locationService.deleteLocationById(testLocation.getLocationId()).getClass();

        // assert
        assertEquals(expected, actual);

    }

    @Test
    public void testDeleteLocation_has_conflict() {
        // stub
        Location testLocation = testUtils.getTestLocation();

        when(mockLocationRepository.findById(testLocation.getLocationId()))
                .thenReturn(Optional.of(testLocation));
        // creates a conflict
        when(mockSupplyRepository.findByLocationId(testLocation.getLocationId()))
                .thenReturn(List.of(testUtils.getTestSupply()));

        // assert
        assertThrows(ResourceConflictException.class, () -> {
            locationService.deleteLocationById(testLocation.getLocationId());
        });
    }

    @Test
    public void testDeleteLocation_invalid_id() {
        // stub
        Location testLocation = testUtils.getTestLocation();
        when(mockLocationRepository.findById(testLocation.getLocationId()))
                .thenReturn(Optional.empty());

        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            locationService.deleteLocationById(testLocation.getLocationId());
        });
    }

    @Test
    public void testUpdateLocationPut_valid_inputs() throws Throwable {
        // stub
        Location testLocation = testUtils.getTestLocation();
        Long testLocationId = testUtils.getTestUpdatedLocation().getLocationId();
        when(mockLocationRepository.findById(testLocationId)).thenReturn(Optional.of(testLocation));
        when(mockLocationRepository.save(any(Location.class))).thenReturn(testLocation);

        // execute
        Location actual = locationService.updateLocationPut(testLocationId, testLocation);

        // assertions
        assertEquals(testLocation, actual);
    }

    @Test
    public void testUpdateLocationPatch_valid_inputs() throws Throwable {
        // stub
        Location testLocation = testUtils.getTestLocationPatchUpdate();
        Long testLocationId = testLocation.getLocationId();
        when(mockLocationRepository.findById(testLocationId)).thenReturn(Optional.of(testLocation));
        when(mockLocationRepository.save(any(Location.class))).thenReturn(testLocation);

        // execute
        Location actual = locationService.updateLocationPatch(testLocationId, testLocation);

        // assertions
        assertEquals(testLocation, actual);
    }


}
