package link.reckruter.monolithic.candidate;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CandidateRepository extends JpaRepository<Candidate, String> {
}
