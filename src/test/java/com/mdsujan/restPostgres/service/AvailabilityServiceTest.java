package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.response.AvailabilityV1Response;
import com.mdsujan.restPostgres.response.AvailabilityV2Response;
import com.mdsujan.restPostgres.response.AvailabilityV3Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvailabilityService.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class AvailabilityServiceTest {
    @Autowired
    private AvailabilityService availabilityService;

    @MockBean
    private SupplyRepository mockSupplyRepository;

    @MockBean
    private DemandRepository mockDemandRepository;

    @MockBean
    private ThresholdRepository mockThresholdRepository;

    TestUtils testUtils = new TestUtils();

    @Test
    public void testGetAvlQtyByItemAndLocationV1() throws Throwable {
        // setup
        List<Supply> testSupplyList = List.of(testUtils.getTestSupply());
        List<Demand> testDemandList = List.of(testUtils.getTestDemand());
        AvailabilityV1Response expected =
                testUtils.getTestAvailabilityV1Response();

        // stub
        when(mockSupplyRepository.findByItemIdAndLocationId(1L, 1L))
                .thenReturn(testSupplyList);
        when(mockDemandRepository.findByItemIdAndLocationId(1L, 1L))
                .thenReturn(testDemandList);

        // execute
        AvailabilityV1Response actual = availabilityService
                .getAvlQtyByItemAndLocationV1(1L, 1L);

        // assertion
        assertEquals(expected, actual);

    }

    //
    @Test
    public void testGetAvlQtyByItemV1() throws Throwable {
        // setup
        List<Supply> testSupplyList = List.of(testUtils.getTestSupply());
        List<Demand> testDemandList = List.of(testUtils.getTestDemand());
        AvailabilityV1Response expected =
                testUtils.getTestAvailabilityV1ResponseAllLocations();

        // stub
        when(mockSupplyRepository.findByItemId(1L))
                .thenReturn(testSupplyList);
        when(mockDemandRepository.findByItemId(1L))
                .thenReturn(testDemandList);

        AvailabilityV1Response actualResponse = availabilityService.getAvlQtyByItemV1(1l);

        // test
        assertEquals(expected, actualResponse);
    }

    @Test
    public void testGetStockLevelV2() throws Throwable {
        // setup
        Long testItemId = 1L;
        Long testLocationId = 1L;
        List<Supply> testSupplyList = List.of(testUtils.getTestSupply());
        List<Demand> testDemandList = List.of(testUtils.getTestDemand());
        Threshold testThreshold = testUtils.getTestThreshold();
        AvailabilityV2Response expected =
                testUtils.getTestAvailabilityV2Response();

        // stub
        when(mockSupplyRepository.findByItemIdAndLocationId(testItemId, testLocationId))
                .thenReturn(testSupplyList);
        when(mockDemandRepository.findByItemIdAndLocationId(testItemId, testLocationId))
                .thenReturn(testDemandList);
        when(mockThresholdRepository.findByItemIdAndLocationId(testItemId, testLocationId))
                .thenReturn(testThreshold);

        AvailabilityV2Response actualResponse = availabilityService.getStockLevelV2(
                testItemId, testLocationId
        );

        // test
        assertEquals(expected, actualResponse);
    }

    @Test
    public void testGetStockLevelV3() throws Throwable {

        // setup
        Long testItemId = 1L;
        Long testLocationId = 1L;
        List<Supply> testSupplyList = List.of(testUtils.getTestSupply());
        List<Demand> testDemandList = List.of(testUtils.getTestDemand());
        Threshold testThreshold = testUtils.getTestThreshold();
        AvailabilityV3Response expected =
                testUtils.getTestAvailabilityV3Response();

        // stub
        when(mockSupplyRepository.findByItemIdAndLocationId(testItemId, testLocationId))
                .thenReturn(testSupplyList);
        when(mockDemandRepository.findByItemIdAndLocationId(testItemId, testLocationId))
                .thenReturn(testDemandList);
        when(mockThresholdRepository.findByItemIdAndLocationId(testItemId, testLocationId))
                .thenReturn(testThreshold);

        AvailabilityV3Response actualResponse = availabilityService.getStockLevelV3(
                testItemId, testLocationId
        );

        // test
        assertEquals(expected, actualResponse);
    }


}
