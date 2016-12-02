package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor{

	//Attributes
	private String companyName;
	private CreditCard creditCard;
	
	//Constructors
	public Sponsor() {
		super();
	}
	//Getters and Setters
	@NotBlank
	@NotNull
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	//Relationships
	private Collection<Campaign> campaigns;

	@OneToMany(mappedBy = "sponsor")
	public Collection<Campaign> getCampaigns() {
		return campaigns;
	}
	public void setCampaigns(Collection<Campaign> campaigns) {
		this.campaigns = campaigns;
	}
	
	public void addCampaign(Campaign campaign){
		this.campaigns.add(campaign);
		campaign.setSponsor(this);
	}
	
	public void removeCampaign(Campaign campaign){
		this.campaigns.remove(campaign);
		campaign.setSponsor(null);
	}
	
}
