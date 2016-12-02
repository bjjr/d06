package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Fee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class FeeServiceTest extends AbstractTest {

	// Service under test -------------------------
	@Autowired
	private FeeService feeService;

	// Test ---------------------------------------
	@Test
	public void testfeeFindOne() {
		System.out
				.println("--------------List unique fee registered------------");

		Fee fee;

		fee = feeService.findFee();

		System.out.println(fee.getFee());

		System.out
				.println("----------------------END---------------------------");
	}

	@Test
	public void testSaveFee() {
		System.out.println("--------------Edit fee ------------");

		Fee fee, saved;

		authenticate("administrator1");
		fee = feeService.findFee();

		fee.setFee(66.6);

		saved = feeService.save(fee);
		feeService.flush();
		Assert.isTrue(feeService.exist(saved.getId()));

		unauthenticate();
		System.out
				.println("----------------------END---------------------------");

	}

}
