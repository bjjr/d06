package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class LikeSA extends DomainEntity{
	
	//Attributes
	private boolean likeSA;
	
	//Constructors
	public LikeSA(){
		super();
	}
	
	//Getters and setters
	public boolean isLikeSA() {
		return likeSA;
	}

	public void setLikeSA(boolean likeSA) {
		this.likeSA = likeSA;
	}
	
	//Relationships
	private SocialActor socialActor;
	private Recipe recipe;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public SocialActor getSocialActor() {
		return socialActor;
	}

	public void setSocialActor(SocialActor socialActor) {
		this.socialActor = socialActor;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	

}
