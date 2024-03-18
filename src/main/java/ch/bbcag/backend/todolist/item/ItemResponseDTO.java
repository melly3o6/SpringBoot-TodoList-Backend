package ch.bbcag.backend.todolist.item;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class ItemResponseDTO extends ItemRequestDTO {
    private Integer id;
    private Timestamp createdAt;
    private List<Integer> tagIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemResponseDTO that = (ItemResponseDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public List<Integer> getTagIds() {
        return tagIds;
    }
    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }
}
