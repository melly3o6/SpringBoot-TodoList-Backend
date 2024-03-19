package ch.bbcag.backend.todolist.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

public class ItemRequestDTO {

    // Attributes

    @NotBlank(message = "must not be blank")
    private String name;

    @NotBlank(message = "must not be blank")
    private String description;

    @NotNull(message = "owner is required")
    private Integer personId;

    private Set<Integer> linkedTagIds;

    private Timestamp deletedAt;

    private Timestamp doneAt;

    // Getters

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPersonId() {
        return personId;
    }

    public Set<Integer> getLinkedTagId() {
        return linkedTagIds;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public Timestamp getDoneAt() {
        return doneAt;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public void setLinkedTagId(Set<Integer> tagIds) {
        this.linkedTagIds = tagIds;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setDoneAt(Timestamp doneAt) {
        this.doneAt = doneAt;
    }

    // equals & hashCode

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

}
