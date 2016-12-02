package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity{
	
	// Attributes
	
	private Date moment;
	private String subject;
	private String body;
	
	// Constructors
	
	public Message(){
		super();
	}

	// Getters and Setters
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@NotNull
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotBlank
	@NotNull
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	// Relationships
	
	private Actor sender;
	private Collection<Actor> recipients;
	private Priority priority;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Actor getSender() {
		return sender;
	}

	public void setSender(Actor sender) {
		this.sender = sender;
	}
	
	@NotEmpty
	@ManyToMany
	public Collection<Actor> getRecipients() {
		return recipients;
	}

	public void setRecipients(Collection<Actor> recipients) {
		this.recipients = recipients;
	}
	
	public void addRecipient(Actor recipient){
		recipients.add(recipient);
		recipient.getReceivedMessages().add(this);
	}
	
	public void removeRecipient(Actor recipient){
		recipients.remove(recipient);
		recipient.getReceivedMessages().remove(this);
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	
	
}

