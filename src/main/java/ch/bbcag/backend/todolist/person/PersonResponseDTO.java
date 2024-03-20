package ch.bbcag.backend.todolist.person;

import java.util.List;
import java.util.Objects;

public final class PersonResponseDTO extends PersonRequestDTO {
    private Integer id;
    private List<Integer> itemIds;

    // Getters
    public Integer getId() {
        return id;
    }
    public List<Integer> getItemIds() {
        return itemIds;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }


    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    // equals & hashCode & toString

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PersonResponseDTO personResponseDTO)) {
            return false;
        }

        return super.equals(obj) && id.equals(personResponseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "PersonResponseDTO{" +
                "id=" + id +
                '}';
    }
}
