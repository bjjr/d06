package controllers.cook;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MasterClassService;
import controllers.AbstractController;
import domain.MasterClass;

@Controller
@RequestMapping("/masterClass/cook")
public class MasterClassCookController extends AbstractController {

	// Services ----------------------------------------------
	@Autowired
	private MasterClassService masterClassService;

	// Constructor -------------------------------------------
	public MasterClassCookController() {
		super();
	}

	// Listing -----------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		String requestURI;
		Collection<MasterClass> masterClasses;

		requestURI = "masterClass/cook/list.do";
		masterClasses = masterClassService.findAllByCook();

		res = new ModelAndView("masterClass/list");
		res.addObject("requestURI", requestURI);
		res.addObject("masterClasses", masterClasses);
		res.addObject("registered", null);

		return res;
	}

	// Creating ----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		MasterClass masterClass;

		masterClass = masterClassService.create();

		res = createEditModelAndView(masterClass);

		return res;
	}

	// Editing -----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int masterClassId) {
		ModelAndView res;
		MasterClass masterClass;

		masterClass = masterClassService.findOne(masterClassId);
		res = createEditModelAndView(masterClass);

		return res;
	}

	// Saving ------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MasterClass masterClass, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = createEditModelAndView(masterClass);
		} else {
			try {
				masterClassService.save(masterClass);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable th) {
				res = createEditModelAndView(masterClass, "masterClass.commit.error");
			}
		}

		return res;
	}

	// Deleting ----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(MasterClass masterClass, BindingResult binding) {
		ModelAndView res;

		try {
			masterClassService.delete(masterClass);
			res = new ModelAndView("redirect:list.do");
		} catch (Throwable th) {
			res = createEditModelAndView(masterClass, "masterClass.commit.error");
		}

		return res;
	}

	// Ancillary methods -------------------------------------

	protected ModelAndView createEditModelAndView(MasterClass masterClass) {
		ModelAndView res;

		res = createEditModelAndView(masterClass, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(MasterClass masterClass, String message) {
		ModelAndView res;

		res = new ModelAndView("masterClass/edit");
		res.addObject("masterClass", masterClass);
		res.addObject("message", message);

		return res;
	}

}
