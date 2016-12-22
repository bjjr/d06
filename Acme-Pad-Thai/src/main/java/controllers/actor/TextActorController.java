package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TextService;
import controllers.AbstractController;
import domain.Text;

@Controller
@RequestMapping("/text2/actor")
public class TextActorController extends AbstractController {

	// Services ------------------------------------------
	
	@Autowired
	private TextService textService;
	
	// Constructor ---------------------------------------
	
	public TextActorController() {
		super();
	}
	
	// Display -------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int masterClassId, @RequestParam int textId) {
		ModelAndView res;
		Text text;
		
		text = textService.findOne(masterClassId, textId);
		
		res = new ModelAndView("text/display");
		res.addObject("masterClassId", masterClassId);
		res.addObject("text", text);
		
		return res;
	}
}
