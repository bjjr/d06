package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Nutritionist;
import domain.SocialActor;

import services.NutritionistService;
import services.SocialActorService;

@Controller
@RequestMapping("/socialActor")
public class SocialActorController extends AbstractController{
	
	// Services -----------------------------------------------
	
	@Autowired
	private SocialActorService socialActorService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	// Constructors -------------------------------------------
	
	public SocialActorController(){
		super();
	}
	
	// Follow -------------------------------------------------
	
	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam int socialActorId){
		ModelAndView result;
		SocialActor socialActorPrincipal;
		SocialActor socialActorToFollow;
		Collection<Nutritionist> nutritionists;
		
		nutritionists = nutritionistService.findAll();
		socialActorPrincipal = socialActorService.findByPrincipal();
		socialActorToFollow = socialActorService.findOne(socialActorId);
		
		try{
			socialActorService.followSocialActor(socialActorPrincipal, socialActorToFollow);
			result = new ModelAndView("nutritionist/list");
			result.addObject("requestURI", "nutritionist/list.do");
			result.addObject("nutritionists", nutritionists);
			result.addObject("message", "nutritionist.commit.ok");
		}
		catch(Throwable oops){
			result = new ModelAndView("nutritionist/list");
			result.addObject("requestURI", "nutritionist/list.do");
			result.addObject("nutritionists", nutritionists);
			result.addObject("message", "nutritionist.commit.error");
		}
		
		return result;
	}
	
	// Unfollow -----------------------------------------------
	
	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam int socialActorId){
		ModelAndView result;
		SocialActor socialActorPrincipal;
		SocialActor socialActorToUnfollow;
		Collection<Nutritionist> nutritionists;
		
		nutritionists = nutritionistService.findAll();
		socialActorPrincipal = socialActorService.findByPrincipal();
		socialActorToUnfollow = socialActorService.findOne(socialActorId);
		
		try{
			socialActorService.unfollowSocialActor(socialActorPrincipal, socialActorToUnfollow);
			result = new ModelAndView("nutritionist/list");
			result.addObject("requestURI", "nutritionist/list.do");
			result.addObject("nutritionists", nutritionists);
			result.addObject("message", "nutritionist.commit.ok");
		}
		catch(Throwable oops){
			result = new ModelAndView("nutritionist/list");
			result.addObject("requestURI", "nutritionist/list.do");
			result.addObject("nutritionists", nutritionists);
			result.addObject("message", "nutritionist.commit.error");
		}
		
		return result;
	}

}
