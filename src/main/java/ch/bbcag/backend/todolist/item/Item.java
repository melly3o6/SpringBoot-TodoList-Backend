package ch.bbcag.backend.todolist.item;

import ch.bbcag.backend.todolist.person.Person;
import ch.bbcag.backend.todolist.tag.Tag;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Item {
    private String description;
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(insertable = false)
    private java.sql.Timestamp createdAt;
    @Column(insertable = false)
    private java.sql.Timestamp deletedAt;
    private java.sql.Timestamp doneAt;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @ManyToMany
    @JoinTable(
            name = "item_tag",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> linkedTags = new HashSet<>();

    public Set<Tag> getLinkedTags() {
        return linkedTags;
    }

    public void setLinkedTags(Set<Tag> linkedTags) {
        this.linkedTags = linkedTags;
    }

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

    // Getters
    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
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
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
