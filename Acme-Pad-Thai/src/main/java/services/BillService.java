package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BillRepository;
import domain.Bill;
import domain.Campaign;

@Service
@Transactional
public class BillService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BillRepository billRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private FeeService feeService;

	// Constructors -----------------------------------------------------------
	public BillService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Bill create(Campaign c) {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could create Bill");

		Bill res;
		Date creationMoment;

		res = new Bill();
		creationMoment = new Date(System.currentTimeMillis() - 1);

		res.setCreationMoment(creationMoment);
		res.setCampaign(c);
		res.setCost(res.getCampaign().getDisplayed()
				* feeService.findFee().getFee());

		return res;
	}

	public Bill save(Bill bill) {
		Assert.notNull(bill);
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("SPONSOR"),
				"Only an admin or sponsor could save bill");

		if (actorService.checkAuthority("SPONSOR")) {
			Assert.isTrue(bill.getCampaign().equals(
					billRepository.findOne(bill.getId()).getCampaign())
					&& bill.getCost() == billRepository.findOne(bill.getId())
							.getCost()
					&& bill.getCreationMoment().equals(
							billRepository.findOne(bill.getId())
									.getCreationMoment())
					&& bill.getDescription().equals(
							billRepository.findOne(bill.getId())
									.getDescription()));
		}

		return billRepository.save(bill);
	}
	
	public void flush() {
		billRepository.flush();
	}

	public void delete(Bill bill) {
		Assert.notNull(bill);
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could delete bill");

		billRepository.delete(bill);
	}

	public Collection<Bill> findAll() {
		Collection<Bill> result;

		result = billRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Bill findOne(int id) {
		Bill result;

		result = billRepository.findOne(id);
		Assert.notNull(result);

		return result;
	}

	public Boolean exist(int id) {
		Boolean res;
		res = billRepository.exists(id);
		return res;
	}

	// Other business methods -------------------------------------------------
	/** Media facturas pagadas **/
	public Double avgPaidBills() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could delete bill");
		Double res;
		res = billRepository.avgPaidBills();
		return res;
	}

	/** Desviacion estandar facturas pagadas **/
	public Double stddevPaidBills() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could delete bill");

		Double res;
		res = billRepository.stddevPaidBills();
		return res;
	}

	/** Media facturas NO pagadas **/
	public Double avgUnpaidBills() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could delete bill");
		Double res;
		res = billRepository.avgUnpaidBills();
		return res;
	}

	/** Desviacion estandar facturas NO pagadas **/
	public Double stddevUnpaidBills() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could delete bill");
		Double res;
		res = billRepository.stddevUnpaidBills();
		return res;
	}

}
