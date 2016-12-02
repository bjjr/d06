package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity{

	//Attributes
	private double fee;
	
	//Constructor
	public Fee() {
		super();
	}

	//Getters and Setters
	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}
	
}
