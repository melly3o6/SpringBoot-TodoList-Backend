package ch.bbcag.backend.todolist.tag;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag insert(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag update(Tag changing, Integer id) {
        Tag existing = this.findById(id);
        mergeTags(existing, changing);
        return tagRepository.save(existing);
    }

    public List<Tag> findAll() {
       return tagRepository.findAll();
    }

    public Tag findById(Integer id) {
        return tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }

    public List<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    private void mergeTags(Tag existing, Tag changing) {
        if (changing.getName() != null) {
            existing.setName(changing.getName());
        }
    }
}
