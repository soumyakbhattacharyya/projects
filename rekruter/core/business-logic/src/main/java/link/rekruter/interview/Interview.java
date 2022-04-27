package link.rekruter.interview;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import link.rekruter.candidate.Candidate;
import link.rekruter.person.Person;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Interview {

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

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime appointment;

    @Column
    private Long durationOfInterview;

    @Column
    private String location;

    @Column
    private String createdBy;

    @Column
    private LocalDateTime createdOn;

    @Column
    private Boolean isPrivate;

    @Column
    private Boolean cancelled;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @OneToMany(mappedBy = "invitees")
    private Set<Person> inviteesPersons;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
