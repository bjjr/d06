package controllers.nutritionist;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;

import controllers.AbstractController;
import domain.Curriculum;
import domain.Endorser;

@Controller
@RequestMapping("/endorser/nutritionist")
public class EndorserNutritionistController extends AbstractController{
	
	// Services -----------------------------------------------
	
	@Autowired
	private CurriculumService curriculumService;
	
	// Constructors -------------------------------------------
	
	public EndorserNutritionistController(){
		super();
	}
	
	// List by curriculum -------------------------------------
	
	@RequestMapping(value = "/listByCurriculum", method = RequestMethod.GET)
	public ModelAndView listByCurriculum(@RequestParam int curriculumId){
		ModelAndView result;
		Curriculum curriculum;
		Collection<Endorser> endorsers;
		
		curriculum = curriculumService.findOne(curriculumId);
		endorsers = curriculum.getEndorsers();
		
		result = new ModelAndView("endorser/listByCurriculum");
		result.addObject("endorsers", endorsers);
		result.addObject("requestURI", "endorser/nutritionist/listByCategory.do");
		
		return result;
	}

}
