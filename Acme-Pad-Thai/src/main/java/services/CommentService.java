package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Comment;
import domain.Recipe;
import domain.SocialActor;

import repositories.CommentRepository;

@Service
@Transactional
public class CommentService {
	
	//Managed repository
	
	@Autowired
	private CommentRepository commentRepository;
	
	// Supporting services
	@Autowired
	private SocialActorService socialActorService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private RecipeService recipeService;
	
	//Constructors
	public CommentService(){
		super();
	}
	
	// Simple CRUD methods
	
	public Comment create(Recipe recipe){
		Assert.isTrue(actorService.checkAuthority("USER") || 
				actorService.checkAuthority("NUTRITIONIST"), "1");
		Assert.notNull(recipe);
		Assert.isTrue(recipe.getId()!=0, "2");
		
		Comment result;
		SocialActor sc;
		
		sc = socialActorService.findByPrincipal();
		result = new Comment();
		result.setRecipe(recipe);
		recipe.addComment(result);
		recipeService.save(recipe);
		result.setSocialActor(sc);
		sc.addComment(result);
		socialActorService.save(sc);
				
		return result;
	}
	
	public Comment save(Comment comment){
		Assert.isTrue(actorService.checkAuthority("USER") || 
				actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(comment);
		
		Comment result;
		Date moment;
		
		moment = new Date(System.currentTimeMillis()-1000);
		comment.setMoment(moment);
		
		result = commentRepository.save(comment);
		
		return result;
	}
	
	public void flush() {
		commentRepository.flush();
	}
	
	public void delete(Comment comment){
		Assert.notNull(comment);
		Assert.isTrue(comment.getId()!=0);
		Assert.isTrue(commentRepository.exists(comment.getId()));
		
		SocialActor sa;
		
		sa = comment.getSocialActor();
		sa.removeComment(comment);
		socialActorService.save(sa);
		
		commentRepository.delete(comment);
	}
	
	public Comment findOne(int id){
		Assert.notNull(id);
		Assert.isTrue(id != 0);
		
		Comment result;
		
		result = commentRepository.findOne(id);
		Assert.notNull(result);
		
		return result;
		
		
	}

}
