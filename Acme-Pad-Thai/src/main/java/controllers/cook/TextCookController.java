package controllers.cook;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TextService;
import controllers.AbstractController;
import domain.Text;

@Controller
@RequestMapping("/text")
public class TextCookController extends AbstractController {

	// Services ------------------------------------------

	@Autowired
	private TextService textService;
	
	// Constructor ---------------------------------------

	public TextCookController() {
		super();
	}
	
	// Create --------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "add")
	public ModelAndView create(@RequestParam int masterClassId) {
		ModelAndView res;
		Text text;
		
		text = textService.create();
		
		res = createEditModelAndView(text);
		res.addObject("masterClassId", masterClassId);
		
		return res;
	}

	// Editing -------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "edit")
	public ModelAndView edit(@RequestParam int masterClassId,
			@RequestParam int textId) {
		ModelAndView res;
		Text text;

		text = textService.findOne(masterClassId, textId);

		res = createEditModelAndView(text);
		res.addObject("masterClassId", masterClassId);

		return res;
	}
	
	// Save ---------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Text text, @RequestParam int masterClassId, BindingResult binding) {
		ModelAndView res;
		
		if (binding.hasErrors()) {
			res = createEditModelAndView(text);
		} else {
			try {
				textService.save(text, masterClassId);
				res = new ModelAndView("redirect:/learningMaterial/cook/list.do?masterClassId=" + masterClassId);
			} catch (Throwable th) {
				res = createEditModelAndView(text, "text.commit.error");
			}
		}
		
		return res;
	}
	
	// Delete -------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView edit(Text text, @RequestParam int masterClassId, BindingResult binding) {
		ModelAndView res;
		
		try {
			textService.delete(text, masterClassId);
			res = new ModelAndView("redirect:/learningMaterial/cook/list.do?masterClassId=" + masterClassId);
		} catch (Throwable th) {
			res = createEditModelAndView(text, "text.commit.error");
		}
		
		return res;
	}

	// Ancillary methods --------------------------------

	protected ModelAndView createEditModelAndView(Text text) {
		ModelAndView res;

		res = createEditModelAndView(text, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Text text, String message) {
		ModelAndView res;

		res = new ModelAndView("text/edit");
		res.addObject("text", text);
		res.addObject("message", message);

		return res;
	}
}
