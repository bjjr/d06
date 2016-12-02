package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class User extends SocialActor{
	
	//Constructors
	
	public User()
	{
		super();
	}
	
	//Relationships
	private Collection<Recipe> recipes;

	@OneToMany(mappedBy= "user")
	public Collection<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Collection<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	public void addRecipe(Recipe recipe){
		recipes.add(recipe);
		recipe.setUser(this);
	}
	
	public void removeRecipe(Recipe recipe){
		recipes.remove(recipe);
		recipe.setUser(null);
	}

}
