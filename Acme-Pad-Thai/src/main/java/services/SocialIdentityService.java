package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.SocialIdentity;

import repositories.SocialIdentityRepository;

@Service
@Transactional
public class SocialIdentityService {
	
	// Managed repository -----------------------------------
	
	@Autowired
	private SocialIdentityRepository socialIdentityRepository;
	
	// Supporting services ----------------------------------
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------
	
	public SocialIdentityService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------
	
	public SocialIdentity create(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		SocialIdentity result;
		
		result = new SocialIdentity();
		
		return result;
	}
	
	public SocialIdentity findOne(int socialIdentityID){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		SocialIdentity result;
		
		result = socialIdentityRepository.findOne(socialIdentityID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<SocialIdentity> findAll(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Collection<SocialIdentity> result;
		
		result = socialIdentityRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public SocialIdentity save(SocialIdentity socialIdentity){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Assert.notNull(socialIdentity);
		
		SocialIdentity result;
		
		result = socialIdentityRepository.save(socialIdentity);
		
		return result;
	}
	
	public void flush() {
		socialIdentityRepository.flush();
	}
	
	public void delete(SocialIdentity socialIdentity){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Assert.notNull(socialIdentity);
		Assert.isTrue(socialIdentity.getId()!=0);
		
		Assert.isTrue(socialIdentityRepository.exists(socialIdentity.getId()));
		
		socialIdentity.getActor().removeSocialIdentity(socialIdentity);
		
		socialIdentityRepository.delete(socialIdentity);
	}
	
	// Other business methods -------------------------------

}
