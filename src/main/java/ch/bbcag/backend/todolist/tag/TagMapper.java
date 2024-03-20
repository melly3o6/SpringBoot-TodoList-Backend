package ch.bbcag.backend.todolist.tag;

import ch.bbcag.backend.todolist.item.Item;

import java.util.List;

public class TagMapper {

    // To response

    public static TagResponseDTO toResponseDTO(Tag tag) {
        TagResponseDTO tagResponseDTO = new TagResponseDTO();

        tagResponseDTO.setId(tag.getId());
        tagResponseDTO.setName(tag.getName());

        if (tag.getLinkedItems() != null) {
            List<Integer> itemIds = tag
                    .getLinkedItems()
                    .stream()
                    .map(Item::getId)
                    .toList();
            tagResponseDTO.setItemIds(itemIds);
        }

        return tagResponseDTO;
    }

    // From request
    public static Tag fromRequestDTO(TagRequestDTO tagRequestDTO) {
        Tag tag = new Tag();

        tag.setName(tagRequestDTO.getName());

        if (tag.getLinkedItems() == null) {
            tag.setLinkedItems(null);
        }

        return tag;
    }

    private static Item mapToItem(Integer id) {
        Item item = new Item();
        item.setId(id);
        return item;
    }
}
