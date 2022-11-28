package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.app.MyApp;
import com.mdsujan.restPostgres.controller.ItemController;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.service.ItemService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItemController.class)
public class ItemControllerTest {

    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService mockItemService;

    @Test
    public void testGetAllItems() {
        // given
        Item item1 = new Item(
                1L,
                "testDesc",
                "testCategory",
                "testType",
                "testStatus",
                99.99,
                false,
                false,
                false);
        Item item2 = new Item(
                2L,
                "testDesc",
                "testCategory",
                "testType",
                "testStatus",
                99.99,
                false,
                false,
                false);
        Item item3 = new Item(
                3L,
                "testDesc",
                "testCategory",
                "testType",
                "testStatus",
                99.99,
                false,
                false,
                false);
        Item item4 = new Item(
                4L,
                "testDesc",
                "testCategory",
                "testType",
                "testStatus",
                99.99,
                false,
                false,
                false);

        List<Item> itemList = List.of(item1, item2, item3, item4);

        // stub
        Mockito.when(mockItemService.getAllItems()).thenReturn(itemList);

        // when
        List<Item> itemResponse = itemController.getAllItems();

        // then
        assertThat(itemResponse.size()).isEqualTo(4);
    }
}
