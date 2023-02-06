package com.shoppingwebsite.shoppingwebsite.service;

import com.shoppingwebsite.shoppingwebsite.exception.ItemNotFoundException;
import com.shoppingwebsite.shoppingwebsite.model.Item;
import com.shoppingwebsite.shoppingwebsite.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    private Item item;

    @BeforeEach
    public void setUp() {
        item = Item.builder()
                .id(1L)
                .name("Jacket")
                .description("Men's Jacket")
                .type("Cloth")
                .price(40.5)
                .imageUrl("https://images.unsplash.com/photo-1581655353564-df123a1eb820?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .build();
    }

    @Test
    public void whenItemSaved_thenReturnItem() {
        when(itemRepository.save(item)).thenReturn(item);

        assertEquals(itemService.addItem(item), item);
    }

    @Test
    public void whenAllItemsFound_thenReturnItemList() {
        Item item2 = Item.builder()
                .name("Pants")
                .description("Women's Pants")
                .type("Cloth")
                .price(56.25)
                .imageUrl("https://images.unsplash.com/photo-1581655353564-df123a1eb820?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .build();

        when(itemRepository.findAll()).thenReturn(Stream
                .of(item, item2)
                .collect(Collectors.toList()));

        assertEquals(2, itemService.findAllItems().size());
    }

    @Test
    public void whenEmptyItemsFound_thenReturnEmptyList() {
        when(itemRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(itemService.findAllItems().isEmpty());
        assertEquals(0, itemService.findAllItems().size());
    }

    @Test
    public void whenUpdateItem_thenReturnUpdatedItem() {
        when(itemRepository.save(item)).thenReturn(item);

        item.setName("Shirts");
        item.setDescription("Men's Shirts");
        item.setPrice(70);

        Item updatedItem = itemService.updateItem(item);

        assertEquals(updatedItem, item);
    }

    @Test
    public void whenItemFoundById_thenReturnItem() {
        Long id = 1L;
        when(itemRepository.findItemById(id)).thenReturn(Optional.of(item));

        Item savedItem = itemService.findItemById(id);
        assertNotNull(savedItem);
        assertEquals("Jacket", savedItem.getName());
        assertEquals("Men's Jacket", savedItem.getDescription());
        assertEquals(40.5, savedItem.getPrice());
    }

    @Test
    public void whenItemNotFoundById_thenThrowException() {
        Long id = 1L;
        Exception exception = assertThrows(ItemNotFoundException.class, () -> {
            itemService.findItemById(id);
        });

        String expectedMessage = "Item by id " + id + " was not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void whenDeleteItem_thenNothing() {
        Long id = 1L;

        doNothing().when(itemRepository).deleteItemById(id);

        itemService.deleteItem(id);

        verify(itemRepository, times(1)).deleteItemById(id);
    }

}