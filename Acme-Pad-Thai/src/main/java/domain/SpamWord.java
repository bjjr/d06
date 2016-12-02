package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class SpamWord extends DomainEntity{
	
	// Attributes
	
	private String word;
	
	// Constructor
	
	public SpamWord(){
		super();
	}
	
	// Getters and Setters
	
	@NotBlank
	@NotNull
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
}
