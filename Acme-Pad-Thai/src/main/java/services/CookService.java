package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Cook;
import domain.Folder;

import repositories.CookRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class CookService {

	// Managed respository ----------------------

	@Autowired
	private CookRepository cookRepository;

	// Supporting services ----------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private FolderService folderService;

	// Constructors -----------------------------

	public CookService() {
		super();
	}

	// Simple CRUD methods ----------------------

	public Cook create() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		
		Cook res;
		res = new Cook();
		
		Collection<Folder> folders;
		folders = folderService.createFolderObligatory(res);
		res.setFolders(folders);
		
		UserAccount ua;
		ua = userAccountService.create("COOK");
		res.setUserAccount(ua);
		
		return res;
		
	}
	
	public Cook save(Cook c) {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") ||
					  actorService.checkAuthority("COOK"));
		Assert.notNull(c);
		
		Cook res;
		res = cookRepository.save(c);
		
		return res;
	}
	
	public Cook findOne(int id) {
		Cook res;
		res = cookRepository.findOne(id);
		return res;
	}
	
	public void flush() {
		cookRepository.flush();
	}

	// Other business methods -------------------

	public Cook findByPrincipal() {
		Cook res;
		UserAccount ua;

		// Do not check if ua is null, this method never returns null.
		ua = LoginService.getPrincipal();

		res = findByUserAccount(ua);

		return res;
	}

	public Cook findByUserAccount(UserAccount userAccount) {
		Cook res;

		res = cookRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Collection<Cook> findAllOrderByNumPromotedMasterClasses() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Collection<Cook> res;
		res = cookRepository.findAllOrderByNumPromotedMasterClasses();

		Assert.notNull(res);

		return res;

	}

	public Integer findMinTeachedMasterClasses() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Integer res;
		res = cookRepository.findMinTeachedMasterClasses();
		Assert.notNull(res);

		return res;
	}

	public Double findAvgTeachedMasterClasses() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Double res;
		res = cookRepository.findAvgTeachedMasterClasses();
		Assert.notNull(res);

		return res;
	}

	public Integer findMaxTeachedMasterClasses() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Integer res;
		res = cookRepository.findMaxTeachedMasterClasses();
		Assert.notNull(res);

		return res;
	}

	public Double findStddevTeachedMasterClasses() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Double res;
		res = cookRepository.findStddevTeachedMasterClasses();
		Assert.notNull(res);

		return res;
	}

	public Double findAvgPromotedMasterClasses() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Double res;
		res = cookRepository.findAvgPromotedMasterClasses();
		Assert.notNull(res);

		return res;
	}

	public Double findAvgDemotedMasterClasses() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Double res;
		res = cookRepository.findAvgDemotedMasterClasses();
		Assert.notNull(res);

		return res;
	}

}
