package ch.bbcag.backend.todolist.tag;

import ch.bbcag.backend.todolist.item.Item;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tag {
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @ManyToMany
    private Set<Item> linkedItems = new HashSet<>();

    public Set<Item> getLinkedItems() {
        return linkedItems;
    }

    public void setLinkedItems(Set<Item> linkedItems) {
        this.linkedItems = linkedItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
