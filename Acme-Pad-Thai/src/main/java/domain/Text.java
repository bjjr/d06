package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Text extends LearningMaterial {
	
	//Attributes
	private String body;

	// Constructors
	public Text() {
		super();
	}

	// Getters & setters
	@NotBlank
	@NotNull
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
