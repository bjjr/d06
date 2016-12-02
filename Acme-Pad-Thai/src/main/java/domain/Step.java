package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Step extends DomainEntity{
	
	//Attributes
	private String description;
	private String picture;
	private Collection<String> hints;
	
	//Constructors
	public Step(){
		super();
	}
	
	//Getters and setters
	@NotBlank
	@NotNull
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@ElementCollection
	public Collection<String> getHints() {
		return hints;
	}

	public void setHints(Collection<String> hints) {
		this.hints = hints;
	}
	
	public void addHint(String hint){
		hints.add(hint);
	}
	
	public void removeHint(String hint){
		hints.remove(hint);
	}
	
	

}
