package ch.bbcag.backend.todolist.tag;

import ch.bbcag.backend.todolist.FailedValidationException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findById(Integer id) {
        return tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    public List<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    public Tag insert(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag update(Tag changing, Integer id) {
        Tag existing = this.findById(id);
        mergeTags(existing, changing);
        return tagRepository.save(existing);
    }

    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }

    private void mergeTags(Tag existing, Tag changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getName() != null) {
            if (StringUtils.isNotBlank(changing.getName())) {
                existing.setName(changing.getName());
            } else {
                errors.put("name", List.of("name must not be empty"));
            }

            if (!errors.isEmpty()) {
                throw new FailedValidationException(errors);
            }
        }
    }
}
