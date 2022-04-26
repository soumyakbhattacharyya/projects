package link.rekruter.opening_manager.opening;

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
@RequestMapping(value = "/api/openings", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpeningController {

    private final OpeningService openingService;

    public OpeningController(final OpeningService openingService) {
        this.openingService = openingService;
    }

    @GetMapping
    public ResponseEntity<List<OpeningDTO>> getAllOpenings() {
        return ResponseEntity.ok(openingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpeningDTO> getOpening(@PathVariable final Long id) {
        return ResponseEntity.ok(openingService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createOpening(@RequestBody @Valid final OpeningDTO openingDTO) {
        return new ResponseEntity<>(openingService.create(openingDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOpening(@PathVariable final Long id,
            @RequestBody @Valid final OpeningDTO openingDTO) {
        openingService.update(id, openingDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpening(@PathVariable final Long id) {
        openingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
