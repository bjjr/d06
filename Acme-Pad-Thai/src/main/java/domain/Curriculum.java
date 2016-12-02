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
public class Curriculum extends DomainEntity{
	
	// Attributes
	
	private String photo;
	private String educationSection;
	private String experienceSection;
	private String hobbiesSection;
	
	// Constructors
	
	public Curriculum(){
		super();
	}
	
	// Getters and Setters
	
	@NotBlank
	@NotNull
	@URL
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@NotBlank
	@NotNull
	public String getEducationSection() {
		return educationSection;
	}
	
	public void setEducationSection(String educationSection) {
		this.educationSection = educationSection;
	}
	
	@NotBlank
	@NotNull
	public String getExperienceSection() {
		return experienceSection;
	}
	
	public void setExperienceSection(String experienceSection) {
		this.experienceSection = experienceSection;
	}
	
	@NotBlank
	@NotNull
	public String getHobbiesSection() {
		return hobbiesSection;
	}
	
	public void setHobbiesSection(String hobbiesSection) {
		this.hobbiesSection = hobbiesSection;
	}
	
	// Relationships
	
	private Collection<Endorser> endorsers;

	@ManyToMany
	public Collection<Endorser> getEndorsers() {
		return endorsers;
	}

	public void setEndorsers(Collection<Endorser> endorsers) {
		this.endorsers = endorsers;
	}
	
	public void addEndorser(Endorser endorser){
		endorsers.add(endorser);
		endorser.getCurricula().add(this);
	}
	
	public void removeEndorser(Endorser endorser){
		endorsers.remove(endorser);
		endorser.getCurricula().remove(this);
	}

}
