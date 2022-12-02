package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.LocationController;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.service.LocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocationController.class)
public class LocationControllerTest {

    @Autowired
    private LocationController locationController;

    @MockBean
    private LocationService mockLocationService;

    // a single item for testing
    private final Location mockLocation = new Location(
            1L,
            "testLocationDesc",
            "testType",
            true,
            true,
            true,
            "testCity",
            "testState",
            "testCountry",
            "testPincode");

    private final Location mockLocationUpdatePut = new Location(
            1L,
            "testLocationDescPut",
            "testTypePut",
            false,
            false,
            false,
            "testCityPut",
            "testStatePut",
            "testCountryPut",
            "testPincodePut");

    private final Location mockLocationUpdatePatch = new Location(
            1L,
            null,
            null,
            false,
            false,
            false,
            "testCityPatch",
            null,
            null,
            "testPincodePatch");

    // a list of items for testing
    private final List<Location> mockLocationsList = List.of(mockLocation,
            new Location(
                    1L,
                    "testLocationDesc2",
                    "testType2",
                    true,
                    true,
                    true,
                    "testCity2",
                    "testState2",
                    "testCountry2",
                    "testPincode2"));

    @Test
    public void testGetAllLocations() {
        // stub
        Mockito.when(mockLocationService.getAllLocations()).thenReturn(mockLocationsList);

        // when
        List<Location> locationResponse = locationController.getLocations();

        // then
        assertThat(locationResponse.size()).isEqualTo(mockLocationsList.size());
    }

    @Test
    public void testGetLocationById() throws Throwable {
        // stub
        Mockito.when(mockLocationService.getLocationById(1L)).thenReturn(mockLocation);

        // when
        Location locationResponse = locationController.getLocationWithId(1L);

        // then
        assertThat(locationResponse).isEqualTo(mockLocation);
    }

    @Test
    public void testCreateLocation() throws Throwable {
        // stub
        Mockito.when(mockLocationService.createLocation(mockLocation)).thenReturn(mockLocation);

        // when
        Location locationResponse = locationController.createLocation(mockLocation);

        // then
        assertThat(locationResponse).isEqualTo(mockLocation);
    }

    // deleteLocation
    @Test
    public void testDeleteLocation() throws Throwable {
        // stub
        Mockito.when(mockLocationService.deleteLocationById(mockLocation.getLocationId())).thenReturn("Location with locationId=" + mockLocation.getLocationId() + " successfully deleted.");

        // when
        String deleteLocationResponse = locationController.deleteLocation(mockLocation.getLocationId());

        // then
        assertThat(deleteLocationResponse).isEqualTo("Location with locationId=" + mockLocation.getLocationId() + " successfully deleted.");
    }

    // updateLocation put
    @Test
    public void testUpdateLocationPut() throws Throwable {
        // stub
        Mockito.when(mockLocationService.updateLocationPut(mockLocationUpdatePut.getLocationId(), mockLocationUpdatePut)).thenReturn(mockLocationUpdatePut);

        // when
        Location locationResponse = locationController.updateLocationPut(mockLocationUpdatePut.getLocationId(), mockLocationUpdatePut);

        // then
        assertThat(locationResponse).isEqualTo(mockLocationUpdatePut);
    }

    // updateLocation put
    @Test
    public void testUpdateLocationPatch() throws Throwable {
        // stub
        Mockito.when(mockLocationService.updateLocationPatch(mockLocationUpdatePatch.getLocationId(), mockLocationUpdatePatch)).thenReturn(mockLocationUpdatePatch);

        // when
        Location locationResponse = locationController.updateLocationPatch(mockLocationUpdatePatch.getLocationId(), mockLocationUpdatePatch);

        // then
        assertThat(locationResponse).isEqualTo(mockLocationUpdatePatch);
    }

}
