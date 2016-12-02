package services;

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
public class BillServiceTest extends AbstractTest {

	// Service under test -------------------------
	@Autowired
	private BillService billService;
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private FeeService feeService;

	// Test ---------------------------------------
	@Test
	public void testCreateBill() {
		Bill bill;
		Campaign campaign = campaignService.findOne(232);

		authenticate("administrator1");
		bill = billService.create(campaign);

		Assert.isTrue(bill.getCampaign().equals(campaign));
		Assert.isTrue(bill.getCost() == campaign.getDisplayed()
				* feeService.findFee().getFee());
		Assert.isTrue(bill.getCreationMoment().compareTo(
				new Date(System.currentTimeMillis())) < 0);
		Assert.isTrue(bill.getDescription() == null);
		Assert.isTrue(bill.getPaidMoment() == null);

		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testSaveBill() {
		Bill bill, saved;
		Campaign campaign = campaignService.findOne(232);

		authenticate("administrator1");
		bill = billService.create(campaign);

		bill.setDescription("test description");

		saved = billService.save(bill);
		billService.flush();
		
		Assert.isTrue(billService.exist(saved.getId()));

		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testDeleteBill() {
		Bill bill, saved;
		Campaign campaign = campaignService.findOne(232);

		authenticate("administrator1");
		bill = billService.create(campaign);

		bill.setDescription("test description");

		saved = billService.save(bill);

		billService.delete(saved);

		Assert.isTrue(!billService.exist(saved.getId()));

		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testAvgPaidBills() {
		System.out
				.println("--------------Show average of bills paid cost------------");

		authenticate("administrator1");

		Double avg;

		avg = billService.avgPaidBills();

		System.out.println(avg);

		unauthenticate();

		System.out
				.println("----------------------END---------------------------");
	}

	// Test ---------------------------------------
	@Test
	public void testStddevPaidBills() {
		System.out
				.println("--------------Show standar deviaion of bills paid cost------------");

		authenticate("administrator1");

		Double std;

		std = billService.stddevPaidBills();

		System.out.println(std);

		unauthenticate();

		System.out
				.println("----------------------END---------------------------");
	}

	// Test ---------------------------------------
	@Test
	public void TestAvgUnpaidBills() {
		System.out
				.println("--------------Show average of bills unpaid cost------------");

		authenticate("administrator1");

		Double avg;

		avg = billService.avgUnpaidBills();

		System.out.println(avg);

		unauthenticate();

		System.out
				.println("----------------------END---------------------------");
	}

	// Test ---------------------------------------
	@Test
	public void testStddevUnpaidBills() {
		System.out
				.println("--------------Show standar deviaion of bills unpaid cost------------");

		authenticate("administrator1");

		Double std;

		std = billService.stddevUnpaidBills();

		System.out.println(std);

		unauthenticate();

		System.out
				.println("----------------------END---------------------------");
	}
}
