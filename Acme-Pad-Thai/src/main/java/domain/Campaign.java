package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.AccessType;
import javax.persistence.Access;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Campaign extends DomainEntity{

	//Attributes
	private Date startMoment;
	private Date endMoment;
	private Collection<String> banners;
	private int maxDisplayed;
	private boolean star;
	
	//Constructors
	public Campaign() {
		super();
	}
	
	//Getters and Setters
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return startMoment;
	}
	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return endMoment;
	}
	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}
	@NotEmpty
	@Valid
	@ElementCollection
	public Collection<String> getBanners() {
		return banners;
	}
	public void setBanners(Collection<String> banners) {
		this.banners = banners;
	}
	public void addBanner(String banner){
		this.banners.add(banner);
	}
	
	public void removeBanner(String banner){
		this.banners.remove(banner);
	}
	public int getMaxDisplayed() {
		return maxDisplayed;
	}
	public void setMaxDisplayed(int maxDisplayed) {
		this.maxDisplayed = maxDisplayed;
	}
	public boolean isStar() {
		return star;
	}
	public void setStar(boolean star) {
		this.star = star;
	}
	
	//Derived attributes
	private int displayed;
	
	public int getDisplayed() {
		return displayed;
	}
	public void setDisplayed(int displayed) {
		this.displayed = displayed;
	}
	
	//Relationships
	private Sponsor sponsor;
	private Collection<Bill> bills;
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return sponsor;
	}
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@OneToMany(mappedBy = "campaign")
	public Collection<Bill> getBills() {
		return bills;
	}

	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}
	
	
	
}
