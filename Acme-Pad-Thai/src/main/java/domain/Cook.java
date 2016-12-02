package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Cook extends Actor {

	// Constructors
	public Cook() {
		super();
	}

	// Relationships
	private Collection<MasterClass> masterClassesTeach;

	@OneToMany(mappedBy = "cook")
	public Collection<MasterClass> getMasterClassesTeach() {
		return masterClassesTeach;
	}

	public void setMasterClassesTeach(Collection<MasterClass> masterClasses) {
		this.masterClassesTeach = masterClasses;
	}

	public void addMasterClassTeach(MasterClass masterClass) {
		this.masterClassesTeach.add(masterClass);
		masterClass.setCook(this);
	}

	public void removeMasterClassTeach(MasterClass masterClass) {
		this.masterClassesTeach.remove(masterClass);
		masterClass.setCook(null);
	}

}
