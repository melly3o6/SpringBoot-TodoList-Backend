package ch.bbcag.backend.todolist.tag;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class TagRequestDTO {

    // Attributes

    @NotBlank(message = "name must not be blank")
    private String name;


    // Getters

    public String getName() {
        return name;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    // equals & hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagRequestDTO that = (TagRequestDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
