package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity{
	
	//Attributes
	private String name;
	private String description;
	private String picture;
	private Collection<String> tags;
	
	//Constructors
	public Category(){
		super();
	}

	//Getters and setters
	
	@NotNull
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	@NotBlank
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
	public Collection<String> getTags() {
		return tags;
	}

	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}
	
	public void addTag(String tag){
		tags.add(tag);
	}
	
	public void removeTag(String tag){
		tags.remove(tag);
	}
	
	//Relationships
	
	private Collection<Recipe> recipes;
	private Category category;
	private Collection<Category> subcategories;
	
	@ManyToMany
	public Collection<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Collection<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	public void addRecipe(Recipe recipe){
		recipes.add(recipe);
		recipe.getCategories().add(this);
	}
	
	public void removeRecipe(Recipe recipe){
		recipes.remove(recipe);
		recipe.getCategories().remove(this);
	}
	
	@Valid
	@ManyToOne(optional = true)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@OneToMany(mappedBy = "category")
	public Collection<Category> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(Collection<Category> subcategories) {
		this.subcategories = subcategories;
	}
	
	public void addSubcategory(Category subcategory){
		subcategories.add(subcategory);
		subcategory.setCategory(this);
	}
	
	public void removeSubcategory(Category subcategory){
		subcategories.remove(subcategory);
		subcategory.setCategory(null);
	}
	
	
	
	
	

}
