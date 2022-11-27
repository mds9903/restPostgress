package com.mdsujan.restPostgres;

import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.service.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
//@Import(LocationServiceTestContextConfig.class)
@SpringBootTest(classes = LocationService.class)
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationRepository locationRepository;

    @MockBean
    private SupplyRepository supplyRepository;

    @MockBean
    private DemandRepository demandRepository;

    @Before
    public void setUp() {
//        locationService = new LocationService();

        Location location = new Location(123456L,
                "testDesc",
                "testType",
                false,
                false,
                false,
                "testCity",
                "testState",
                "testCountry",
                "111111");

        Mockito.when(locationRepository.findById(location.getLocationId())).thenReturn(Optional.of(location));
    }

    @Test
    public void whenValidLocationId_thenLocationShouldBeFound() throws Throwable {
        Long locationId = 123456L;
        Location found = locationService.getLocationById(locationId);

        assertThat(found.getLocationId()).isEqualTo(locationId);
    }
}