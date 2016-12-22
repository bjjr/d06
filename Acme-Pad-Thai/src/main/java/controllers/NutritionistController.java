package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import services.NutritionistService;
import domain.Folder;
import domain.Nutritionist;


@Controller
@RequestMapping("/nutritionist")
public class NutritionistController extends AbstractController{
	
	// Services -----------------------------------------------
	
	@Autowired
	private NutritionistService nutritionistService;
	
	@Autowired
	private FolderService folderService;
	
	// Constructors -------------------------------------------
	
	public NutritionistController(){
		super();
	}

	// Creating -----------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Nutritionist nutritionist;
		
		nutritionist = nutritionistService.create();
		result = createEditModelAndView(nutritionist);
		
		return result;
	}
	
	// Edition -----------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		Nutritionist nutritionist;
		
		nutritionist = nutritionistService.findByPrincipal();
		Assert.notNull(nutritionist);
		result = createEditModelAndView(nutritionist);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Nutritionist nutritionist, BindingResult binding){
		ModelAndView result;
		String password;
		String passwordEncoder;
		Md5PasswordEncoder encoder;
		Collection<Folder> folders;
		
		if (nutritionist.getId() == 0) {
			folders = folderService.createFolderObligatory(nutritionist);
			nutritionist.setFolders(folders);
		}
		
		encoder = new Md5PasswordEncoder();
		password = nutritionist.getUserAccount().getPassword();
		passwordEncoder = encoder.encodePassword(password, null);
		nutritionist.getUserAccount().setPassword(passwordEncoder);

		
		if(binding.hasErrors()){
			result = createEditModelAndView(nutritionist);
		}
		else{
			try{
				nutritionistService.save(nutritionist);
				result = new ModelAndView("redirect:/");
			}
			catch(Throwable oops){
				result = createEditModelAndView(nutritionist, "nutritionist.commit.error");
			}
		}
		
		return result;
	}
	
	// Ancillary methods -------------------------------------
	
	protected ModelAndView createEditModelAndView(Nutritionist nutritionist){
		ModelAndView result;
		
		result = createEditModelAndView(nutritionist, null);
			
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Nutritionist nutritionist, String message){
		ModelAndView result;
		Collection<Nutritionist> nutritionistCollection;
		
		nutritionistCollection = nutritionistService.findAll();
		
		result = new ModelAndView("nutritionist/edit");
		result.addObject("nutritionist", nutritionist);
		result.addObject("nutritionistCollection", nutritionistCollection);
		result.addObject("message", message);
		
		return result;
	}
	
}
