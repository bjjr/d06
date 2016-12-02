package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Bill;
import domain.Folder;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorRepository sponsorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------
	public SponsorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Sponsor create() {
		Sponsor res;
		res = new Sponsor();
		Collection<Folder> folders;
		folders = folderService.createFolderObligatory(res);
		res.setFolders(folders);
		
		UserAccount ua;
		ua = userAccountService.create("SPONSOR");
		res.setUserAccount(ua);
		
		return res;
	}

	public Sponsor save(Sponsor sponsor) {
		Assert.notNull(sponsor);

		if(sponsor.getId()!=0){
			
		}
		Sponsor res;
		res = sponsorRepository.save(sponsor);
		
		return res;
	}
	
	public void flush() {
		sponsorRepository.flush();
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;

		result = sponsorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsor findOne(int id) {
		Sponsor result;

		result = sponsorRepository.findOne(id);
		Assert.notNull(result);

		return result;
	}
	
	public Boolean exist(int id) {
		Boolean res;
		res = sponsorRepository.exists(id);
		return res;
	}
	
	// Other business methods -------------------------------------------------

	public Sponsor findByPrincipal() {
		Sponsor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = sponsorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Bill> showBillsBySponsor(int id) {
		Collection<Bill> res;

		res = sponsorRepository.findBillsBySponsor(id);

		return res;
	}

	public Collection<String> companiesByNumCampaigns() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could use this method");
		Collection<String> res;

		res = sponsorRepository.companiesByNumCampaigns();

		return res;
	}

	public Collection<String> companiesByNumBills() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could use this method");
		Collection<String> res;

		res = sponsorRepository.companiesByNumBills();

		return res;
	}

	public Collection<Sponsor> inactiveSponsors() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could use this method");
		Collection<Sponsor> res;

		res = sponsorRepository.inactiveSponsors();

		return res;
	}

	public Collection<String> companiesSpentLessThanAverage() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could use this method");
		Collection<String> res;

		res = sponsorRepository.companiesSpentLessThanAverage();

		return res;
	}

	public Collection<String> companiesSpentAtLeastNinety() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could use this method");
		Collection<String> res;

		res = sponsorRepository.companiesSpentAtLeastNinety();

		return res;
	}

	public Sponsor findByUserAccountId(int id) {
		Sponsor res;

		res = sponsorRepository.findByUserAccountId(id);

		return res;
	}

}
