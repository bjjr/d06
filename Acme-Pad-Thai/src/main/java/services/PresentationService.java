package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Presentation;

import repositories.PresentationRepository;

@Service
@Transactional
public class PresentationService {

	// Managed repository --------------------------------

	@Autowired
	private PresentationRepository presentationRepository;

	// Supporting services -------------------------------

	@Autowired
	private ActorService actorService;

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

	public Presentation save(Presentation p) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(p);

		Presentation res;
		res = presentationRepository.save(p);

		return res;
	}
	
	public void flush() {
		presentationRepository.flush();
	}

	public void delete(Presentation p) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);
		Assert.isTrue(presentationRepository.exists(p.getId()));

		presentationRepository.delete(p.getId());
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
}
