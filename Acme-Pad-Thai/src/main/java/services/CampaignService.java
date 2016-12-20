package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CampaignRepository;
import domain.Bill;
import domain.Campaign;

@Service
@Transactional
public class CampaignService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CampaignRepository campaignRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private BillService billService;
	
	// Constructors -----------------------------------------------------------
	public CampaignService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Campaign create() {
		Assert.isTrue(actorService.checkAuthority("SPONSOR"),
				"Only an sponsor could create campaign");

		Campaign res;

		res = new Campaign();

		res.setSponsor(sponsorService.findByPrincipal());
		res.setDisplayed(0);

		return res;
	}

	public Campaign save(Campaign campaign) {
		Assert.notNull(campaign);
		Assert.isTrue(actorService.checkAuthority("SPONSOR")||actorService.checkAuthority("ADMINISTRATOR"),
				"Only an sponsor or administrator could save campaign");

		if (campaign.getId() == 0) {
			Assert.isTrue(new Date(System.currentTimeMillis())
					.compareTo(campaign.getStartMoment()) < 0);
			Assert.isTrue(new Date(System.currentTimeMillis())
					.compareTo(campaign.getEndMoment()) < 0);
		}
		Assert.isTrue(campaign.getStartMoment().compareTo(
				campaign.getEndMoment()) < 0);

		return campaignRepository.save(campaign);
	}
	
	public void flush() {
		campaignRepository.flush();
	}

	public void delete(Campaign campaign) {
		Assert.notNull(campaign);
		Assert.isTrue(actorService.checkAuthority("SPONSOR"),
				"Only an sponsor could delete campaign");
		Assert.isTrue(new Date(System.currentTimeMillis()).compareTo(campaign
				.getStartMoment()) < 0, "Could not delete a started campaign");

		campaignRepository.delete(campaign);
	}

	public Collection<Campaign> findAll() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could search this");
		Collection<Campaign> result;

		result = campaignRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Campaign findOne(int id) {
		Campaign result;

		result = campaignRepository.findOne(id);
		Assert.notNull(result);
		Assert.isTrue(sponsorService.findByPrincipal().getCampaigns().contains(result), "Sponsor only could see his campaigns");
		return result;
	}
	
	public void generateBills(){
		Collection<Campaign> campaigns;
		campaigns = findAll();
		campaigns.removeAll(campaignsWithBillThisMonth());
		for(Campaign c : campaigns){
			Bill b,b1; 
			b = billService.create(c);
			b.setDescription("Bill of month "+b.getCreationMoment().getMonth());
			b1=billService.save(b);
			c.addBill(b1);
			c.setDisplayed(0);
			save(c);
		}
	}

	public Boolean exist(int id) {
		Boolean res;
		res = campaignRepository.exists(id);
		return res;
	}

	// Other business methods -------------------------------------------------
	public Collection<Campaign> campaignsWithBillThisMonth() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could search this");
		Collection<Campaign> res;
		res = campaignRepository.campaignsWithBillThisMonth();
		return res;
	}
	/** Minimo de campa�as de un sponsor **/
	public Integer minCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could search this");
		Integer res;
		res = campaignRepository.minCampignsPerSponsor();
		return res;
	}

	/** Maximo de campa�as de un sponsor **/
	public Integer maxCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could search this");
		Integer res;
		res = campaignRepository.maxCampignsPerSponsor();
		return res;
	}

	/** Media de campa�as por sponsor **/
	public Double avgCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could search this");
		Double res;
		res = campaignRepository.avgCampignsPerSponsor();
		return res;
	}

	/** Minimo de campa�as activas de un sponsor **/
	public Integer minActiveCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could search this");
		Integer res;
		res = campaignRepository.minActiveCampignsPerSponsor();
		return res;
	}

	/** Maximo de campa�as activas de un sponsor **/
	public Integer maxActiveCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could search this");
		Integer res;
		res = campaignRepository.maxActiveCampignsPerSponsor();
		return res;
	}

	/** Media de campa�as activas por sponsor **/
	public Double avgActiveCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could search this");
		Double res;
		res = campaignRepository.avgActiveCampignsPerSponsor();
		return res;
	}

	public void incrementDisplayed(Campaign c) {
		Campaign res = c, saved;
		res.setDisplayed(c.getDisplayed() + 1);
		saved = save(res);
		Assert.isTrue(res.getDisplayed() == saved.getDisplayed());
	}
}
