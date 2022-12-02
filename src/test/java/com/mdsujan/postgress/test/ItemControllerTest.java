package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.ItemController;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.service.ItemService;
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
@SpringBootTest(classes = ItemController.class)
public class ItemControllerTest {

    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService mockItemService;

    // a single item for testing
    private final Item mockItem = new Item(
            1L,
            "testDesc",
            "testCategory",
            "testType",
            "testStatus",
            99.99,
            false,
            false,
            false);

    private final Item mockItemUpdatePut = new Item(
            1L,
            "testDescPut",
            "testCategoryPut",
            "testTypePut",
            "testStatusPut",
            199.99,
            true,
            true,
            true);

    private final Item mockItemUpdatePatch = new Item(
            1L,
            "testDescPatch",
            "testCategoryPatch",
            null,
            null,
            null,
            null,
            null,
            true);

    // a list of items for testing
    private final List<Item> mockItemList = List.of(mockItem ,new Item(
            2L,
            "testDesc",
            "testCategory",
            "testType",
            "testStatus",
            99.99,
            false,
            false,
            false));

    @Test
    public void testGetAllItems() {
        // stub
        Mockito.when(mockItemService.getAllItems()).thenReturn(mockItemList);

        // when
        List<Item> itemResponse = itemController.getAllItems();

        // then
        assertThat(itemResponse.size()).isEqualTo(mockItemList.size());
    }

    @Test
    public void testGetItemById() throws Throwable {
        // stub
        Mockito.when(mockItemService.getItemById(1L)).thenReturn(mockItem);

        // when
        Item itemResponse = itemController.getItem(1L);

        // then
        assertThat(itemResponse).isEqualTo(mockItem);
    }

    @Test
    public void testCreateItem() throws Throwable {
        // stub
        Mockito.when(mockItemService.createItem(mockItem)).thenReturn(mockItem);

        // when
        Item itemResponse = itemController.createItem(mockItem);

        // then
        assertThat(itemResponse).isEqualTo(mockItem);
    }

    // deleteItem
    @Test
    public void testDeleteItem() throws Throwable {
        // stub
        Mockito.when(mockItemService.deleteItemById(mockItem.getItemId()))
                .thenReturn("Item with itemId=" + mockItem.getItemId() + " successfully deleted.");

        // when
        String deleteItemResponse = itemController.deleteItem(mockItem.getItemId());

        // then
        assertThat(deleteItemResponse).isEqualTo("Item with itemId=" + mockItem.getItemId() + " successfully deleted.");
    }

    // updateItem put
    @Test
    public void testUpdateItemPut() throws Throwable {
        // stub
        Mockito.when(mockItemService.updateItemByIdPut(mockItem.getItemId(), mockItemUpdatePut))
                .thenReturn(mockItemUpdatePut);

        // when
        Item itemResponse = itemController.updateItemPut(mockItem.getItemId(), mockItemUpdatePut);

        // then
        assertThat(itemResponse).isEqualTo(mockItemUpdatePut);
    }

    // updateItem patch
    @Test
    public void testUpdateItemPatch() throws Throwable {
        // stub
        Mockito.when(mockItemService.updateItemByIdPatch(mockItem.getItemId(), mockItemUpdatePatch))
                .thenReturn(mockItemUpdatePatch);

        // when
        Item itemResponse = itemController.updateItemPatch(mockItem.getItemId(), mockItemUpdatePatch);

        // then
        assertThat(itemResponse).isEqualTo(mockItemUpdatePatch);
    }

}
