package ch.bbcag.backend.todolist.item;

import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

public class ItemRequestDTO {
    @NotBlank(message = "must not be blank")
    private String name;
    @NotBlank(message = "must not be blank")
    private String description;
    private Timestamp deletedAt;
    private Integer personId;
    private Set<Integer> linkedTagIds;
    public Set<Integer>  getLinkedTagId() {
        return linkedTagIds;
    }

    public void setLinkedTagId(Set<Integer> tagIds) {
        this.linkedTagIds = tagIds;
    }

    private Timestamp doneAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRequestDTO that = (ItemRequestDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, personId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Timestamp getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(Timestamp doneAt) {
        this.doneAt = doneAt;
    }
}
