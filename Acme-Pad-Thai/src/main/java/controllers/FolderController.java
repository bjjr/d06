package controllers;

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

import domain.Actor;
import domain.Folder;

import services.ActorService;
import services.FolderService;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController{
	
	// Services -----------------------------------------------
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -------------------------------------------
	
	public FolderController(){
		super();
	}
	
	// Creating -----------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Folder folder;
		
		folder = folderService.create();
		result = createEditModelAndView(folder);
		
		return result;
	}
	
	// Listing -----------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Folder> folders;
		Actor actor;
		
		actor = actorService.findByPrincipal();
		folders = actor.getFolders();
		
		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/list.do");
		
		return result;
	}
	
	// Edition -----------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int folderId){
		ModelAndView result;
		Folder folder;
		
		folder = folderService.findOne(folderId);
		Assert.notNull(folder);
		result = createEditModelAndView(folder);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Folder folder, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(folder);
		}
		else{
			try{
				folderService.save(folder);
				result = new ModelAndView("redirect:list.do");
			}
			catch(Throwable oops){
				result = createEditModelAndView(folder, "folder.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Folder folder, BindingResult binding){
		ModelAndView result;
		
		try{
			folderService.delete(folder);
			result = new ModelAndView("redirect:list.do");
		}
		catch(Throwable oops){
			result = createEditModelAndView(folder, "folder.commit.error");
		}
		
		return result;
	}
	
	// Ancillary methods -------------------------------------
	
		protected ModelAndView createEditModelAndView(Folder folder){
			ModelAndView result;
			
			result = createEditModelAndView(folder, null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Folder folder, String message){
			ModelAndView result;
			Collection<Folder> folders;
			
			folders = folderService.findAll();
			
			result = new ModelAndView("folder/edit");
			result.addObject("folder", folder);
			result.addObject("folders", folders);
			result.addObject("message", message);
			
			return result;
		}

}
