package services;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Payload;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.constraintvalidators.URLValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.Actor;
import domain.MasterClass;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	// Managed repository --------------------------------

	@Autowired
	private PresentationRepository presentationRepository;

	// Supporting services -------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private MasterClassService masterClassService;

	// Constructors --------------------------------------

	public PresentationService() {
		super();
	}

	// Simple CRUD methods -------------------------------

	public Presentation create() {
		Assert.isTrue(actorService.checkAuthority("COOK"));

		Presentation res;
		res = new Presentation();

		Collection<String> attachments;
		attachments = new ArrayList<String>();

		res.setAttachments(attachments);

		return res;
	}

	public Presentation save(Presentation p, int masterClassId) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(p);

		Presentation res;
		MasterClass masterClass;
		URLValidator validator = new URLValidator();
		validator.initialize(new URL() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public String regexp() {
				return null;
			}
			
			@Override
			public String protocol() {
				return null;
			}
			
			@Override
			public int port() {
				return -1;
			}
			
			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}
			
			@Override
			public String message() {
				return null;
			}
			
			@Override
			public String host() {
				return null;
			}
			
			@Override
			public Class<?>[] groups() {
				return null;
			}
			
			@Override
			public Flag[] flags() {
				return null;
			}
		});
		
		for (String url : p.getAttachments()) {
			if (validator.isValid(url, null) == false) {
				throw new IllegalArgumentException();
			}
		}
		
		masterClass = masterClassService.findOne(masterClassId);
		res = presentationRepository.save(p);
		masterClass.addLearningMaterial(res);
		masterClassService.save(masterClass);

		return res;
	}
	
	public void flush() {
		presentationRepository.flush();
	}

	public void delete(Presentation p, int masterClassId) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);
		Assert.isTrue(presentationRepository.exists(p.getId()));

		MasterClass masterClass;
		
		masterClass = masterClassService.findOne(masterClassId);
		masterClass.removeLearningMaterial(p);		
		presentationRepository.delete(p.getId());
		masterClassService.save(masterClass);
	}

	public Boolean exists(Presentation p) {
		Assert.notNull(p);

		Boolean res;
		res = presentationRepository.exists(p.getId());

		Assert.notNull(res);

		return res;
	}

	// Other business methods ----------------------------

	public Double findAvgNumPresentation() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Double res;
		res = presentationRepository.findAvgNumPresentation();

		return res;
	}
	
	public Presentation findOne(int masterClassId, int presentationId) {
		Assert.isTrue(actorService.checkAuthority("USER")
				|| actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("NUTRITIONIST")
				|| actorService.checkAuthority("SPONSOR")
				|| actorService.checkAuthority("COOK"));
		
		Presentation res;
		Actor principal;
		MasterClass masterClass;
		
		principal = actorService.findByPrincipal();
		masterClass = masterClassService.findOne(masterClassId);
		
		Assert.isTrue(principal.getMasterClasses().contains(masterClass)
					  || masterClass.getCook().equals(principal));
		
		res = presentationRepository.findOne(presentationId);
		Assert.notNull(res);
		
		return res;
	}
}
