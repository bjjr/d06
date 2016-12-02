package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.Nutritionist;
import domain.Priority;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class MessageServiceTest extends AbstractTest{
	
	// Service under test --------------------------------
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PriorityService priorityService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	@Autowired
	private FolderService folderService;
	
	// Tests ---------------------------------------------
	
	@Test
	public void testCreateMessage(){
		authenticate("nutritionist3");
		
		Message res;
		
		res = messageService.create();
		
		Assert.isTrue(res.getBody() == null);
		Assert.isTrue(res.getMoment() == null);
		Assert.isTrue(res.getPriority() == null);
		Assert.isTrue(res.getRecipients() == null);
		Assert.isTrue(res.getSender() == null);
		Assert.isTrue(res.getSubject() == null);
		
		unauthenticate();
	}
	
	@Test
	public void testFindOneMessage(){
		authenticate("nutritionist3");
		
		System.out.println("------- TEST FIND ONE -------");
		
		Message message;
		int id = 70;
		
		message = messageService.findOne(id);
		System.out.println("Message whose id is " + id + " found: " + message + "\n");
		
		unauthenticate();
	}
	
	@Test
	public void testFindAllMessage(){
		authenticate("nutritionist3");
		
		Collection<Message> messages;
		
		messages = messageService.findAll();
		
		System.out.println("------- TEST FIND ALL -------");
		
		System.out.println(messages.size() + " messages found \n");
		
		unauthenticate();
	}
	
	@Test
	public void testSaveMessage(){
		authenticate("nutritionist3");
		
		Message message;
		Message saved;
		Collection<Message> messages;
		Actor sender;
		Collection<Actor> recipients;
		Date moment;
		Priority priority;
		Nutritionist recipient;
		
		message = messageService.create();
		recipients = new ArrayList<Actor>();
		moment = new Date(System.currentTimeMillis()-1000);
		priority = priorityService.findOne(24);
		sender = actorService.findByPrincipal();
		recipient = nutritionistService.findOne(52);
		
		recipients.add(recipient);
		
		message.setBody("bodyTest");
		message.setMoment(moment);
		message.setPriority(priority);
		message.setRecipients(recipients);
		message.setSender(sender);
		message.setSubject("SubjectTest");
		
		saved = messageService.save(message);
		messageService.flush();
		messages = messageService.findAll();
		
		
		Assert.isTrue(messages.contains(saved));
		
		System.out.println("------- TEST SAVE -------");
		
		System.out.println("Message saved \n");
		
		unauthenticate();
	}
	
	@Test
	public void testDeleteMessage(){
		authenticate("nutritionist3");
		
		Message message;

		message = messageService.findOne(70);
		
		messageService.delete(message);
		
		System.out.println("------- TEST DELETE -------");
		
		System.out.println("Message deleted correctly \n");
		
		unauthenticate();
	}
	
	@Test
	public void testDeleteMessageToTrash(){
		authenticate("user1");
		
		Actor actor;
		Message message;
		
		actor = actorService.findByPrincipal();
		message = messageService.findOne(70);
		
		System.out.println("------- TEST DELETE MESSAGE TO TRASH -------");
		
		messageService.deleteMessageToTrash(actor, message);
		
		for(Folder f:actor.getFolders()){
			if(f.getName().equals("Trashbox")){
				if(f.getMessages().contains(message)){
					System.out.println("Message moved to Trashbox correctly");
				}
			}
			else{
				if(!f.getMessages().contains(message)){
					System.out.println("Message is not in " + f.getName());
				}
				else{
					System.out.println("Message is in " + f.getName());
				}
			}
		}
		
		System.out.println("\n");
		
		unauthenticate();
	}
	
	@Test
	public void testMoveMessage(){
		authenticate("user1");
		
		Actor actor;
		Folder folder;
		Message message;
		
		actor = actorService.findByPrincipal();
		message = messageService.findOne(70);
		folder = folderService.findOne(174);
		
		System.out.println("------- TEST MOVE MESSAGE -------");
		
		for(Folder f:actor.getFolders()){
			if(f.getMessages().contains(message)){
				System.out.println("Folder before move message: " + f.getName());
				break;
			}
		}
		
		messageService.moveMessage(folder, message, actor);
		
		for(Folder f:actor.getFolders()){
			if(f.getMessages().contains(message)){
				System.out.println("Folder after move message: " + f.getName() + "\n");
				break;
			}
		}
		
		unauthenticate();
	}
	
	@Test
	public void testSendMessage(){
		authenticate("user1");
		
		Actor actor;
		Message message;
		Collection<Actor> recipients;
		Nutritionist nutritionist;
		Nutritionist nutritionist1;
		
		actor = actorService.findByPrincipal();
		message = messageService.findOne(70);
		nutritionist = nutritionistService.findOne(52);
		nutritionist1 = nutritionistService.findOne(54);
		recipients = new ArrayList<Actor>();
		
		recipients.add(nutritionist);
		recipients.add(nutritionist1);
		
		messageService.sendMessage(message, actor, recipients);
		
		System.out.println("------- TEST SEND MESSAGE -------");
		
		for(Actor a:recipients){
			if(a.getReceivedMessages().contains(message)){
				System.out.println("Actor " + a.getName() + " received the message");
			}
		}
		
		System.out.println("\n");
		
		unauthenticate();
	}
	
	@Test
	public void testSendMessageWithSpam(){
		authenticate("user1");
		
		Actor actor;
		Message message;
		Collection<Actor> recipients;
		Nutritionist nutritionist;
		Nutritionist nutritionist1;
		
		actor = actorService.findByPrincipal();
		message = messageService.findOne(71);
		nutritionist = nutritionistService.findOne(52);
		nutritionist1 = nutritionistService.findOne(54);
		recipients = new ArrayList<Actor>();
		
		recipients.add(nutritionist);
		recipients.add(nutritionist1);
		
		messageService.sendMessage(message, actor, recipients);
		
		System.out.println("------- TEST SEND MESSAGE WITH SPAM-------");
		
		for(Actor a:recipients){
			if(a.getReceivedMessages().contains(message)){
				for(Folder f:a.getFolders()){
					if(f.getMessages().contains(message)){
						System.out.println("Actor " + a.getName() + " received the " +
								"message in the folder " + f.getName());
					}
				}
			}
		}
		
		System.out.println("\n");
		
		unauthenticate();
	}

}
