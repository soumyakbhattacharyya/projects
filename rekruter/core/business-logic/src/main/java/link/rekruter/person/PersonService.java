package link.rekruter.person;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import link.rekruter.candidate_message.CandidateMessage;
import link.rekruter.candidate_message.CandidateMessageRepository;
import link.rekruter.interview.Interview;
import link.rekruter.interview.InterviewRepository;


@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final CandidateMessageRepository candidateMessageRepository;
    private final InterviewRepository interviewRepository;

    public PersonService(final PersonRepository personRepository,
            final CandidateMessageRepository candidateMessageRepository,
            final InterviewRepository interviewRepository) {
        this.personRepository = personRepository;
        this.candidateMessageRepository = candidateMessageRepository;
        this.interviewRepository = interviewRepository;
    }

    public List<PersonDTO> findAll() {
        return personRepository.findAll()
                .stream()
                .map(person -> mapToDTO(person, new PersonDTO()))
                .collect(Collectors.toList());
    }

    public PersonDTO get(final Long id) {
        return personRepository.findById(id)
                .map(person -> mapToDTO(person, new PersonDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final PersonDTO personDTO) {
        final Person person = new Person();
        mapToEntity(personDTO, person);
        return personRepository.save(person).getId();
    }

    public void update(final Long id, final PersonDTO personDTO) {
        final Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(personDTO, person);
        personRepository.save(person);
    }

    public void delete(final Long id) {
        personRepository.deleteById(id);
    }

    private PersonDTO mapToDTO(final Person person, final PersonDTO personDTO) {
        personDTO.setId(person.getId());
        personDTO.setEmail(person.getEmail());
        personDTO.setName(person.getName());
        personDTO.setUserId(person.getUserId());
        personDTO.setInterviewer(person.getInterviewer());
        personDTO.setRecipient(person.getRecipient() == null ? null : person.getRecipient().getId());
        personDTO.setInvitees(person.getInvitees() == null ? null : person.getInvitees().getId());
        return personDTO;
    }

    private Person mapToEntity(final PersonDTO personDTO, final Person person) {
        person.setEmail(personDTO.getEmail());
        person.setName(personDTO.getName());
        person.setUserId(personDTO.getUserId());
        person.setInterviewer(personDTO.getInterviewer());
        if (personDTO.getRecipient() != null && (person.getRecipient() == null || !person.getRecipient().getId().equals(personDTO.getRecipient()))) {
            final CandidateMessage recipient = candidateMessageRepository.findById(personDTO.getRecipient())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "recipient not found"));
            person.setRecipient(recipient);
        }
        if (personDTO.getInvitees() != null && (person.getInvitees() == null || !person.getInvitees().getId().equals(personDTO.getInvitees()))) {
            final Interview invitees = interviewRepository.findById(personDTO.getInvitees())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "invitees not found"));
            person.setInvitees(invitees);
        }
        return person;
    }

}
