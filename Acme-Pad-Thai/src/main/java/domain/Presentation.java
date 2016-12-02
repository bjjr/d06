package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Presentation extends LearningMaterial {
	
	//Attributes
	private String path;

	// Constructors
	public Presentation() {
		super();
	}

	// Getters & setters
	@NotNull
	@NotBlank
	@URL
	@Pattern(regexp = "^(\\S*)\\.slideshare\\.net\\/\\S+$")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
