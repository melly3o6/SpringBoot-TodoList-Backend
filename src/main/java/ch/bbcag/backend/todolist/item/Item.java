package ch.bbcag.backend.todolist.item;

import ch.bbcag.backend.todolist.person.Person;
import ch.bbcag.backend.todolist.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Item {

    // Attributes

    // ID is required when class has annotation @Id
    // ID of item -> value is generated

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Name of item -> Annotation @NotNull for validation
    @NotNull
    private String name;

    // Description of item -> Annotation @NotNull for validation
    @NotNull
    private String description;

    // Many-to-one relationship between items and person (The author of the item)
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    // Many-to-many relationship between items and tags
    @ManyToMany
    @JoinTable(
            name = "item_tag",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> linkedTags = new HashSet<>();

    // Time & Date when the item was created
    @Column(insertable = false)
    private java.sql.Timestamp createdAt;

    // Time & Date when the item was deleted
    @Column(insertable = false)
    private java.sql.Timestamp deletedAt;

    // Time & Date when the item was marked as done
    private java.sql.Timestamp doneAt;

    // Getters

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Person getPerson() {
        return person;
    }

    public Set<Tag> getLinkedTags() {
        return linkedTags;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public Timestamp getDoneAt() {
        return doneAt;
    }

    // Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setLinkedTags(Set<Tag> linkedTags) {
        this.linkedTags = linkedTags;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item that = (Item) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
