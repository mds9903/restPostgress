package com.mdsujan.postgress.test;

import com.mdsujan.restPostgres.app.MyApp;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.service.ItemService;
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
@SpringBootTest(classes = ItemService.class)
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository mockItemRepository;

    @MockBean
    private SupplyRepository mockSupplyRepository;

    @MockBean
    private DemandRepository mockDemandRepository;

    private final Item mockItem = new Item(1L, "testDesc", "testCategory", "testType", "testStatus", 19.99, false, false, false);

    private final Item mockItem2 = new Item(2L, "testDesc2", "testCategory2", "testType2", "testStatus2", 29.99, false, false, false);

    private final List<Item> mockItemList = List.of(mockItem, mockItem2);

    private final Item mockItemUpdatePut = new Item(1L, "testDescPut", "testCategoryPut", "testTypePut", "testStatusPut", 10.99, true, true, true);

    private final Item mockItemUpdatePatch = new Item(1L, null, "testCategoryPut", "testTypePut", null, null, true, true, null);

    @Test
    public void getItemByIdTest() throws Throwable {
        // stub
        Mockito.when(mockItemRepository.findById(1L)).thenReturn(Optional.of(mockItem));

        // when
        Item itemResponse = itemService.getItemById(1L);

        // test
        assertThat(itemResponse).isEqualTo(mockItem);
    }

    @Test
    public void getAllItemsTest() throws Throwable {
        // stub
        Mockito.when(mockItemRepository.findAll()).thenReturn(mockItemList);

        // when
        List<Item> itemResponseList = itemService.getAllItems();

        // test
        assertThat(itemResponseList).isEqualTo(mockItemList);
    }

    @Test
    public void createItemTest() throws Throwable {
//        Mockito.when(mockItemRepository.findById(mockItem.getItemId())).thenReturn(Optional.of(null));

        Mockito.when(mockItemRepository.save(mockItem)).thenReturn(mockItem);

        Item itemResponse = itemService.createItem(mockItem);

        assertThat(itemResponse).isEqualTo(mockItem);
    }

    @Test
    public void updateItemPutTest() throws Throwable {
        Mockito.when(mockItemRepository.findById(mockItemUpdatePut.getItemId())).thenReturn(Optional.of(mockItem));

        Mockito.when(mockItemRepository.save(mockItemUpdatePut)).thenReturn(mockItemUpdatePut);

        Item itemResponse = itemService.updateItemByIdPut(mockItemUpdatePut.getItemId(), mockItemUpdatePut);

        assertThat(itemResponse).isEqualTo(mockItemUpdatePut);
    }

    @Test
    public void updateItemPatchTest() throws Throwable {
        Mockito.when(mockItemRepository.findById(mockItemUpdatePut.getItemId())).thenReturn(Optional.of(mockItem));

        Mockito.when(mockItemRepository.save(mockItemUpdatePut)).thenReturn(mockItemUpdatePut);

        Item itemResponse = itemService.updateItemByIdPut(mockItemUpdatePut.getItemId(), mockItemUpdatePut);

        assertThat(itemResponse).isEqualTo(mockItemUpdatePut);
    }

    @Test
    public void deleteItemTest() throws Throwable {
        Mockito.when(mockItemRepository.findById(mockItem.getItemId())).thenReturn(Optional.of(mockItem));
        Mockito.when(mockSupplyRepository.findByItemItemId(mockItem.getItemId())).thenReturn(new ArrayList<>()); // empty list => no supply dependencies of item
        Mockito.when(mockDemandRepository.findByItemItemId(mockItem.getItemId())).thenReturn(new ArrayList<>()); // empty list => no demand dependencies of item
//        Mockito.doNothing().when(mockItemRepository.deleteById(mockItem.getItemId()));

        String deleteResponse = itemService.deleteItemById(mockItem.getItemId());

        assertThat(deleteResponse).isEqualTo("Item with itemId=" + mockItem.getItemId() + " successfully deleted.");
    }
}
