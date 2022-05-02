package link.reckruter.service.candidate;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CandidateService {

	private final CandidateRepository candidateRepository;

	public CandidateService(final CandidateRepository candidateRepository) {
		this.candidateRepository = candidateRepository;

	}

	public List<CandidateResource> findAll() {
		return candidateRepository.findAll().stream().map(candidate -> mapToDTO(candidate, new CandidateResource()))
				.collect(Collectors.toList());
	}

	public CandidateResource get(final String id) {
		return candidateRepository.findById(id).map(candidate -> mapToDTO(candidate, new CandidateResource()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public CandidateResource create(final CandidateResource candidateResource) {
		final Candidate candidate = new Candidate();

		/**
		 * generate id
		 */
		final String id = UUID.randomUUID().toString();
		candidate.setId(id);

		/**
		 * update the audit information
		 */
		candidate.setDateCreated(new Date());
		candidate.setLastUpdated(new Date());
		mapToEntity(candidateResource, candidate);
		Candidate savedCandidate = candidateRepository.save(candidate);
		CandidateResource returnedCandidateResource = mapToDTO(savedCandidate, candidateResource);
		return returnedCandidateResource;
	}

	public void update(final String id, final CandidateResource candidateResource) {
		final Candidate candidate = candidateRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		mapToEntity(candidateResource, candidate);
		candidateRepository.save(candidate);
	}

	public void delete(final String id) {
		candidateRepository.deleteById(id);
	}

	private CandidateResource mapToDTO(final Candidate candidate, final CandidateResource CandidateResource) {
		CandidateResource.setId(candidate.getId());
		CandidateResource.setFirstName(candidate.getFirstName());
		CandidateResource.setLastName(candidate.getLastName());
		CandidateResource.setPhone(candidate.getPhone());
		CandidateResource.setDescription(candidate.getDescription());
		return CandidateResource;
	}

	private Candidate mapToEntity(final CandidateResource candidateResource, final Candidate candidate) {
		candidate.setFirstName(candidateResource.getFirstName());
		candidate.setLastName(candidateResource.getLastName());
		candidate.setPhone(candidateResource.getPhone());
		candidate.setDescription(candidateResource.getDescription());

		return candidate;
	}

}
