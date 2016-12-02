package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity{
	
	// Attributes
	
	private String nick;
	private String nameSN;
	private String link;
	private String picture;
	
	// Constructors
	
	public SocialIdentity(){
		super();
	}
	
	// Getters and Setters
	
	@NotBlank
	@NotNull
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@NotBlank
	@NotNull
	public String getNameSN() {
		return nameSN;
	}
	
	public void setNameSN(String nameSN) {
		this.nameSN = nameSN;
	}
	
	@NotBlank
	@NotNull
	@URL
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	@URL
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	// Relationships
	
	private Actor actor;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
}
