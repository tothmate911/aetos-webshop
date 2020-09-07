package com.aetos.webshop.dao;

import com.aetos.webshop.model.Item;

import java.util.List;

public interface ItemDao {

    List<Item> getAll();

    Item getById(Long itemId);

    Item storeItem(Item item);

    Item updateItem(Long itemId, Item updatedItem);

    Item deleteItem(Long itemId);

}
