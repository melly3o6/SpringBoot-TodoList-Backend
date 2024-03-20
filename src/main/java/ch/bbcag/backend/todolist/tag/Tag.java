package ch.bbcag.backend.todolist.tag;

import ch.bbcag.backend.todolist.item.Item;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Tag {

    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "linkedTags")
    private Set<Item> linkedItems;

    // Getters

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Item> getLinkedItems() {
        return linkedItems;
    }

    // Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLinkedItems(Set<Item> linkedItems) {
        this.linkedItems = linkedItems;
    }

    // equals & hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
