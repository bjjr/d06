package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.SocialActor;

import repositories.SocialActorRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class SocialActorService {
	
	// Managed repository -----------------------------------
	
	@Autowired
	private SocialActorRepository socialActorRepository;
	
	// Supporting services ----------------------------------
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------
	
	public SocialActorService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------
	
	public SocialActor findOne(int socialActorID){
		SocialActor result;
		
		result = socialActorRepository.findOne(socialActorID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<SocialActor> findAll() {
		Collection<SocialActor> result;
		
		result = socialActorRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public SocialActor save(SocialActor socialActor){
		Assert.notNull(socialActor);
		
		SocialActor result;
		
		result = socialActorRepository.save(socialActor);
		
		return result;
	}
	
	public void flush() {
		socialActorRepository.flush();
	}
	
	// Other business methods -------------------------------
	
	public SocialActor findByPrincipal(){
		SocialActor result;
		UserAccount userAccount;
		
		result = null;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = socialActorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);
		
		return result;
	}
	
	public void followSocialActor(SocialActor current, SocialActor socialActor){
		Assert.isTrue(actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST"));
		Assert.isTrue(!socialActor.equals(current), "You can't follow yourself");
		
		Assert.isTrue(!current.getFollows().contains(socialActor), "You already follow this person");
		current.followSocialActor(socialActor);
		
	}
	
	public void unfollowSocialActor(SocialActor current, SocialActor socialActor){
		Assert.isTrue(actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST"));
		Assert.isTrue(!socialActor.equals(current), "You can't unfollow yourself");
		
		Assert.isTrue(current.getFollows().contains(socialActor), "You don´t follow this person");
		current.unfollowSocialActor(socialActor);
	}
	
}
