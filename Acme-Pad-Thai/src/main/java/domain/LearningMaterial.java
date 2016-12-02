package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class LearningMaterial extends DomainEntity {
	
	//Attributes
	private String title;
	private String abstractText;
	private Collection<String> attachments;
	
	//Constructors
	public LearningMaterial(){
		super();
	}

	// Getters & setters
	@NotBlank
	@NotNull
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@NotNull
	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}
	
	@Valid
	@ElementCollection
	public Collection<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Collection<String> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(String attachment) {
		this.attachments.add(attachment);
	}

	public void removeAttatchment(String attachment) {
		this.attachments.remove(attachment);
	}

}
