package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecipeCopyRepository;
import domain.RecipeCopy;

@Service
@Transactional
public class RecipeCopyService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RecipeCopyRepository recipeCopyRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------
	public RecipeCopyService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public RecipeCopy create() {
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an admin or user could create recipeCopy");

		RecipeCopy res;

		res = new RecipeCopy();

		return res;
	}

	public RecipeCopy save(RecipeCopy recipeCopy) {
		Assert.notNull(recipeCopy);
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an user could save recipeCopy");
		Assert.isTrue(recipeCopy.getId()==0 ,"Recipe copy couldn't be edited");
		
		
		return recipeCopyRepository.save(recipeCopy);
	}

	public void flush(){
		recipeCopyRepository.flush();
	}

	public Collection<RecipeCopy> findAll() {
		Collection<RecipeCopy> result;
		
		result = recipeCopyRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	public RecipeCopy findOne(int id){
		RecipeCopy result;
		
		result = recipeCopyRepository.findOne(id);
		Assert.notNull(result);
		
		return result;
	}
	
	public Boolean exists(int id) {
		Boolean res;
		res = recipeCopyRepository.exists(id);
		return res;
	}

	
	// Other business methods -------------------------------------------------

}
