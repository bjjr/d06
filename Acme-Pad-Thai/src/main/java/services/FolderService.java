package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;

import repositories.FolderRepository;

@Service
@Transactional
public class FolderService {
	
	// Managed repository -----------------------------------
	
	@Autowired
	private FolderRepository folderRepository;
	
	// Supporting services ----------------------------------
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------
	
	public FolderService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------
	
	public Folder create(){
		Folder result;
		
		result = new Folder();
		result.setObligatory(false);
		
		return result;
	}
	
	public Folder findOne(int folderID){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Folder result;
		
		result = folderRepository.findOne(folderID);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Folder> findAll(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Collection<Folder> result;
		
		result = folderRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}
	
	public Folder save(Folder folder){
		Assert.notNull(folder);
		
		Folder result;
		
		result = folderRepository.save(folder);
		
		return result;
	}
	
	public void flush() {
		folderRepository.flush();
	}
	
	public void delete(Folder folder){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || 
				actorService.checkAuthority("USER") ||
				actorService.checkAuthority("NUTRITIONIST") ||
				actorService.checkAuthority("SPONSOR") ||
				actorService.checkAuthority("COOK"));
		Assert.notNull(folder);
		Assert.isTrue(folder.getId()!=0);
		
		Assert.isTrue(folderRepository.exists(folder.getId()));
		
		Assert.isTrue(!folder.isObligatory(), "This folder is obligatory and can't be deleted");
		
		folder.getActor().removeFolder(folder);
		actorService.save(folder.getActor());
		folderRepository.delete(folder);
	}
	
	// Other business methods -------------------------------
	
	public Collection<Folder> createFolderObligatory(Actor a){
		Collection<Folder> result;
		Collection<Folder> obligatoryFolders;
		Folder f1, f2, f3, f4;

		result = new ArrayList<Folder>();
		obligatoryFolders = new ArrayList<Folder>();
		f1 = create();
		f2 = create();
		f3 = create();
		f4 = create();
		
		f1.setName("Inbox");
		f2.setName("Outbox");
		f3.setName("Trashbox");
		f4.setName("Spambox");
		
		obligatoryFolders.add(f1);
		obligatoryFolders.add(f2);
		obligatoryFolders.add(f3);
		obligatoryFolders.add(f4);
		
		for(Folder f:obligatoryFolders){
			f.setObligatory(true);
			f.setActor(a);
			result.add(f);
		}
		
		return result;
	}

}
