package com.mdsujan.postgress.test;

import com.mdsujan.restPostgres.app.MyApp;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.service.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocationService.class)
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Mock
    private LocationRepository mockLocationRepository;

    @Mock
    private SupplyRepository mockSupplyRepository;

    @Mock
    private DemandRepository mockDemandRepository;

    private final Location mockLocation = new Location(
            2L,
            "testDesc",
            "testType",
            false,
            false,
            false,
            "testLine1",
            "testLine2",
            "testLine3",
            "testCity",
            "testState",
            "testCountry",
            "111111");

    private final Location mockLocation2 = new Location(
            2L,
            "testDesc2",
            "testType2",
            false,
            false,
            false,
            "testLine12",
            "testLine22",
            "testLine32",
            "testCity",
            "testState",
            "testCountry",
            "22222");


    private final List<Location> mockLocationList = List.of(mockLocation, mockLocation2);

    private final Location mockLocationUpdatePut = new Location(
            1L,
            "testDescUpdatePut",
            "testType2UpdatePut",
            false,
            false,
            false,
            "testLine12UpdatePut",
            "testLine22UpdatePut",
            "testLine32UpdatePut",
            "testCityUpdatePut",
            "testStateUpdatePut",
            "testCountryUpdatePut",
            "22222");
    private final Location mockLocationUpdatePatch = new Location(
            1L,
            null,
            "testType2UpdatePatch",
            true,
            null,
            null,
            "testLine12UpdatePatch",
            null,
            null,
            "testCityUpdatePatch",
            null,
            "testCountryUpdatePatch",
            null);

    @Test
    public void getLocationByIdTest() throws Throwable {
        // stub
        Mockito.when(mockLocationRepository.findById(1L)).thenReturn(Optional.of(mockLocation));

        // when
        Location locationResponse = locationService.getLocationById(1L);

        // test
        assertThat(locationResponse).isEqualTo(mockLocation);
    }

    @Test
    public void getAllLocationsTest() throws Throwable {
        // stub
        Mockito.when(mockLocationRepository.findAll()).thenReturn(mockLocationList);

        // when
        List<Location> locationResponseList = locationService.getAllLocations();

        // test
        assertThat(locationResponseList).isEqualTo(mockLocationList);
    }

    @Test
    public void createLocationTest() throws Throwable {
//        Mockito.when(mockLocationRepository.findById(mockLocation.getLocationId())).thenReturn(Optional.of(null));

        Mockito.when(mockLocationRepository.save(mockLocation)).thenReturn(mockLocation);

        Location locationResponse = locationService.createLocation(mockLocation);

        assertThat(locationResponse).isEqualTo(mockLocation);
    }

    @Test
    public void updateLocationPutTest() throws Throwable {
        Mockito.when(mockLocationRepository.findById(mockLocationUpdatePut.getLocationId())).thenReturn(Optional.of(mockLocation));

        Mockito.when(mockLocationRepository.save(mockLocationUpdatePut)).thenReturn(mockLocationUpdatePut);

        Location locationResponse = locationService.updateLocationPut(mockLocationUpdatePut.getLocationId(), mockLocationUpdatePut);

        assertThat(locationResponse).isEqualTo(mockLocationUpdatePut);
    }

    @Test
    public void updateLocationPatchTest() throws Throwable {
        Mockito.when(mockLocationRepository.findById(mockLocationUpdatePut.getLocationId())).thenReturn(Optional.of(mockLocation));

        Mockito.when(mockLocationRepository.save(mockLocationUpdatePut)).thenReturn(mockLocationUpdatePut);

        Location locationResponse = locationService.updateLocationPatch(mockLocationUpdatePut.getLocationId(), mockLocationUpdatePut);

        assertThat(locationResponse).isEqualTo(mockLocationUpdatePut);
    }

    @Test
    public void deleteLocationTest() throws Throwable {
        Mockito.when(mockLocationRepository.findById(mockLocation.getLocationId())).thenReturn(Optional.of(mockLocation));
        Mockito.when(mockSupplyRepository.findByLocationLocationId(mockLocation.getLocationId())).thenReturn(new ArrayList<>()); // empty list => no supply dependencies of location
        Mockito.when(mockDemandRepository.findByLocationLocationId(mockLocation.getLocationId())).thenReturn(new ArrayList<>()); // empty list => no demand dependencies of location
//        Mockito.doNothing().when(mockLocationRepository.deleteById(mockLocation.getLocationId()));

        String deleteResponse = locationService.deleteLocationById(mockLocation.getLocationId());

        assertThat(deleteResponse).isEqualTo("Location with locationId=" + mockLocation.getLocationId() + " successfully deleted.");
    }
}
