package link.reckruter.monolithic.candidate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Candidate {

	@Id
	@Column(nullable = false, updatable = false)
	private String id;

	@Column(nullable = false)
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String phone;

	@Column(length = 500)
	private String description;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private Date dateCreated;

	@LastModifiedDate
	@Column(nullable = false)
	private Date lastUpdated;

}
