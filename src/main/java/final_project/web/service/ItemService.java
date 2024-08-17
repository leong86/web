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

    public Item updateItem(Long id, Item itemDetails) {
        Optional<Item> existingItemOptional = itemRepository.findById(id);
        if (!existingItemOptional.isPresent()) {
            return null; // Or you can throw a custom exception if item not found
        }
        Item existingItem = existingItemOptional.get();

        // Update fields, except createdAt
        existingItem.setName(itemDetails.getName());
        existingItem.setDescription(itemDetails.getDescription());
        existingItem.setPrice(itemDetails.getPrice());
        existingItem.setUnitPrice(itemDetails.getUnitPrice());
        existingItem.setPictureUrls(itemDetails.getPictureUrls());
        existingItem.setUpc(itemDetails.getUpc());

        // updatedAt should be set to now
        existingItem.setUpdatedAt(LocalDateTime.now());

        // Do not update createdAt, keep the original
        // existingItem.setCreatedAt(existingItem.getCreatedAt()); // This line is redundant

        return itemRepository.save(existingItem);
    }


    public boolean deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            return false;
        }
        itemRepository.deleteById(id);
        return true;
    }
}

