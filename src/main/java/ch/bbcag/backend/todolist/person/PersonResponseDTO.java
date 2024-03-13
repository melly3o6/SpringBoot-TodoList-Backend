package ch.bbcag.backend.todolist.person;

import java.util.List;
import java.util.Objects;

public final class PersonResponseDTO extends PersonRequestDTO {
    private Integer id;

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    private List<Integer> itemIds;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
