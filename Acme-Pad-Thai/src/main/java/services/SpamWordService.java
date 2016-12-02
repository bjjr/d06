package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.SpamWord;

import repositories.SpamWordRepository;

@Service
@Transactional
public class SpamWordService {
	
	// Managed repository -----------------------------------
	
	@Autowired
	private SpamWordRepository spamWordRepository;
	
	// Supporting services ----------------------------------
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------
	
	public SpamWordService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------
	
	public SpamWord create(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		SpamWord result;
		
		result = new SpamWord();
		
		return result;
	}
	
	public SpamWord findOne(int spamWordID){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		SpamWord result;
		
		result = spamWordRepository.findOne(spamWordID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<SpamWord> findAll(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Collection<SpamWord> result;
		
		result = spamWordRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public SpamWord save(SpamWord spamWord){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Assert.notNull(spamWord);
		
		SpamWord result;
		
		result = spamWordRepository.save(spamWord);
		
		return result;
	}
	
	public void flush() {
		spamWordRepository.flush();
	}
	
	public void delete(SpamWord spamWord){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Assert.notNull(spamWord);
		Assert.isTrue(spamWord.getId() != 0);
		
		Assert.isTrue(spamWordRepository.exists(spamWord.getId()));
		
		String word;
		
		word = spamWord.getWord();
		
		Assert.isTrue(!(word.equals("viagra") || word.equals("cialis") || word.equals("sex")
				|| word.equals("love")), "This spamword can't be deleted");
		
		spamWordRepository.delete(spamWord);
		
	}

}
