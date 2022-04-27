package link.rekruter.candidate;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import link.rekruter.candidate_message.CandidateMessage;
import link.rekruter.interview.Interview;
import link.rekruter.person.Person;
import link.rekruter.resume.Resume;
import link.rekruter.stage.Stage;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Candidate {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @Column(length = 500)
    private String description;

    @OneToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @OneToOne
    @JoinColumn(name = "assigned_to_id")
    private Person assignedTo;

    @OneToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;
   

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
