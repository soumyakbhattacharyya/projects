package link.reckruter.monolithic.candidate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandidateController {

	private static Map<String, Candidate> candidateRepo = new HashMap<>();

	@GetMapping(path = "/candidates")
	public List<Candidate> getAll() {

		List<Candidate> candidates = new ArrayList<>();
		Candidate candidate = new Candidate(UUID.randomUUID().toString(), "John", "Smith", "9876543219",
				"web developer");
		candidates.add(candidate);
		candidateRepo.put(candidate.getId(), candidate);
		return candidates;

	}

	@GetMapping(path = "/candidates/{id}")
	public Candidate getById(@PathVariable String id) {
		return candidateRepo.get(id);

	}

}
