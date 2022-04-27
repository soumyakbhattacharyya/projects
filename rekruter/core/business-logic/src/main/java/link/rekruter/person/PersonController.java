package link.rekruter.person;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonService personService;

    public PersonController(final PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable final Long id) {
        return ResponseEntity.ok(personService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPerson(@RequestBody @Valid final PersonDTO personDTO) {
        return new ResponseEntity<>(personService.create(personDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable final Long id,
            @RequestBody @Valid final PersonDTO personDTO) {
        personService.update(id, personDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable final Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
