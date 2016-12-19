package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Folder;
import domain.Message;

import services.ActorService;
import services.FolderService;
import services.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController{
	
	// Services -----------------------------------------------
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -------------------------------------------
	
	public MessageController(){
		super();
	}
	
	// Creating -----------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Message message;
		
		message = messageService.create();
		result = createEditModelAndView(message);
		
		return result;
	}
	
	// List by folder ------------------------------------------
	
	@RequestMapping(value = "/listByFolder", method = RequestMethod.GET)
	public ModelAndView listByFolder(@RequestParam int folderId){
		ModelAndView result;
		Folder folder;
		Collection<Message> messages;
		Boolean outbox;
		Boolean trashbox;
		
		outbox = false;
		trashbox = false;
		folder = folderService.findOne(folderId);
		messages = folder.getMessages();
		
		if(folder.getName().equals("Trashbox")){
			trashbox = true;
		}
		if(folder.getName().equals("Outbox")){
			outbox = true;
		}
		
		result = new ModelAndView("message/listByFolder");
		result.addObject("messages", messages);
		result.addObject("outbox", outbox);
		result.addObject("trashbox", trashbox);
		result.addObject("folderId", folderId);
		result.addObject("requestURI", "message/listByFolder.do");
		
		return result;
	}
	
	@RequestMapping(value = "/moveToTrashbox", method = RequestMethod.GET)
	public ModelAndView moveToTrashbox(@RequestParam int messageId, @RequestParam int folderId){
		ModelAndView result;
		Actor actor;
		Message message;
		
		actor = actorService.findByPrincipal();
		message = messageService.findOne(messageId);
		messageService.deleteMessageToTrash(actor, message);
		
		result = new ModelAndView("redirect:listByFolder.do?folderId=" + folderId);
		
		return result;
	}
	
	// Ancillary methods -------------------------------------
	
	protected ModelAndView createEditModelAndView(Message messageDomain){
		ModelAndView result;
			
		result = createEditModelAndView(messageDomain, null);
				
		return result;
	}
			
	protected ModelAndView createEditModelAndView(Message messageDomain, String message){
		ModelAndView result;
		Collection<Message> messages;
				
		messages = messageService.findAll();
				
		result = new ModelAndView("message/create");
		result.addObject("messageDomain", messageDomain);
		result.addObject("messages", messages);
		result.addObject("message", message);
			
		return result;
	}

}
