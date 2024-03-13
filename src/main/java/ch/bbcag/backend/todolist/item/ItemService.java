package ch.bbcag.backend.todolist.item;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item insert(Item item) {
        return itemRepository.save(item);
    }

    public Item update(Item changing, Integer id) {
        Item existing = this.findById(id);
        mergeItems(existing, changing);
        return itemRepository.save(existing);
    }
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
    public Item findById(Integer id) {
        return itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    public void deleteById(Integer id) {
        itemRepository.deleteById(id);
    }
    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    private void mergeItems(Item existing, Item changing) {
        if (changing.getName() != null) {
            existing.setName(changing.getName());
        }
        if (changing.getDescription() != null) {
            existing.setDescription(changing.getDescription());
        }
        if (changing.getDeletedAt() != null) {
            existing.setDeletedAt(changing.getDeletedAt());
        }
        if (changing.getDoneAt() != null) {
            existing.setDoneAt(changing.getDoneAt());
        }
        if (changing.getLinkedTags() != null) {
            existing.setLinkedTags(changing.getLinkedTags());
        }
    }

    public List<Item> findByTag(String name) {
        return itemRepository.findByTagName(name);
    }
    public List<Item> findByNameAndTagName(String name, String tagName) {
        return itemRepository.findByNameAndTagName(name, tagName);
    }
}
