package link.rekruter.interview;

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
@RequestMapping(value = "/api/interviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(final InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @GetMapping
    public ResponseEntity<List<InterviewDTO>> getAllInterviews() {
        return ResponseEntity.ok(interviewService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewDTO> getInterview(@PathVariable final Long id) {
        return ResponseEntity.ok(interviewService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createInterview(
            @RequestBody @Valid final InterviewDTO interviewDTO) {
        return new ResponseEntity<>(interviewService.create(interviewDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateInterview(@PathVariable final Long id,
            @RequestBody @Valid final InterviewDTO interviewDTO) {
        interviewService.update(id, interviewDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable final Long id) {
        interviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
