package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Quantity extends DomainEntity{
	
	//Attributes
	private double quantity;
	
	//Constructors
	public Quantity(){
		super();
	}
	
	//Getters and setters
	
	@Digits(integer = 9, fraction = 2)
	@Min((long) 1.0)
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	//Relationships
	private Recipe recipe;
	private Ingredient ingredient;
	private Unit unit;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	
	
	
	

}
