package ch.bbcag.backend.todolist.person;

import ch.bbcag.backend.todolist.item.Item;

import java.util.List;

public class PersonMapper {

    // To response

    public static PersonResponseDTO toResponseDTO(Person person) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();


        personResponseDTO.setId(person.getId());
        personResponseDTO.setUsername(person.getUsername());

        if (person.getItems() != null) {
            List<Integer> itemIds = person
                    .getItems()
                    .stream()
                    .map(Item::getId)
                    .toList();

            personResponseDTO.setItemIds(itemIds);
        }

        return personResponseDTO;
    }

    // From request

    public static Person fromRequestDTO(PersonRequestDTO personRequestDTO) {
        Person person = new Person();
        person.setUsername(personRequestDTO.getUsername());
        return person;
    }

}
