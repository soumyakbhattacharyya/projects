package link.rekruter.interview;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import link.rekruter.candidate.Candidate;
import link.rekruter.candidate.CandidateRepository;


@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final CandidateRepository candidateRepository;

    public InterviewService(final InterviewRepository interviewRepository,
            final CandidateRepository candidateRepository) {
        this.interviewRepository = interviewRepository;
        this.candidateRepository = candidateRepository;
    }

    public List<InterviewDTO> findAll() {
        return interviewRepository.findAll()
                .stream()
                .map(interview -> mapToDTO(interview, new InterviewDTO()))
                .collect(Collectors.toList());
    }

    public InterviewDTO get(final Long id) {
        return interviewRepository.findById(id)
                .map(interview -> mapToDTO(interview, new InterviewDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final InterviewDTO interviewDTO) {
        final Interview interview = new Interview();
        mapToEntity(interviewDTO, interview);
        return interviewRepository.save(interview).getId();
    }

    public void update(final Long id, final InterviewDTO interviewDTO) {
        final Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(interviewDTO, interview);
        interviewRepository.save(interview);
    }

    public void delete(final Long id) {
        interviewRepository.deleteById(id);
    }

    private InterviewDTO mapToDTO(final Interview interview, final InterviewDTO interviewDTO) {
        interviewDTO.setId(interview.getId());
        interviewDTO.setTitle(interview.getTitle());
        interviewDTO.setDescription(interview.getDescription());
        interviewDTO.setAppointment(interview.getAppointment());
        interviewDTO.setDurationOfInterview(interview.getDurationOfInterview());
        interviewDTO.setLocation(interview.getLocation());
        interviewDTO.setCreatedBy(interview.getCreatedBy());
        interviewDTO.setCreatedOn(interview.getCreatedOn());
        interviewDTO.setIsPrivate(interview.getIsPrivate());
        interviewDTO.setCancelled(interview.getCancelled());
        interviewDTO.setCandidate(interview.getCandidate() == null ? null : interview.getCandidate().getId());
        return interviewDTO;
    }

    private Interview mapToEntity(final InterviewDTO interviewDTO, final Interview interview) {
        interview.setTitle(interviewDTO.getTitle());
        interview.setDescription(interviewDTO.getDescription());
        interview.setAppointment(interviewDTO.getAppointment());
        interview.setDurationOfInterview(interviewDTO.getDurationOfInterview());
        interview.setLocation(interviewDTO.getLocation());
        interview.setCreatedBy(interviewDTO.getCreatedBy());
        interview.setCreatedOn(interviewDTO.getCreatedOn());
        interview.setIsPrivate(interviewDTO.getIsPrivate());
        interview.setCancelled(interviewDTO.getCancelled());
        if (interviewDTO.getCandidate() != null && (interview.getCandidate() == null || !interview.getCandidate().getId().equals(interviewDTO.getCandidate()))) {
            final Candidate candidate = candidateRepository.findById(interviewDTO.getCandidate())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "candidate not found"));
            interview.setCandidate(candidate);
        }
        return interview;
    }

}
