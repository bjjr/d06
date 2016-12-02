package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Unit extends DomainEntity{
	
	//Attributes
	private String unit;
	
	//Constructors
	public Unit(){
		super();
	}
	
	//Getters and setters
	
	@NotBlank
	@NotNull
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

}
