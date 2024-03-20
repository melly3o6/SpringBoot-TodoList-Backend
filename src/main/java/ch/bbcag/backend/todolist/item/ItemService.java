package ch.bbcag.backend.todolist.item;

import ch.bbcag.backend.todolist.FailedValidationException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Integer id) {
        return itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    public List<Item> findByTagName(String name) {
        return itemRepository.findByTagName(name);
    }
    public List<Item> findByNameAndTagName(String name, String tagName) {
        return itemRepository.findByNameAndTagName(name, tagName);
    }

    public Item insert(Item item) {
        return itemRepository.save(item);
    }

    public Item update(Item changing, Integer id) {
        Item existing = this.findById(id);
        mergeItems(existing, changing);
        return itemRepository.save(existing);
    }


    public void deleteById(Integer id) {
        itemRepository.deleteById(id);
    }


    private void mergeItems(Item existing, Item changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getName() != null) {
            if (StringUtils.isNotBlank(changing.getName())) {
                existing.setName(changing.getName());
            } else {
                errors.put("name", List.of("name must not be empty"));
            }
        }
        if (changing.getDescription() != null) {
            if (StringUtils.isNotBlank(changing.getDescription())) {
                existing.setDescription(changing.getDescription());
            } else {
                errors.put("description", List.of("description must not be empty"));
            }
        }
        if (changing.getDoneAt() != null) {
            existing.setDoneAt(changing.getDoneAt());
        }
        if (changing.getDeletedAt() != null) {
            existing.setDeletedAt(changing.getDeletedAt());
        }
        if (changing.getPerson() != null) {
            existing.setPerson(changing.getPerson());
        }
        if (changing.getLinkedTags() != null) {
            existing.setLinkedTags(changing.getLinkedTags());
        }
        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        }
    }


}
