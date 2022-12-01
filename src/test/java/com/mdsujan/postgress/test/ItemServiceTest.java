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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void whenInvalidItemId_getItemByIdTest() throws Throwable{
        // stub
        Mockito.when(mockItemRepository.findById(1L)).thenReturn(Optional.of(mockItem));

        // when
        Item itemResponse = itemService.getItemById(1L);

        // test
        assertThat(itemResponse).isEqualTo(mockItem);
    }
}