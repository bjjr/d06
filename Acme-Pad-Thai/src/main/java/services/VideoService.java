package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Video;

import repositories.VideoRepository;

@Service
@Transactional
public class VideoService {

	// Managed repository --------------------------------
	
	@Autowired
	private VideoRepository videoRepository;
	
	// Supporting services -------------------------------
	
	@Autowired
	private ActorService actorService;
	
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
	
	public Video save(Video v) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(v);
		
		Video res;
		res = videoRepository.save(v);
		
		return res;
	}
	
	public void delete(Video v) {
		Assert.isTrue(actorService.checkAuthority("COOK"));
		Assert.notNull(v);
		Assert.isTrue(v.getId() != 0);
		Assert.isTrue(videoRepository.exists(v.getId()));
		
		videoRepository.delete(v);
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
}
