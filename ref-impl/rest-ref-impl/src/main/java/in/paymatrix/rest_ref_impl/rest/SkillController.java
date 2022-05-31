package in.paymatrix.rest_ref_impl.rest;

import in.paymatrix.rest_ref_impl.model.Skill;
import in.paymatrix.rest_ref_impl.service.SkillService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping(value = "/api/skills", produces = MediaType.APPLICATION_JSON_VALUE)
public class SkillController {

    private final SkillService skillService;

    public SkillController(final SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable final Long id) {
        return ResponseEntity.ok(skillService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSkill(@RequestBody @Valid final Skill skill) {
        return new ResponseEntity<>(skillService.create(skill), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSkill(@PathVariable final Long id,
            @RequestBody @Valid final Skill skill) {
        skillService.update(id, skill);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSkill(@PathVariable final Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
