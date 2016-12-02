package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class RecipeCopy extends DomainEntity{

	//Attributes
	private String ticker;
	private String title;
	private String summary;
	private Date momentAuthored;
	private Date momentLastUpdated;
	private Collection<String> pictures;
	private Collection<String> hints;
	private String nameUser;
	private boolean winner;
	
	//Constructors
	public RecipeCopy() {
		super();
	}
	
	//Getters and Setters
	@NotNull
	@NotBlank
	@Pattern(regexp = "^[0-9]{6}-[a-zA-Z]{4}$")
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	@NotNull
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@NotNull
	@NotBlank
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMomentAuthored() {
		return momentAuthored;
	}
	public void setMomentAuthored(Date momentAuthored) {
		this.momentAuthored = momentAuthored;
	}
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMomentLastUpdated() {
		return momentLastUpdated;
	}
	public void setMomentLastUpdated(Date momentLastUpdated) {
		this.momentLastUpdated = momentLastUpdated;
	}
	@Valid
	@ElementCollection
	public Collection<String> getPictures() {
		return pictures;
	}
	public void setPictures(Collection<String> pictures) {
		this.pictures = pictures;
	}
	
	@ElementCollection
	public Collection<String> getHints() {
		return hints;
	}
	public void setHints(Collection<String> hints) {
		this.hints = hints;
	}
	
	@NotBlank
	@NotNull
	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	
	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}
	
	//Derived attributes
	private int likesRC;
	private int dislikesRC;

	public int getLikesRC() {
		return likesRC;
	}

	public void setLikesRC(int likesRC) {
		this.likesRC = likesRC;
	}

	public int getDislikesRC() {
		return dislikesRC;
	}

	public void setDislikesRC(int dislikesRC) {
		this.dislikesRC = dislikesRC;
	}

	//Relationships
	private Contest contest;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	

}
