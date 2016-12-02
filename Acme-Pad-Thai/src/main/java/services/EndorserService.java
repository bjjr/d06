package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.Endorser;

import repositories.EndorserRepository;

@Service
@Transactional
public class EndorserService {
	
	// Managed repository -----------------------------------
	
	@Autowired
	private EndorserRepository endorserRepository;
	
	// Supporting services ----------------------------------
	
	@Autowired
	private CurriculumService curriculumService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------
	
	public EndorserService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------
	
	public Endorser create(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Endorser result;
		
		result = new Endorser();
		
		return result;
	}
	
	public Endorser findOne(int endorserID){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Endorser result;
		
		result = endorserRepository.findOne(endorserID);
		Assert.notNull(result);
		
		return result;
	}

	public Collection<Endorser> findAll(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Collection<Endorser> result;
		
		result = endorserRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public Endorser save(Endorser endorser){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") ||
				actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(endorser);
		
		Endorser result;
		
		result = endorserRepository.save(endorser);
		
		return result;
	}
	
	public void flush() {
		endorserRepository.flush();
	}
	
	public void delete(Endorser endorser){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Assert.notNull(endorser);
		Assert.isTrue(endorser.getId() != 0);
		
		Assert.isTrue(endorserRepository.exists(endorser.getId()));
		
		Collection<Curriculum> curricula;
		
		curricula = endorser.getCurricula();
		
		for(Curriculum c:curricula){
			c.getEndorsers().remove(endorser);
			curriculumService.save(c);
		}
		
		endorserRepository.delete(endorser);
	}
	
	// Other business methods -------------------------------
	
}
