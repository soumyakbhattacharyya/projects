package link.rekruter.candidate_message;

import java.util.Date;
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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import link.rekruter.candidate.Candidate;
import link.rekruter.message.Message;
import link.rekruter.person.Person;
import lombok.Getter;
import lombok.Setter;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class CandidateMessage {

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

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @OneToMany(mappedBy = "recipient")
    private Set<Person> recipientPersons;

    @OneToMany(mappedBy = "messages")
    private Set<Message> messagesMessages;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private Date lastUpdated;

}
