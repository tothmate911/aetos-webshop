package com.aetos.webshop.dao;

import com.aetos.webshop.model.Item;

import java.util.List;

public interface ItemDao {

    List<Item> getAll();

    Item getById(Long id);

    Item addItem(Item item);

    Item editItem(Long id, Item item);

    Item deleteItem(Long id);

}
