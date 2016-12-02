package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Contest extends DomainEntity{

	//Attributes
	private String title;
	private Date openingTime;
	private Date closingTime;

	//Constructor
	public Contest() {
		super();
	}

	//Getters and Setters
	@NotNull
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}
	
	//Relationships
	private Collection<RecipeCopy> recipeCopies;

	@OneToMany(mappedBy = "contest")
	public Collection<RecipeCopy> getRecipeCopies() {
		return recipeCopies;
	}

	public void setRecipeCopies(Collection<RecipeCopy> recipeCopies) {
		this.recipeCopies = recipeCopies;
	}
	
	public void addRecipeCopy(RecipeCopy recipeCopy){
		this.recipeCopies.add(recipeCopy);
		recipeCopy.setContest(this);
	}
	
	public void removeRecipeCopy(RecipeCopy recipeCopy){
		this.recipeCopies.remove(recipeCopy);
		recipeCopy.setContest(null);
	}
	
}
