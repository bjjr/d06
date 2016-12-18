package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TextRepository;
import domain.Actor;
import domain.MasterClass;
import domain.Text;

@Service
@Transactional
public class TextService {

	// Managed repository --------------------------------
	
	@Autowired
	private TextRepository textRepository;
	
	// Supporting services -------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private MasterClassService masterClassService;
	
	// Constructors --------------------------------------
	
	public TextService() {
		super();
	}
	
	// Simple CRUD methods -------------------------------
	
	public Text create() {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		
		Text res;
		res = new Text();
		
		Collection<String> attachments;
		attachments = new ArrayList<String>();
		
		res.setAttachments(attachments);
		
		return res;
	}
	
	public Text save(Text t, int masterClassId) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(t);
		
		Text res;
		
		res = textRepository.save(t);
		
		return res;
	}
	
	public void flush() {
		textRepository.flush();
	}
	
	public void delete(Text t, int masterClassId) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(t);
		Assert.isTrue(t.getId() != 0);
		Assert.isTrue(textRepository.exists(t.getId()));
		
		MasterClass masterClass;
		
		masterClass = masterClassService.findOne(masterClassId);
		masterClass.removeLearningMaterial(t);	
		textRepository.delete(t);
		masterClassService.save(masterClass);
	}
	
	public Boolean exists(Text t) {
		Assert.notNull(t);
		
		Boolean res;
		res = textRepository.exists(t.getId());
		
		Assert.notNull(res);
		
		return res;
	}
	
	// Other business methods ----------------------------
	
	public Double findAvgNumText() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		
		Double res;
		res = textRepository.findAvgNumText();
		
		return res;
	}
	
	public Text findOne(int masterClassId, int textId) {
		Assert.isTrue(actorService.checkAuthority("USER")
				|| actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("NUTRITIONIST")
				|| actorService.checkAuthority("SPONSOR")
				|| actorService.checkAuthority("COOK"));
		
		Text res;
		Actor principal;
		MasterClass masterClass;
		
		principal = actorService.findByPrincipal();
		masterClass = masterClassService.findOne(masterClassId);
		
		Assert.isTrue(principal.getMasterClasses().contains(masterClass)
					  || masterClass.getCook().equals(principal));
		
		res = textRepository.findOne(textId);
		Assert.notNull(res);
		
		return res;
	}
}
