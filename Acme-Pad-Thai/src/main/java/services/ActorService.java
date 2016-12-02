package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ActorService {
	
	// Managed repository ---------------------------
	
	@Autowired
	private ActorRepository actorRepository;
	
	// Supporting services --------------------------
	
	// Constructors ---------------------------------
	
	public ActorService() {
		super();
	}
	
	// Simple CRUD methods --------------------------
	
	public Actor findOne(Integer actorId) {
		Assert.isTrue(actorId != 0);
		
		Actor res;
		res = actorRepository.findOne(actorId);
		
		Assert.notNull(res);
		
		return res;
	}
	
	public Actor save(Actor a) {
		Assert.notNull(a);
		
		Actor res;
		res = actorRepository.save(a);
		
		return res;
	}
	
	public void flush() {
		actorRepository.flush();
	}
	
	// Other business methods -----------------------
	
	public Actor findByPrincipal(){
		Actor result = null;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		
		return result;
	}
	
	public boolean checkAuthority(String authority){
		boolean result;
		Actor actor;
		Collection<Authority> authorities;
		result = false;

		try {
			actor = this.findByPrincipal();
			authorities = actor.getUserAccount().getAuthorities();
			
			for (Authority a : authorities) {
				if(a.getAuthority().equals(authority.toUpperCase())){
					result = true;
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			result = false;
		}
		
		return result;
	}
	
	public Actor findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		
		Actor res;
		res = actorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(res);

		return res;
	}
	
}
