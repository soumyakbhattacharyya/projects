package link.rekruter.candidate_message;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import link.rekruter.candidate.Candidate;
import link.rekruter.candidate.CandidateRepository;


@Service
public class CandidateMessageService {

    private final CandidateMessageRepository candidateMessageRepository;
    private final CandidateRepository candidateRepository;

    public CandidateMessageService(final CandidateMessageRepository candidateMessageRepository,
            final CandidateRepository candidateRepository) {
        this.candidateMessageRepository = candidateMessageRepository;
        this.candidateRepository = candidateRepository;
    }

    public List<CandidateMessageDTO> findAll() {
        return candidateMessageRepository.findAll()
                .stream()
                .map(candidateMessage -> mapToDTO(candidateMessage, new CandidateMessageDTO()))
                .collect(Collectors.toList());
    }

    public CandidateMessageDTO get(final Long id) {
        return candidateMessageRepository.findById(id)
                .map(candidateMessage -> mapToDTO(candidateMessage, new CandidateMessageDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final CandidateMessageDTO candidateMessageDTO) {
        final CandidateMessage candidateMessage = new CandidateMessage();
        mapToEntity(candidateMessageDTO, candidateMessage);
        return candidateMessageRepository.save(candidateMessage).getId();
    }

    public void update(final Long id, final CandidateMessageDTO candidateMessageDTO) {
        final CandidateMessage candidateMessage = candidateMessageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(candidateMessageDTO, candidateMessage);
        candidateMessageRepository.save(candidateMessage);
    }

    public void delete(final Long id) {
        candidateMessageRepository.deleteById(id);
    }

    private CandidateMessageDTO mapToDTO(final CandidateMessage candidateMessage,
            final CandidateMessageDTO candidateMessageDTO) {
        candidateMessageDTO.setId(candidateMessage.getId());
        candidateMessageDTO.setCandidate(candidateMessage.getCandidate() == null ? null : candidateMessage.getCandidate().getId());
        return candidateMessageDTO;
    }

    private CandidateMessage mapToEntity(final CandidateMessageDTO candidateMessageDTO,
            final CandidateMessage candidateMessage) {
        if (candidateMessageDTO.getCandidate() != null && (candidateMessage.getCandidate() == null || !candidateMessage.getCandidate().getId().equals(candidateMessageDTO.getCandidate()))) {
            final Candidate candidate = candidateRepository.findById(candidateMessageDTO.getCandidate())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "candidate not found"));
            candidateMessage.setCandidate(candidate);
        }
        return candidateMessage;
    }

}
