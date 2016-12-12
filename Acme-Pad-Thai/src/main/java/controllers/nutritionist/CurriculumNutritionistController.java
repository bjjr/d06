package controllers.nutritionist;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Curriculum;
import domain.Nutritionist;

import services.CurriculumService;
import services.NutritionistService;

@Controller
@RequestMapping("/curriculum/nutritionist")
public class CurriculumNutritionistController extends AbstractController{
	
	// Services -----------------------------------------------
	
	@Autowired
	private CurriculumService curriculumService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	// Constructors -------------------------------------------
	
	public CurriculumNutritionistController(){
		super();
	}
	
	// Creating -----------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Curriculum curriculum;
		
		curriculum = curriculumService.create();
		result = createEditModelAndView(curriculum);
		
		return result;
	}
	
	// Listing ------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Curriculum curriculum;
		Nutritionist nutritionist;
		
		nutritionist = nutritionistService.findByPrincipal();
		curriculum = nutritionist.getCurriculum();
		
		result = new ModelAndView("curriculum/list");
		result.addObject("curriculum", curriculum);
		result.addObject("requestURI", "curriculum/nutritionist/list.do");
		
		return result;
	}
	
	// Edition -----------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int curriculumId){
		ModelAndView result;
		Curriculum curriculum;
		
		curriculum = curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);
		result = createEditModelAndView(curriculum);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Curriculum curriculum, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(curriculum);
		}
		else{
			try{
				curriculumService.save(curriculum);
				result = new ModelAndView("redirect:list.do");
			}
			catch(Throwable oops){
				result = createEditModelAndView(curriculum, "curriculum.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Curriculum curriculum, BindingResult binding){
		ModelAndView result;
		
		try{
			curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:list.do");
		}
		catch(Throwable oops){
			result = createEditModelAndView(curriculum, "curriculum.commit.error");
		}
		
		return result;
	}
	
	// Ancillary methods -------------------------------------
	
	protected ModelAndView createEditModelAndView(Curriculum curriculum){
		ModelAndView result;
		
		result = createEditModelAndView(curriculum, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Curriculum curriculum, String message){
		ModelAndView result;
		Collection<Curriculum> curricula;
		
		curricula = curriculumService.findAll();
		
		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("curricula", curricula);
		result.addObject("message", message);
		
		return result;
	}

}
