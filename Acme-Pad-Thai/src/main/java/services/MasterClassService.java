package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MasterClassRepository;
import domain.Actor;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;
import domain.Message;
import domain.Priority;

@Service
@Transactional
public class MasterClassService {

	// Managed repository ---------------------------

	@Autowired
	private MasterClassRepository masterClassRepository;

	// Supporting services --------------------------

	@Autowired
	private CookService cookService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PriorityService priorityService;

	// Constructors --------------------------------

	public MasterClassService() {
		super();
	}

	// Simple CRUD methods -------------------------

	public MasterClass create() {
		Assert.isTrue(actorService.checkAuthority("COOK"));

		MasterClass res;
		Cook c;

		c = cookService.findByPrincipal();

		res = new MasterClass();
		res.setCook(c);

		return res;
	}
	
	public MasterClass findOne(Integer masterClassId) {
		Assert.isTrue(masterClassId != 0);
		
		MasterClass res;
		res = masterClassRepository.findOne(masterClassId);
		
		Assert.notNull(res);
		
		return res;
	}
	
	public Boolean exists(Integer masterClassId) {
		Assert.isTrue(masterClassId != 0);
		
		Boolean res;
		res = masterClassRepository.exists(masterClassId);
		
		Assert.notNull(res);
		
		return res;
	}

	public Collection<MasterClass> findAllByCook() {
		//TODO añadir a la lista de cambios
		Assert.isTrue(actorService.checkAuthority("COOK") ||
					  actorService.checkAuthority("ADMINISTRATOR"));

		Collection<MasterClass> res;
		Cook c;

		c = cookService.findByPrincipal();

		res = masterClassRepository.findAllByCook(c.getId());

		return res;
	}

	public Collection<String> findAllForNonUsers() {
		Assert.isTrue(!(actorService.checkAuthority("USER")
				|| actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("COOK")
				|| actorService.checkAuthority("SPONSOR") || actorService
				.checkAuthority("NUTRITIONIST")));

		Collection<String> res;
		res = new ArrayList<String>();

		Collection<MasterClass> masterClasses;
		masterClasses = masterClassRepository.findAll();

		for (MasterClass m : masterClasses) {
			res.add("Cook: " + m.getCook().getName() + " | Title: "
					+ m.getTitle() + " | Description: " + m.getDescription());
		}

		return res;
	}

	public MasterClass save(MasterClass m) {
		// TODO Añadir a lista de cambios
		Assert.isTrue(actorService.checkAuthority("COOK") ||
					  actorService.checkAuthority("ADMINISTRATOR"));
		Assert.notNull(m, "The masterClass to be saved must cannot be null.");

		MasterClass res;
		res = masterClassRepository.save(m);

		return res;
	}
	
	public void flush() {
		masterClassRepository.flush();
	}

	public void delete(MasterClass m) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(m, "The master class to be deleted cannot be null.");
		Assert.isTrue(masterClassRepository.exists(m.getId()));

		Collection<Actor> attenders;
		attenders = findAttenders(m);

		Cook c;
		c = m.getCook();
		c.removeMasterClass(m);

		Message msg;
		msg = sendDeletionMessage(m, attenders);
		
		removeAttenders(m, attenders);
		
		// Commit changes to the database
		messageService.save(msg);
		actorService.save(c);
		for (Actor a : attenders) {
			actorService.save(a);
		}
		
		masterClassRepository.delete(m);
	}

	private Message sendDeletionMessage(MasterClass m, Collection<Actor> attenders) {
		Message res;

		res = messageService.create();

		res.setSubject("The master class " + m.getTitle() + " was removed");
		res.setBody("The master class " + m.getTitle() + " was removed"
				+ " by the cook and you will be no longer able"
				+ " to access its contents");

		 Priority p = priorityService.findOne(23);
		 res.setPriority(p);

		messageService.sendMessage(res, actorService.findByPrincipal(), attenders);
		
		return res;
	}

	private void removeAttenders(MasterClass m, Collection<Actor> attenders) {
		for (Actor a : attenders) {
			a.removeMasterClass(m);
		}
	}

	// Other business methods ----------------------

	public void registerActor(MasterClass m) {
		Assert.isTrue(actorService.checkAuthority("USER")
				|| actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("NUTRITIONIST")
				|| actorService.checkAuthority("SPONSOR")
				|| actorService.checkAuthority("COOK"));
		Assert.notNull(m);

		Actor a;
		a = actorService.findByPrincipal();

		a.addMasterClass(m);
	}

	public Collection<Actor> findAttenders(MasterClass m) {
		Assert.notNull(m);

		Collection<Actor> res;
		res = masterClassRepository.findAttenders(m.getId());

		return res;
	}

	public Long findNumPromotedMasterClasses() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Long res;

		res = masterClassRepository.findNumPromotedMasterClasses();
		Assert.notNull(res);

		return res;
	}

	public Integer findMinNumMasterClassesPerCook() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Integer res;

		res = masterClassRepository.findMinNumMasterClassesPerCook();
		Assert.notNull(res);

		return res;
	}

	public Integer findMaxNumMasterClassesPerCook() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Integer res;

		res = masterClassRepository.findMaxNumMasterClassesPerCook();
		Assert.notNull(res);

		return res;
	}

	public Double findAvgNumMasterClassesPerCook() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Double res;

		res = masterClassRepository.findAvgNumMasterClassesPerCook();
		Assert.notNull(res);

		return res;
	}

	public Double findStddevNumMasterClassesPerCook() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Double res;

		res = masterClassRepository.findStddevNumMasterClassesPerCook();
		Assert.notNull(res);

		return res;
	}

	public void promoteMasterClass(MasterClass m) {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Assert.notNull(m);
		Assert.isTrue(masterClassRepository.exists(m.getId()));

		m.setPromoted(true);
	}

	public void demoteMasterClass(MasterClass m) {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Assert.notNull(m);
		Assert.isTrue(masterClassRepository.exists(m.getId()));

		m.setPromoted(false);
	}

	public void addLearningMaterial(MasterClass m, LearningMaterial l) {
		Assert.notNull(m);
		Assert.notNull(l);

		m.addLearningMaterial(l);
		save(m);
	}

	public void removeLearningMaterial(MasterClass m, LearningMaterial l) {
		Assert.notNull(m);
		Assert.notNull(l);

		m.removeLearningMaterial(l);
		save(m);
	}

}
