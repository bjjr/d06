package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Property extends DomainEntity{
	
	//Attributes
	private String name;
	
	//Constructors
	public Property(){
		super();
	}
	
	//Getters and setters
	
	@NotBlank
	@NotNull
	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//Relationships
	private Collection<Ingredient> ingredients;

	@ManyToMany
	public Collection<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Collection<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void addIngredient(Ingredient ingredient){
		ingredients.add(ingredient);
		ingredient.getProperties().add(this);
	}
	
	public void removeIngredient(Ingredient ingredient){
		ingredients.remove(ingredient);
		ingredient.getProperties().remove(this);
	}
	
	

}
