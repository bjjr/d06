package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.LikeSA;
import domain.Recipe;
import domain.SocialActor;
import domain.User;

import repositories.LikeSARepository;

@Service
@Transactional
public class LikeSAService {
	
	//Managed repository
	@Autowired
	private LikeSARepository likeSARepository;
	
	// Supporting services
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private SocialActorService socialActorService;
	
	@Autowired
	private UserService userService;
	
	//Constructors
	public LikeSAService(){
		super();
	}
	
	// Simple CRUD methods
	public LikeSA create(Recipe recipe){
		Assert.isTrue(actorService.checkAuthority("USER") || 
				actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(recipe);
		Assert.isTrue(recipe.getId()!=0);
		
		LikeSA result;
		SocialActor sc;
		
		sc = socialActorService.findByPrincipal();
		
		if(actorService.checkAuthority("USER")){
			User u;
			
			u = userService.findByPrincipal();
			
			Assert.isTrue(!u.getRecipes().contains(recipe));
		}
		
		for(LikeSA l : recipe.getLikesSA()){
			Assert.isTrue(l.getSocialActor() != sc);
		}
		
		result = new LikeSA();
		result.setRecipe(recipe);
		recipe.addLike(result);
		recipeService.save(recipe);
		result.setSocialActor(sc);
		sc.addLike(result);
		socialActorService.save(sc);
		
		return result;
	}
	
	public LikeSA save(LikeSA likeSA){
		Assert.isTrue(actorService.checkAuthority("USER") || 
				actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(likeSA);
		
		LikeSA result;
		
		result = likeSARepository.save(likeSA);
		
		return result;
	}
	
	public void flush() {
		likeSARepository.flush();
	}
	
	public void delete(LikeSA likeSA){
		Assert.notNull(likeSA);
		Assert.isTrue(likeSA.getId()!=0);
		Assert.isTrue(likeSARepository.exists(likeSA.getId()));
		
		SocialActor sa;
		
		sa = likeSA.getSocialActor();
		sa.removeLike(likeSA);
		socialActorService.save(sa);
		
		likeSARepository.delete(likeSA);
	}
	
	public LikeSA findOne(int id){
		Assert.notNull(id);
		Assert.isTrue(id!=0);
		
		LikeSA result;
		
		result = likeSARepository.findOne(id);
		Assert.notNull(result);
		
		return result;
	}
	

}
