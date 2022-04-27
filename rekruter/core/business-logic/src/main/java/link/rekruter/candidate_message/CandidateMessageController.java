package link.rekruter.candidate_message;

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
@RequestMapping(value = "/api/candidateMessages", produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateMessageController {

    private final CandidateMessageService candidateMessageService;

    public CandidateMessageController(final CandidateMessageService candidateMessageService) {
        this.candidateMessageService = candidateMessageService;
    }

    @GetMapping
    public ResponseEntity<List<CandidateMessageDTO>> getAllCandidateMessages() {
        return ResponseEntity.ok(candidateMessageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateMessageDTO> getCandidateMessage(@PathVariable final Long id) {
        return ResponseEntity.ok(candidateMessageService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCandidateMessage(
            @RequestBody @Valid final CandidateMessageDTO candidateMessageDTO) {
        return new ResponseEntity<>(candidateMessageService.create(candidateMessageDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCandidateMessage(@PathVariable final Long id,
            @RequestBody @Valid final CandidateMessageDTO candidateMessageDTO) {
        candidateMessageService.update(id, candidateMessageDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidateMessage(@PathVariable final Long id) {
        candidateMessageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
