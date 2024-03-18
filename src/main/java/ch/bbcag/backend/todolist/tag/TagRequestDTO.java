package ch.bbcag.backend.todolist.tag;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;
import java.util.Set;

public class TagRequestDTO {
    @NotBlank(message = "must not be blank")
    private String name;
    private Set<Integer> linkedItemIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagRequestDTO that = (TagRequestDTO) o;
        return Objects.equals(name, that.name);
    }
    public Set<Integer> getLinkedItemId() {
        return linkedItemIds;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLinkedItemId(Set<Integer> itemIds) {
        this.linkedItemIds = itemIds;
    }

}
