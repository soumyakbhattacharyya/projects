package link.rekruter.candidate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import link.rekruter.person.Person;
import link.rekruter.person.PersonRepository;
import link.rekruter.resume.Resume;
import link.rekruter.resume.ResumeRepository;
import link.rekruter.stage.Stage;
import link.rekruter.stage.StageRepository;


@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final ResumeRepository resumeRepository;
    private final PersonRepository personRepository;
    private final StageRepository stageRepository;

    public CandidateService(final CandidateRepository candidateRepository,
            final ResumeRepository resumeRepository, final PersonRepository personRepository,
            final StageRepository stageRepository) {
        this.candidateRepository = candidateRepository;
        this.resumeRepository = resumeRepository;
        this.personRepository = personRepository;
        this.stageRepository = stageRepository;
    }

    public List<CandidateDTO> findAll() {
        return candidateRepository.findAll()
                .stream()
                .map(candidate -> mapToDTO(candidate, new CandidateDTO()))
                .collect(Collectors.toList());
    }

    public CandidateDTO get(final Long id) {
        return candidateRepository.findById(id)
                .map(candidate -> mapToDTO(candidate, new CandidateDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final CandidateDTO candidateDTO) {
        final Candidate candidate = new Candidate();
        mapToEntity(candidateDTO, candidate);
        return candidateRepository.save(candidate).getId();
    }

    public void update(final Long id, final CandidateDTO candidateDTO) {
        final Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(candidateDTO, candidate);
        candidateRepository.save(candidate);
    }

    public void delete(final Long id) {
        candidateRepository.deleteById(id);
    }

    private CandidateDTO mapToDTO(final Candidate candidate, final CandidateDTO candidateDTO) {
        candidateDTO.setId(candidate.getId());
        candidateDTO.setFirstName(candidate.getFirstName());
        candidateDTO.setLastName(candidate.getLastName());
        candidateDTO.setPhone(candidate.getPhone());
        candidateDTO.setDescription(candidate.getDescription());
        candidateDTO.setResume(candidate.getResume() == null ? null : candidate.getResume().getId());
        candidateDTO.setAssignedTo(candidate.getAssignedTo() == null ? null : candidate.getAssignedTo().getId());
        candidateDTO.setStage(candidate.getStage() == null ? null : candidate.getStage().getId());
        return candidateDTO;
    }

    private Candidate mapToEntity(final CandidateDTO candidateDTO, final Candidate candidate) {
        candidate.setFirstName(candidateDTO.getFirstName());
        candidate.setLastName(candidateDTO.getLastName());
        candidate.setPhone(candidateDTO.getPhone());
        candidate.setDescription(candidateDTO.getDescription());
        if (candidateDTO.getResume() != null && (candidate.getResume() == null || !candidate.getResume().getId().equals(candidateDTO.getResume()))) {
            final Resume resume = resumeRepository.findById(candidateDTO.getResume())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resume not found"));
            candidate.setResume(resume);
        }
        if (candidateDTO.getAssignedTo() != null && (candidate.getAssignedTo() == null || !candidate.getAssignedTo().getId().equals(candidateDTO.getAssignedTo()))) {
            final Person assignedTo = personRepository.findById(candidateDTO.getAssignedTo())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "assignedTo not found"));
            candidate.setAssignedTo(assignedTo);
        }
        if (candidateDTO.getStage() != null && (candidate.getStage() == null || !candidate.getStage().getId().equals(candidateDTO.getStage()))) {
            final Stage stage = stageRepository.findById(candidateDTO.getStage())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "stage not found"));
            candidate.setStage(stage);
        }
        return candidate;
    }

}
