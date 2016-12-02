package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CampaignRepository;
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
		Assert.isTrue(actorService.checkAuthority("SPONSOR"),
				"Only an sponsor could save campaign");

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
				.getStartMoment()) < 0);

		campaignRepository.delete(campaign);
	}

	public Collection<Campaign> findAll() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could create campaign");
		Collection<Campaign> result;

		result = campaignRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Campaign findOne(int id) {
		Campaign result;

		result = campaignRepository.findOne(id);
		Assert.notNull(result);

		return result;
	}

	public Boolean exist(int id) {
		Boolean res;
		res = campaignRepository.exists(id);
		return res;
	}

	// Other business methods -------------------------------------------------
	/** Minimo de campañas de un sponsor **/
	public Integer minCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("SPONSOR"),
				"Only an admin could create campaign");
		Integer res;
		res = campaignRepository.minCampignsPerSponsor();
		return res;
	}

	/** Maximo de campañas de un sponsor **/
	public Integer maxCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("SPONSOR"),
				"Only an admin could create campaign");
		Integer res;
		res = campaignRepository.maxCampignsPerSponsor();
		return res;
	}

	/** Media de campañas por sponsor **/
	public Double avgCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("SPONSOR"),
				"Only an admin could create campaign");
		Double res;
		res = campaignRepository.avgCampignsPerSponsor();
		return res;
	}

	/** Minimo de campañas activas de un sponsor **/
	public Integer minActiveCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("SPONSOR"),
				"Only an admin could create campaign");
		Integer res;
		res = campaignRepository.minActiveCampignsPerSponsor();
		return res;
	}

	/** Maximo de campañas activas de un sponsor **/
	public Integer maxActiveCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("SPONSOR"),
				"Only an admin could create campaign");
		Integer res;
		res = campaignRepository.maxActiveCampignsPerSponsor();
		return res;
	}

	/** Media de campañas activas por sponsor **/
	public Double avgActiveCampignsPerSponsor() {
		Assert.isTrue(actorService.checkAuthority("SPONSOR"),
				"Only an admin could create campaign");
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
