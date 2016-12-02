package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Priority extends DomainEntity{
	
	// Attributes
	
	private String priority;
	
	// Constructors
	
	public Priority(){
		super();
	}
	
	// Getters and Setters

	@NotNull
	@NotBlank
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
