package com.shoppingwebsite.shoppingwebsite.repository;

import com.shoppingwebsite.shoppingwebsite.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteItemById(Long id);

    Optional<Item> findItemById(Long id);
}