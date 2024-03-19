package ch.bbcag.backend.todolist.item;

import ch.bbcag.backend.todolist.person.Person;
import ch.bbcag.backend.todolist.tag.Tag;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemMapper {

    // To response

    public static ItemResponseDTO toResponseDTO(Item item) {
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();

        itemResponseDTO.setId(item.getId());
        itemResponseDTO.setName(item.getName());
        itemResponseDTO.setCreatedAt(item.getCreatedAt());

        if (item.getLinkedTags() != null) {
            List<Integer> tagIds = item
                    .getLinkedTags()
                    .stream()
                    .map(Tag::getId)
                    .toList();

            itemResponseDTO.setTagIds(tagIds);
        }

        return itemResponseDTO;
    }

    // From request

    public static Item fromRequestDTO(ItemRequestDTO itemRequestDTO) {
        Person person = mapToPerson(itemRequestDTO.getPersonId());
        Item item = new Item();

        item.setName(itemRequestDTO.getName());

        item.setDescription(itemRequestDTO.getDescription());

        if (itemRequestDTO.getPersonId() == null) {
            item.setPerson(null);
        }
        else {
            item.setPerson(person);
        }

        if (item.getLinkedTags() == null) {
            item.setLinkedTags(null);
        } else {
            Set<Integer> linkedTagIds = itemRequestDTO.getLinkedTagId();

            if (linkedTagIds != null) {
                Set<Tag> linkedTags = linkedTagIds
                    .stream()
                    .map(ItemMapper::mapToTag)
                    .collect(Collectors.toSet());

                item.setLinkedTags(linkedTags);
            }
        }
        return item;
    }
    private static Person mapToPerson(Integer id) {
        Person person = new Person();
        person.setId(id);
        return person;
    }
    private static Tag mapToTag(Integer id) {
        Tag tag = new Tag();
        tag.setId(id);
        return tag;
    }

}
