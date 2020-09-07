package com.aetos.webshop.dao;

import com.aetos.webshop.model.Item;
import com.aetos.webshop.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ItemDaoDB implements ItemDao {

    private ItemRepository itemRepository;

    @Override
    public List<Item> getAll() {
        log.info("Get all items");
        return itemRepository.findAll();
    }

    @Override
    public Item getById(Long itemId) {
        log.info("Get item by id: " + itemId);
        return itemRepository.findById(itemId).orElse(null);
    }

    @Override
    public Item storeItem(Item item) {
        itemRepository.save(item);
        log.info("Item saved: " + item);
        return item;
    }

    @Override
    public Item updateItem(Long itemId, Item updatedItem) {
        Item itemToUpdate = itemRepository.findById(itemId).orElse(null);
        if (itemToUpdate != null) {
            itemToUpdate.setName(updatedItem.getName());
            itemToUpdate.setDescription(updatedItem.getDescription());
            itemToUpdate.setCategory(updatedItem.getCategory());
            itemToUpdate.setPrice(updatedItem.getPrice());
            itemToUpdate.setQuantityOnStock(updatedItem.getQuantityOnStock());

            itemRepository.save(itemToUpdate);
            log.info("Updated item: " + itemToUpdate);
        } else {
            log.info("Failed to update, item with id " + itemId + " not found");
        }
        return itemToUpdate;
    }

    @Override
    public Item deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null) {
            itemRepository.delete(item);
            log.info("Item deleted: " + item);
        } else {
            log.info("Failed to delete, item with id " + itemId + " not found");
        }
        return item;
    }
}
