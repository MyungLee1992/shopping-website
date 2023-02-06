package com.shoppingwebsite.shoppingwebsite.service;

import com.shoppingwebsite.shoppingwebsite.exception.ItemNotFoundException;
import com.shoppingwebsite.shoppingwebsite.model.Item;
import com.shoppingwebsite.shoppingwebsite.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }

    public Item findItemById(Long id) {
        return itemRepository.findItemById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item by id " + id + " was not found"));
    }

    public void deleteItem(Long id) {
        itemRepository.deleteItemById(id);
    }
}
