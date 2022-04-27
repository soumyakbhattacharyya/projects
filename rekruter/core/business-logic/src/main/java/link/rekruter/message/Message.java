package link.rekruter.message;

import java.time.LocalDateTime;
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
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import link.rekruter.candidate_message.CandidateMessage;
import lombok.Getter;
import lombok.Setter;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Message {

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
    private String sender;

    @Column
    private String subject;

    @Column
    private String body;

    @Column
    private LocalDateTime sendAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messages_id")
    private CandidateMessage messages;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private Date lastUpdated;

}
