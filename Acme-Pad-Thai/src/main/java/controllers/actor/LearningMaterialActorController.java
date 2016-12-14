package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MasterClassService;
import controllers.AbstractController;
import domain.LearningMaterial;

@Controller
@RequestMapping("learningMaterial/actor")
public class LearningMaterialActorController extends AbstractController {

	// Services ----------------------------------------
	
	@Autowired
	private MasterClassService masterClassService;
	
	// Constructor -------------------------------------
	
	public LearningMaterialActorController() {
		super();
	}
	
	// Listing -----------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int masterClassId) {
		ModelAndView res;
		Collection<LearningMaterial> learningMaterials;
		String requestURI;
		
		requestURI = "learningMaterial/actor/list.do";
		learningMaterials = masterClassService.findLearningMaterials(masterClassId);
		
		res = new ModelAndView("learningMaterial/list");
		res.addObject("requestURI", requestURI);
		res.addObject("learningMaterials", learningMaterials);
		res.addObject("masterClassId", masterClassId);
		
		return res;
	}
}
