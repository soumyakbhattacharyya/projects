package link.rekruter.candidate;

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
@RequestMapping(value = "/api/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(final CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getAllCandidates() {
        return ResponseEntity.ok(candidateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getCandidate(@PathVariable final Long id) {
        return ResponseEntity.ok(candidateService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCandidate(
            @RequestBody @Valid final CandidateDTO candidateDTO) {
        return new ResponseEntity<>(candidateService.create(candidateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCandidate(@PathVariable final Long id,
            @RequestBody @Valid final CandidateDTO candidateDTO) {
        candidateService.update(id, candidateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable final Long id) {
        candidateService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
