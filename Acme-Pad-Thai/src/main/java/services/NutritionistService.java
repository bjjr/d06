package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Folder;
import domain.Nutritionist;

import repositories.NutritionistRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class NutritionistService {
	
	// Managed repository -----------------------------------
	
	@Autowired
	private NutritionistRepository nutritionistRepository;
	
	// Supporting services ----------------------------------
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	// Constructors -----------------------------------------
	
	public NutritionistService(){
		super();
	}
		
	// Simple CRUD methods ----------------------------------
	
	public Nutritionist create(){
		Assert.isTrue(!(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK")));
		Nutritionist result;
		UserAccount userAccount;
		Collection<Folder> folders;
		
		userAccount = userAccountService.create("NUTRITIONIST");
		
		result = new Nutritionist();
		
		folders = folderService.createFolderObligatory(result);
		
		result.setFolders(folders);
		result.setUserAccount(userAccount);
		
		return result;
	}
	
	public Nutritionist findOne(int nutritionistID){
		Nutritionist result;
	
		result = nutritionistRepository.findOne(nutritionistID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Nutritionist> findAll(){
		Collection<Nutritionist> result;
		
		result = nutritionistRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public Nutritionist save(Nutritionist nutritionist){
		Assert.isTrue((!(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"))) || actorService.checkAuthority("USER")
				|| actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(nutritionist);
		
		Nutritionist result;
		
		result = nutritionistRepository.save(nutritionist);
		
		return result;
	}
	
	public void flush() {
		nutritionistRepository.flush();
	}
	
	// Other business methods -------------------------------
	
	public Nutritionist findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Nutritionist result;

		result = nutritionistRepository.findByUserAccountId(userAccount.getId());		

		return result;
	}

	public Nutritionist findByPrincipal() {
		Nutritionist result;
		UserAccount userAccount;
	
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

}
