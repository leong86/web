package final_project.web.service;

import final_project.web.entity.Item;
import final_project.web.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item createItem(Item item) {
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        return itemRepository.save(item);
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item getItemByName(String name) {
        return itemRepository.findByName(name);
    }

    public Item updateItem(Long id, Item item) {
        if (!itemRepository.existsById(id)) {
            return null;
        }
        item.setId(id);
        item.setUpdatedAt(LocalDateTime.now());
        return itemRepository.save(item);
    }

    public boolean deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            return false;
        }
        itemRepository.deleteById(id);
        return true;
    }
}

