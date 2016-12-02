package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Ingredient extends DomainEntity{
	
	//Attributes
	private String name;
	private String description;
	private String picture;
	
	//Constructors
	public Ingredient(){
		super();
	}
	
	//Getters and setters
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
	
	//Relationships
	private Collection<Property> properties;

	@NotEmpty
	@ManyToMany(mappedBy = "ingredients")
	public Collection<Property> getProperties() {
		return properties;
	}

	public void setProperties(Collection<Property> properties) {
		this.properties = properties;
	}
	
	public void addProperty(Property property){
		properties.add(property);
		property.getIngredients().add(this);
	}
	
	public void removeProperty(Property property){
		properties.remove(property);
		property.getIngredients().remove(this);
	}
	

}
