package controllers.cook;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PresentationService;
import controllers.AbstractController;
import domain.Presentation;

@Controller
@RequestMapping("/presentation")
public class PresentationCookController extends AbstractController {

	// Services ------------------------------------------

	@Autowired
	private PresentationService presentationService;
	
	// Constructor ---------------------------------------

	public PresentationCookController() {
		super();
	}
	
	// Create --------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int masterClassId) {
		ModelAndView res;
		Presentation presentation;
		
		presentation = presentationService.create();
		
		res = createEditModelAndView(presentation, masterClassId);
		
		return res;
	}

	// Editing -------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int masterClassId, @RequestParam int presentationId) {
		ModelAndView res;
		Presentation presentation;

		presentation = presentationService.findOne(masterClassId, presentationId);

		res = createEditModelAndView(presentation, masterClassId);

		return res;
	}
	
	// Save ---------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Presentation presentation, BindingResult binding, @RequestParam int masterClassId) {
		ModelAndView res;
		
		if (binding.hasErrors()) {
			res = createEditModelAndView(presentation, masterClassId);
		} else {
			try {
				presentationService.save(presentation, masterClassId);
				res = new ModelAndView("redirect:/learningMaterial/cook/list.do?masterClassId=" + masterClassId);
			} catch (Throwable th) {
				res = createEditModelAndView(presentation, masterClassId, "presentation.commit.error");
			}
		}
		
		return res;
	}
	
	// Delete -------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView edit(Presentation presentation, BindingResult binding, @RequestParam int masterClassId) {
		ModelAndView res;
		
		try {
			presentationService.delete(presentation, masterClassId);
			res = new ModelAndView("redirect:/learningMaterial/cook/list.do?masterClassId=" + masterClassId);
		} catch (Throwable th) {
			res = createEditModelAndView(presentation, masterClassId, "presentation.commit.error");
		}
		
		return res;
	}

	// Ancillary methods --------------------------------

	protected ModelAndView createEditModelAndView(Presentation presentation, int masterClassId) {
		ModelAndView res;

		res = createEditModelAndView(presentation, masterClassId, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Presentation presentation, int masterClassId, String message) {
		ModelAndView res;

		res = new ModelAndView("presentation/edit");
		res.addObject("presentation", presentation);
		res.addObject("message", message);
		res.addObject("masterClassId", masterClassId);

		return res;
	}
}
