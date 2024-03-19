package ch.bbcag.backend.todolist.person;

import ch.bbcag.backend.todolist.item.Item;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Person {

    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Item> items = new HashSet<>();

    // Constructors

    public Person(Integer id) {
        this.id = id;
    }

    public Person() {}

    // Getters

    public Integer getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<Item> getItems() {
        return items;
    }

    // Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
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
        Person that = (Person) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
