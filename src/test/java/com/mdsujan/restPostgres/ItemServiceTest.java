package com.mdsujan.restPostgres;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import org.junit.Test;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.service.ItemService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

//@DataJpaTest
@SpringBootTest(classes = ItemService.class)
//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private SupplyRepository supplyRepository;


    @MockBean
    private DemandRepository demandRepository;

    @Test
    public void getItemTest(){
        Item item = new Item(
                123456L,
                "testDesc",
                "testCategory",
                "testType",
                "testStatus",
                99.99,
                false,
                false,
                false);

        itemRepository.save(item);

        given(itemRepository.findById(123456L)).willReturn(Optional.of(item));
//        Assertions.assertThat(item.getItemId()).isEqualTo(123456L);
    }

}
