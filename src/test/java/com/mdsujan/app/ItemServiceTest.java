package com.mdsujan.app;

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
//@Import(ItemServiceTestContextConfig.class)
@SpringBootTest(classes = MyApp.class)
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private SupplyRepository supplyRepository;

    @MockBean
    private DemandRepository demandRepository;

    @Before
    public void setUp() {
        Item item = new Item(123456L, "testDesc", "testCategory", "testType", "testStatus", 99.99, false, false, false);

        // stub ; when a method to our mock itemRepository is called we can specify the outcome
        // in this case whenever the service (which we are testing) has a method call of
        // findById() then our mock itemRepository will (mocking the real ItemRepositor)
        // will return an item
        Mockito.when(itemRepository.findById(item.getItemId())).thenReturn(Optional.of(item));
    }

    @Test
    public void whenValidItemId_thenItemShouldBeFound() throws Throwable {
        Long itemId = 123456L;
        Item found = itemService.getItemById(itemId);

        assertThat(found.getItemId()).isEqualTo(itemId);
    }

    @Test
    public void whenValidItemId_thenItemShouldBeDeleted() throws Throwable{
        Long itemId = 123456L;

        String actual = itemService.deleteItemById(itemId);

        String expected = "Item with itemId=" + itemId + " successfully deleted.";

        assertEquals(actual, expected);
    }
}