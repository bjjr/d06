package services;

import java.util.ArrayList;
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
		Date momentCreated;
		Collection<Actor> recipients;
		
		result = new Message();
		momentCreated = new Date(System.currentTimeMillis() - 1000);
		recipients = new ArrayList<>();
		
		result.setMoment(momentCreated);
		result.setRecipients(recipients);
		
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
	
	// This method deletes a message from the Trashbox of the actor logged
	
	public void delete(Message message, Actor actor){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Assert.notNull(message);
		
		Collection<Folder> folders;
		Folder aux;
		
		folders = actor.getFolders();
		aux = folderService.create();
		
		for(Folder f:folders){
			if(f.getName().equals("Trashbox") && f.isObligatory()){
				aux = f;
				break;
			}
		}
		if(actor.getReceivedMessages().contains(message)){
			actor.getReceivedMessages().remove(message);
		}
		else if(actor.getSentMessages().contains(message)){
			actor.getSentMessages().remove(message);
		}
		actorService.save(actor);
		aux.removeMessage(message);
		folderService.save(aux);
		
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
		
		message.setSender(sender);
		message.setRecipients(recipients);
		message = save(message);
		
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
			a.getReceivedMessages().add(message);
			actorService.save(a);
			for(Folder f:a.getFolders()){
				if(isSpam){
					if(f.getName().equals("Spambox") && f.isObligatory()){
						f.addMessage(message);
						folderService.save(f);
						break;
					}
				}
				else{
					if(f.getName().equals("Inbox") && f.isObligatory()){
						f.addMessage(message);
						folderService.save(f);
						break;
					}
				}
			}
		}
		for(Folder fo:sender.getFolders()){
			if(fo.getName().equals("Outbox") && fo.isObligatory()){
				sender.getSentMessages().add(message);
				actorService.save(sender);
				fo.addMessage(message);
				folderService.save(fo);
				break;
			}
		}
	}
	
	public void moveMessage(Folder folder, Message message, Actor actor){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Assert.isTrue(actor.getFolders().contains(folder));
		
		Folder currentFolder;
		
		currentFolder = folderService.create();
		
		for(Folder f:actor.getFolders()){
			if(f.getMessages().contains(message)){
				currentFolder = f;
				break;
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
				folderService.save(f);
			}
			if(f.getName().equals("Trashbox")){
				f.addMessage(message);
				folderService.save(f);
			}
		}
	}
	
	public Collection<Actor> convertUsernameToActor(Collection<String> usernames){
		Collection<Actor> result;
		Collection<Actor> allActors;
		
		result = new ArrayList<Actor>();
		allActors = actorService.findAll();
		
		for(String s:usernames){
			for(Actor a:allActors){
				if(a.getUserAccount().getUsername().equals(s)){
					result.add(a);
				}
			}
		}
		
		return result;
	}
	
}
