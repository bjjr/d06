package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Endorser extends DomainEntity{
	
	// Attributes
	
	private String name;
	private String homepage;
	
	// Constructors
	
	public Endorser(){
		super();
	}
	
	// Getters and Setters
	
	@NotBlank
	@NotNull
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	@NotNull
	@URL
	public String getHomepage() {
		return homepage;
	}
	
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	
	// Relationships
	
	private Collection<Curriculum> curricula;

	@ManyToMany(mappedBy = "endorsers")
	public Collection<Curriculum> getCurricula() {
		return curricula;
	}

	public void setCurricula(Collection<Curriculum> curricula) {
		this.curricula = curricula;
	}
	
	public void addCurriculum(Curriculum curriculum){
		curricula.add(curriculum);
		curriculum.getEndorsers().add(this);
	}
	
	public void removeCurriculum(Curriculum curriculum){
		curricula.remove(curriculum);
		curriculum.getEndorsers().remove(this);
	}

}
