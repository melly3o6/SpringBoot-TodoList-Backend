package ch.bbcag.backend.todolist.item;

import ch.bbcag.backend.todolist.person.Person;
import ch.bbcag.backend.todolist.tag.Tag;

public class ItemMapper {

    // To response

    public static ItemResponseDTO toResponseDTO(Item item) {
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();

        itemResponseDTO.setId(item.getId());
        itemResponseDTO.setName(item.getName());
        itemResponseDTO.setDescription(item.getDescription());
        itemResponseDTO.setCreatedAt(item.getCreatedAt());
        itemResponseDTO.setDoneAt(item.getDoneAt());
        itemResponseDTO.setDeletedAt(item.getDeletedAt());

        if (item.getPerson() != null) {
            itemResponseDTO.setPersonId(item.getPerson().getId());
        }

        if (item.getLinkedTags() != null) {
            for (Tag tag : item.getLinkedTags()) {
                itemResponseDTO.getTagIds().add(tag.getId());
            }
        }

        return itemResponseDTO;
    }

    // From request

    public static Item fromRequestDTO(ItemRequestDTO itemRequestDTO) {
        Item item = new Item();

        item.setName(itemRequestDTO.getName());
        item.setDescription(itemRequestDTO.getDescription());
        item.setDeletedAt(itemRequestDTO.getDeletedAt());
        item.setDoneAt(itemRequestDTO.getDoneAt());

        if (itemRequestDTO.getPersonId() != null) {
            item.setPerson(new Person(itemRequestDTO.getPersonId()));
        }

        return item;
    }

}
