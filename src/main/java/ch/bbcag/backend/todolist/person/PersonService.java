package ch.bbcag.backend.todolist.person;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person create(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Integer id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Person update(Person changing, Integer id) {
        Person existing = this.findById(id);
        mergePersons(existing, changing);
        return personRepository.save(existing);
    }

    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }

    private void mergePersons(Person existing, Person changing) {
        if (changing.getUsername() != null) {
            existing.setUsername(changing.getUsername());
        }

        if (changing.getPassword() != null) {
            String newPassword = passwordEncoder.encode(changing.getPassword());
            existing.setPassword(newPassword);
        }
    }
}