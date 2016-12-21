package controllers.administrator;

import java.util.ArrayList;
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

import services.SpamWordService;

import controllers.AbstractController;
import domain.SpamWord;

@Controller
@RequestMapping("/spamWord/administrator")
public class SpamWordController extends AbstractController{
	
	// Services -----------------------------------------------
	
	@Autowired
	private SpamWordService spamWordService;
	
	// Constructors -------------------------------------------
	
	public SpamWordController(){
		super();
	}
	
	// Register -----------------------------------------------
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(){
		ModelAndView result;
		SpamWord spamWord;
		
		spamWord = spamWordService.create();
		result = createEditModelAndView(spamWord);
		
		return result;
	}
	
	// Listing -----------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<SpamWord> spamWords;
		Collection<SpamWord> obligatorySpamWords;
		
		spamWords = spamWordService.findAll();
		obligatorySpamWords = new ArrayList<SpamWord>();
		
		for(SpamWord sw:spamWords){
			if(sw.getWord().equals("Viagra") || sw.getWord().equals("Cialis")
					|| sw.getWord().equals("Sex") || sw.getWord().equals("Love")){
				obligatorySpamWords.add(sw);
			}
		}
		
		result = new ModelAndView("spamWord/list");
		result.addObject("spamWords", spamWords);
		result.addObject("obligatorySpamWords", obligatorySpamWords);
		result.addObject("requestURI", "spamWord/administrator/list.do");
		
		return result;
	}
	
	// Edition -----------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int spamWordId){
		ModelAndView result;
		SpamWord spamWord;
			
		spamWord = spamWordService.findOne(spamWordId);
		Assert.notNull(spamWord);
		result = createEditModelAndView(spamWord);
			
		return result;
	}
		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SpamWord spamWord, BindingResult binding){
		ModelAndView result;
			
		if(binding.hasErrors()){
			result = createEditModelAndView(spamWord);
		}
		else{
			try{
				spamWordService.save(spamWord);
				result = new ModelAndView("redirect:list.do");
			}
			catch(Throwable oops){
				result = createEditModelAndView(spamWord, "spamWord.commit.error");
			}
		}
			
		return result;
	}
		
	// Ancillary methods -------------------------------------
	
	protected ModelAndView createEditModelAndView(SpamWord spamWord){
		ModelAndView result;
				
		result = createEditModelAndView(spamWord, null);
					
		return result;
	}
				
	protected ModelAndView createEditModelAndView(SpamWord spamWord, String message){
		ModelAndView result;
		Collection<SpamWord> spamWords;
					
		spamWords = spamWordService.findAll();
					
		result = new ModelAndView("spamWord/edit");
		result.addObject("spamWord", spamWord);
		result.addObject("spamWords", spamWords);
		result.addObject("message", message);
				
		return result;
	}

}
