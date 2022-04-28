package link.reckruter.monolithic.candidate;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateController {

	@Autowired
	private CandidateService candidateService;

	@GetMapping
	public List<CandidateResource> getAll() {
		return candidateService.findAll();
	}

	@GetMapping(path = "/{id}")
	public CandidateResource getById(@PathVariable String id) {
		return candidateService.get(id);
	}

	@PostMapping
	public ResponseEntity<Object> createCandidate(@RequestBody CandidateResource candidate) {
		CandidateResource savedEntity = candidateService.create(candidate);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(savedEntity.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}