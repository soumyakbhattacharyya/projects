package link.rekruter.stage;

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
@RequestMapping(value = "/api/stages", produces = MediaType.APPLICATION_JSON_VALUE)
public class StageController {

    private final StageService stageService;

    public StageController(final StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping
    public ResponseEntity<List<StageDTO>> getAllStages() {
        return ResponseEntity.ok(stageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StageDTO> getStage(@PathVariable final Long id) {
        return ResponseEntity.ok(stageService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createStage(@RequestBody @Valid final StageDTO stageDTO) {
        return new ResponseEntity<>(stageService.create(stageDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStage(@PathVariable final Long id,
            @RequestBody @Valid final StageDTO stageDTO) {
        stageService.update(id, stageDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable final Long id) {
        stageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
