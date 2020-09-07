package com.aetos.webshop.controller;

import com.aetos.webshop.dao.ItemDao;
import com.aetos.webshop.model.Item;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

    private ItemDao itemDao;

    @GetMapping("")
    public List<Item> getAll() {
        return itemDao.getAll();
    }

    @GetMapping("/{itemId}")
    public Item getById(@PathVariable Long itemId) {
        return itemDao.getById(itemId);
    }

    @PostMapping("")
    public Item storeItem(@RequestBody Item item) {
        return itemDao.storeItem(item);
    }

    @PutMapping("/{itemId}")
    public Item updateItem(@PathVariable Long itemId, @RequestBody Item item) {
        return itemDao.updateItem(itemId, item);
    }

    @DeleteMapping("/{itemId}")
    public Item deleteItem(@PathVariable Long itemId) {
        return itemDao.deleteItem(itemId);
    }

}
