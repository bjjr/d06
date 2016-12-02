package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.SpamWord;

import repositories.MessageRepository;

@Service
@Transactional
public class MessageService {
	
	// Managed repository -----------------------------------
	
	@Autowired
	private MessageRepository messageRepository;
	
	// Supporting services ----------------------------------
	
	@Autowired
	private SpamWordService spamWordService;
	
	// Supporting services ----------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private FolderService folderService;
	
	// Constructors -----------------------------------------
	
	public MessageService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------
	
	public Message create(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Message result;
		
		result = new Message();
		
		return result;
	}
	
	public Message findOne(int messageID){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Message result;
		
		result = messageRepository.findOne(messageID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Message> findAll(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Collection<Message> result;
		
		result = messageRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public Message save(Message message){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Assert.notNull(message);
		
		Message result;
		Date moment;
		
		moment = new Date(System.currentTimeMillis()-1000);
		message.setMoment(moment);
		
		result = messageRepository.save(message);
		
		return result;
	}
	
	public void flush() {
		messageRepository.flush();
	}
	
	// This method deletes a message from the database
	
	public void delete(Message message){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		
		Assert.isTrue(messageRepository.exists(message.getId()));
		
		messageRepository.delete(message);
	}
	
	// Other business methods -------------------------------
	
	public void sendMessage(Message message, Actor sender, Collection<Actor> recipients){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		
		String body = message.getBody();
		Collection<SpamWord> spamWords;
		boolean isSpam;
		
		spamWords = spamWordService.findAll();
		isSpam = false;
		body = body.toLowerCase();
		
		for(SpamWord spam:spamWords){
			if(body.contains(spam.getWord())){
				isSpam = true;
				break;
			}
		}
		
		for(Actor a:recipients){
			for(Folder f:a.getFolders()){
				if(isSpam){
					if(f.getName().equals("Spambox")){
						a.getReceivedMessages().add(message);
						f.addMessage(message);
					}
				}
				else{
					if(f.getName().equals("Inbox")){
						a.getReceivedMessages().add(message);
						f.addMessage(message);
					}
				}
			}
		}
		for(Folder fo:sender.getFolders()){
			if(fo.getName().equals("Outbox")){
				sender.getSentMessages().add(message);
				fo.addMessage(message);
			}
		}
	}
	
	public void moveMessage(Folder folder, Message message, Actor actor){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		
		Folder currentFolder;
		
		currentFolder = folderService.create();
		
		for(Folder f:actor.getFolders()){
			if(f.getMessages().contains(message)){
				currentFolder = f;
			}
		}

		folder.addMessage(message);
		currentFolder.removeMessage(message);
		
		folderService.save(folder);
		folderService.save(currentFolder);
	}
	
	// This method deletes a message, sending it to Trashbox folder
	
	public void deleteMessageToTrash(Actor actor, Message message){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		
		for(Folder f:actor.getFolders()){
			if(f.getMessages().contains(message) && !f.getName().equals("Trashbox")){
				f.getMessages().remove(message);
			}
			if(f.getName().equals("Trashbox")){
				f.addMessage(message);
			}
		}
	}
	
}
