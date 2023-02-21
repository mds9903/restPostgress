package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.exceptionHandling.DuplicateResourceException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceConflictException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.response.PaginatedResponse;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItemService.class)
public class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @MockBean
    ItemRepository mockItemRepository;

    @MockBean
    SupplyRepository mockSupplyRepository;

    @MockBean
    DemandRepository mockDemandRepository;

    @MockBean
    ThresholdRepository mockThresholdRepository;

    TestUtils testUtils = new TestUtils();


    @Test
    public void testGetAllItems() {

        // setup
        List<Item> expectedItemList = List.of(testUtils.getTestItem());

        // stub
        when(mockItemRepository.findAll()).thenReturn(expectedItemList);

        // execute
        Object actual = itemService.getAllItems();

        // assertions
        assertEquals(expectedItemList, actual);
    }

    @Test
    public void testGetAllItemsPaginated() {
        // setup
        Integer testPageSize = 1;
        Integer testPageNum = 1;
        PaginatedResponse expectedPaginatedItemList = testUtils.getTestPaginatedResponse();
        List<Item> expectedItemList = List.of(testUtils.getTestItem());
        Page<Item> expectedPage = new PageImpl<>(expectedItemList);

        // stub
        when(mockItemRepository.findAll(any(Pageable.class)))
                .thenReturn(expectedPage);
        when(mockItemRepository.count()).thenReturn(1L);

        // execute
        PaginatedResponse actual = itemService.getAllItemsPaginated(testPageSize, testPageNum);

        // assertions
        assertEquals(expectedPaginatedItemList.getItems().get(0).getItemId(),
                actual.getItems().get(0).getItemId());

    }

    @DisplayName("get item by id when id is valid")
    @Test
    public void testGetItemById_valid_id() throws Throwable {
        // setup
        Item expected = testUtils.getTestItem();

        // stub
        when(mockItemRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(expected));

        // execute
        Object actual = itemService.getItemById(expected.getItemId());

        // assertions
        assertEquals(expected, actual);
    }

    @DisplayName("get item by id when item not found")
    @Test
    public void testGetItemById_invalid_id() throws Throwable {
        // stub
        when(mockItemRepository.findById(anyLong())).thenReturn(Optional.empty());
        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            itemService.getItemById(-999L);
        });
    }

    @Test
    public void testCreateItem_valid_input() throws Throwable {
        // stub
        Item expectedItem = testUtils.getTestItem();
        when(mockItemRepository.save(any(Item.class))).thenReturn(expectedItem);

        // execute
        Object actualItem = itemService.createItem(expectedItem);

        // assertions
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testCreateItem_invalid_input() {
        // stub
        when(mockItemRepository.findById(any(Long.class))).thenReturn(Optional.of(testUtils.getTestItem()));

        // assertions
        assertThrows(DuplicateResourceException.class, () -> {
            itemService.createItem(testUtils.getTestItem());
        });
    }

    @Test
    public void testCreateItems_valid_input() throws Throwable {

        // stub
        List<Item> expectedItemList = List.of(testUtils.getTestItem());
        when(mockItemRepository.save(any(Item.class))).thenReturn(expectedItemList.get(0));

        // execute
        Object actual = itemService.createItems(expectedItemList);

        // assertions
        assertEquals(expectedItemList, actual);
    }

    @Test
    public void testDeleteItem_valid_id() throws Throwable {
        // stub
        Item testItem = testUtils.getTestItem();
        when(mockItemRepository.findById(testItem.getItemId()))
                .thenReturn(Optional.of(testItem));
        doNothing().when(mockItemRepository).deleteById(anyLong());

        // execute
        Object expected = "".getClass();
        Object actual = itemService.deleteItemById(testItem.getItemId()).getClass();

        // assert
        assertEquals(expected, actual);

    }

    @Test
    public void testDeleteItem_has_conflict() {
        // stub
        Item testItem = testUtils.getTestItem();

        when(mockItemRepository.findById(testItem.getItemId()))
                .thenReturn(Optional.of(testItem));
        // creates a conflict
        when(mockSupplyRepository.findByItemId(testItem.getItemId()))
                .thenReturn(List.of(testUtils.getTestSupply()));

        // assert
        assertThrows(ResourceConflictException.class, () -> {
            itemService.deleteItemById(testItem.getItemId());
        });

    }

    @Test
    public void testDeleteItem_invalid_id() {
        // stub
        Item testItem = testUtils.getTestItem();
        when(mockItemRepository.findById(testItem.getItemId()))
                .thenReturn(Optional.empty());

        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            itemService.deleteItemById(testItem.getItemId());
        });
    }

    @Test
    public void testUpdateItemPut_valid_inputs() throws Throwable {
        // stub
        Item testItem = testUtils.getTestItem();
        Long testItemId = testUtils.getTestItemUpdated().getItemId();
        when(mockItemRepository.findById(testItemId)).thenReturn(Optional.of(testItem));
        when(mockItemRepository.save(any(Item.class))).thenReturn(testItem);

        // execute
        Item actual = itemService.updateItemByIdPut(testItemId, testItem);

        // assertions
        assertEquals(testItem, actual);
    }

    @Test
    public void testUpdateItemPatch_valid_inputs() throws Throwable {
        // stub
        Item testItem = testUtils.getTestItemPatchUpdate();
        Long testItemId = testItem.getItemId();
        when(mockItemRepository.findById(testItemId)).thenReturn(Optional.of(testItem));
        when(mockItemRepository.save(any(Item.class))).thenReturn(testItem);

        // execute
        Item actual = itemService.updateItemByIdPatch(testItemId, testItem);

        // assertions
        assertEquals(testItem, actual);
    }


}
