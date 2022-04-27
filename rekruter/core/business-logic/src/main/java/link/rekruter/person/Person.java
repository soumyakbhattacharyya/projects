package link.rekruter.person;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import link.rekruter.candidate.Candidate;
import link.rekruter.candidate_message.CandidateMessage;
import link.rekruter.interview.Interview;
import lombok.Getter;
import lombok.Setter;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Person {

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
    private String email;

    @Column
    private String name;

    @Column
    private String userId;

    @Column(nullable = false)
    private Boolean interviewer;

    @OneToOne(mappedBy = "assignedTo", fetch = FetchType.LAZY)
    private Candidate assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private CandidateMessage recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitees_id")
    private Interview invitees;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private Date lastUpdated;

}
