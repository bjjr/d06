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

import repositories.VideoRepository;
import domain.Actor;
import domain.MasterClass;
import domain.Video;

@Service
@Transactional
public class VideoService {

	// Managed repository --------------------------------
	
	@Autowired
	private VideoRepository videoRepository;
	
	// Supporting services -------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private MasterClassService masterClassService;
	
	// Constructors --------------------------------------
	
	public VideoService() {
		super();
	}
	
	// Simple CRUD methods -------------------------------
	
	public Video create() {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		
		Video res;
		res = new Video();
		
		Collection<String> attachments;
		attachments = new ArrayList<String>();
		
		res.setAttachments(attachments);
		
		return res;
	}
	
	public Video save(Video v, int masterClassId) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(v);
		
		Video res;
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
		
		for (String url : v.getAttachments()) {
			if (validator.isValid(url, null) == false) {
				throw new IllegalArgumentException();
			}
		}
		
		masterClass = masterClassService.findOne(masterClassId);
		res = videoRepository.save(v);
		masterClass.addLearningMaterial(res);
		masterClassService.save(masterClass);
		
		return res;
	}
	
	public void delete(Video v, int masterClassId) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(v);
		Assert.isTrue(v.getId() != 0);
		Assert.isTrue(videoRepository.exists(v.getId()));
		
		MasterClass masterClass;
		
		masterClass = masterClassService.findOne(masterClassId);
		masterClass.removeLearningMaterial(v);
		videoRepository.delete(v);
		masterClassService.save(masterClass);
	}
	
	public void flush() {
		videoRepository.flush();
	}
	
	public Boolean exists(Video v) {
		Assert.notNull(v);
		
		Boolean res;
		res = videoRepository.exists(v.getId());
		
		Assert.notNull(res);
		
		return res;
	}
	
	// Other business methods ----------------------------
	
	public Double findAvgNumVideo() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		
		Double res;
		res = videoRepository.findAvgNumVideo();
		
		return res;
	}
	
	public Video findOne(int masterClassId, int videoId) {
		Assert.isTrue(actorService.checkAuthority("USER")
				|| actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("NUTRITIONIST")
				|| actorService.checkAuthority("SPONSOR")
				|| actorService.checkAuthority("COOK"));
		
		Video res;
		Actor principal;
		MasterClass masterClass;
		
		principal = actorService.findByPrincipal();
		masterClass = masterClassService.findOne(masterClassId);
		
		Assert.isTrue(principal.getMasterClasses().contains(masterClass)
					  || masterClass.getCook().equals(principal));
		
		res = videoRepository.findOne(videoId);
		Assert.notNull(res);
		
		return res;
	}
}
