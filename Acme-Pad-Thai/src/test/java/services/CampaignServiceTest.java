package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Bill;
import domain.Campaign;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class CampaignServiceTest extends AbstractTest {

	// Service under test -------------------------
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private SponsorService sponsorService;

	// Test ---------------------------------------
	@Test
	public void testCreateCampaign() {
		Campaign campaign;

		authenticate("sponsor1");
		campaign = campaignService.create();

		Assert.isTrue(campaign.getBanners() == null);
		Assert.isTrue(campaign.getBills() == null);
		Assert.isTrue(campaign.getDisplayed() == 0);
		Assert.isTrue(campaign.getEndMoment() == null);
		Assert.isTrue(campaign.getMaxDisplayed() == 0);
		Assert.isTrue(campaign.getSponsor().equals(
				sponsorService.findByPrincipal()));
		Assert.isTrue(campaign.getStartMoment() == null);

		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testSaveCampaign() {
		Campaign campaign, saved;
		Collection<String> banners = new ArrayList<String>();
		banners.add("http://www.google.com");
		Collection<Bill> bills = new ArrayList<>();

		Calendar end = Calendar.getInstance();
		end.setTime(new Date()); 
		end.add(Calendar.YEAR, 15);

		Calendar start = Calendar.getInstance();
		start.setTime(new Date()); 
		start.add(Calendar.YEAR, 1);
		
		authenticate("sponsor1");
		campaign = campaignService.create();

		campaign.setBanners(banners);
		campaign.setBills(bills);
		campaign.setEndMoment(end.getTime());
		campaign.setMaxDisplayed(50);
		campaign.setStar(false);
		campaign.setStartMoment(start.getTime());
		
		saved = campaignService.save(campaign);
		campaignService.flush();
		
		Assert.isTrue(campaignService.exist(saved.getId()));

		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testDeleteCampaign() {
		Campaign campaign, saved;
		Collection<String> banners = new ArrayList<String>();
		banners.add("http://www.google.com");
		Collection<Bill> bills = new ArrayList<>();

		Calendar end = Calendar.getInstance();
		end.setTime(new Date()); 
		end.add(Calendar.YEAR, 15);

		Calendar start = Calendar.getInstance();
		start.setTime(new Date()); 
		start.add(Calendar.YEAR, 1);
		
		authenticate("sponsor1");
		campaign = campaignService.create();

		campaign.setBanners(banners);
		campaign.setBills(bills);
		campaign.setEndMoment(end.getTime());
		campaign.setMaxDisplayed(50);
		campaign.setStar(false);
		campaign.setStartMoment(start.getTime());
		
		saved = campaignService.save(campaign);
		
		campaignService.delete(saved);

		Assert.isTrue(!campaignService.exist(saved.getId()));

		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testMinCampaignsPerSponsor() {
		authenticate("sponsor1");
		System.out
				.println("--------------Show minumun of campaigns per Sponsor------------");

		Integer min;

		min = campaignService.minCampignsPerSponsor();

		System.out.println(min);

		System.out
				.println("----------------------END---------------------------");
		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testMaxCampaignsPerSponsor() {
		authenticate("sponsor1");
		System.out
				.println("--------------Show maximun of campaigns per Sponsor------------");

		Integer max;

		max = campaignService.maxCampignsPerSponsor();

		System.out.println(max);

		System.out
				.println("----------------------END---------------------------");
		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testAvgCampaignsPerSponsor() {
		authenticate("sponsor1");
		System.out
				.println("--------------Show average of campaigns per Sponsor------------");

		Double avg;

		avg = campaignService.avgCampignsPerSponsor();

		System.out.println(avg);

		System.out
				.println("----------------------END---------------------------");
		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testMinActiveCampaignsPerSponsor() {
		authenticate("sponsor1");
		System.out
				.println("--------------Show minumun of active campaigns per Sponsor------------");

		Integer min;

		min = campaignService.minActiveCampignsPerSponsor();

		System.out.println(min);

		System.out
				.println("----------------------END---------------------------");
		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testMaxActiveCampaignsPerSponsor() {
		authenticate("sponsor1");
		System.out
				.println("--------------Show maximun of active campaigns per Sponsor------------");

		Integer max;

		max = campaignService.maxActiveCampignsPerSponsor();

		System.out.println(max);

		System.out
				.println("----------------------END---------------------------");
		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testAvgActiveCampaignsPerSponsor() {
		authenticate("sponsor1");
		System.out
				.println("--------------Show average of active campaigns per Sponsor------------");

		Double avg;

		avg = campaignService.avgActiveCampignsPerSponsor();

		System.out.println(avg);

		System.out
				.println("----------------------END---------------------------");
		unauthenticate();
	}
	
	// Test ---------------------------------------
	@Test
	public void testIncrementDisplayed() {
		authenticate("sponsor1");
		System.out
				.println("--------------Increments num of displays------------");

		Campaign c = campaignService.findOne(231);
		int cDis = c.getDisplayed();
		
		System.out.println(cDis);

		campaignService.incrementDisplayed(c);
		
		Campaign s = campaignService.findOne(231);
		int sDis = s.getDisplayed();
		
		System.out.println(sDis);

		Assert.isTrue((cDis+1)==sDis);
		
		System.out
				.println("----------------------END---------------------------");
		unauthenticate();
	}
	
}
