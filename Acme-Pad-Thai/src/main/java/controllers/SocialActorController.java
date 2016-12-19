package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.SocialActor;

import services.SocialActorService;

@Controller
@RequestMapping("/socialActor")
public class SocialActorController extends AbstractController {

	// Services -----------------------------------------------

	@Autowired
	private SocialActorService socialActorService;

	// Constructors -------------------------------------------

	public SocialActorController() {
		super();
	}

	// Listing -----------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SocialActor> socialActors;
		Collection<SocialActor> follows;
		SocialActor principal;

		socialActors = socialActorService.findAll();
		principal = socialActorService.findByPrincipal();
		follows = principal.getFollows();
		socialActors.remove(principal);

		result = new ModelAndView("socialActor/list");
		result.addObject("socialActors", socialActors);
		result.addObject("follows", follows);
		result.addObject("requestURI", "socialActor/list.do");

		return result;
	}

	// Follow -------------------------------------------------

	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam int socialActorId) {
		ModelAndView result;
		SocialActor socialActorPrincipal;
		SocialActor socialActorToFollow;

		socialActorPrincipal = socialActorService.findByPrincipal();
		socialActorToFollow = socialActorService.findOne(socialActorId);

		try {
			socialActorService.followSocialActor(socialActorPrincipal, socialActorToFollow);
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "socialActor.commit.ok");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "socialActor.commit.error");
		}

		return result;
	}

	// Unfollow -----------------------------------------------

	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam int socialActorId) {
		ModelAndView result;
		SocialActor socialActorPrincipal;
		SocialActor socialActorToUnfollow;

		socialActorPrincipal = socialActorService.findByPrincipal();
		socialActorToUnfollow = socialActorService.findOne(socialActorId);

		try {
			socialActorService.unfollowSocialActor(socialActorPrincipal, socialActorToUnfollow);
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "socialActor.commit.ok");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "socialActor.commit.error");
		}

		return result;
	}

}
