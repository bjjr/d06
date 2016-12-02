package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Category;

import repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService {
	
	//Managed repository
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	// Supporting services
	@Autowired
	private ActorService actorService;
	
	//Constructors
	public CategoryService(){
		super();
	}
	
	// Simple CRUD methods
	
	public Category create(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		
		Category result;
		
		result = new Category();
		
		return result;
	}
	
	public Category save(Category category){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || actorService.checkAuthority("USER"));
		Assert.notNull(category);
		
		Category result;
		
		result = categoryRepository.save(category);
		
		return result;
		
	}
	
	public void flush() {
		categoryRepository.flush();
	}
	
	public void delete(Category category){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Assert.notNull(category);
		Assert.isTrue(category.getId()!=0);
		Assert.isTrue(categoryRepository.exists(category.getId()));
		Assert.isTrue(category.getRecipes().isEmpty());
		
		Category father;
		
		father = category.getCategory();
		
		if(father == null){
			if(category.getSubcategories().isEmpty()){
				categoryRepository.delete(category.getId());
			}
			else{
				deleteChildren(category);
			}
		}
		else{
			father.getSubcategories().remove(category);
			save(father);
			category.setCategory(null);
			if(category.getSubcategories().isEmpty()){
				categoryRepository.delete(category.getId());
			}
			else{
				deleteChildren(category);
			}
			
		}
		
	}
	
	//--------------------------------------------------------------
	public void deleteChildren(Category category){
		if(!category.getSubcategories().isEmpty()){
			for(Category c : category.getSubcategories()){
				deleteChildren(c);
			}
		}
		else{
			categoryRepository.delete(category.getId());
		}
	}
	
	public Category findOne(int id){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		Assert.isTrue(id!=0);
		Assert.notNull(id);
		
		Category result;
		
		result= categoryRepository.findOne(id);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Category> findAll(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR") || actorService.checkAuthority("USER"));
		
		Collection<Category> result;
		
		result= categoryRepository.findAll();
		Assert.notNull(result);
		
		return result;
	}

}
