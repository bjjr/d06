package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import domain.Category;
import domain.Recipe;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
public class CategoryServiceTest extends AbstractTest{
	
	//Service under test
	@Autowired
	private CategoryService categoryService;
	
	//Tests
	
	@Test
	public void testCreateCategory(){
		Category category;
		
		super.authenticate("Administrator1");
		
		category = categoryService.create();
		
		super.authenticate(null);
		
		System.out.println("Category" + category.getId() + "created");
		
	}
	
	@Test
	public void testSaveCategory(){
		Category category, saved;
		
		super.authenticate("Administrator1");
		
		category = categoryService.findOne(101);
		
		saved = categoryService.save(category);
		categoryService.flush();
		
		super.authenticate(null);
		
		System.out.println("Category " + saved.getId() + "saved");
		
	}
	
	@Test
	public void testDeleteCategory(){
		Category category, saved;
		String name, description;
		Collection<Recipe> recipes;
		Collection<Category> subcategories;
		Collection<String> tags;
		
		super.authenticate("Administrator1");
		
		category = categoryService.create();
		name = "New Category";
		description = "This is a new category";
		recipes = new ArrayList<Recipe>();
		subcategories = new ArrayList<Category>();
		tags = new ArrayList<String>();
		
		category.setName(name);
		category.setDescription(description);
		category.setRecipes(recipes);
		category.setSubcategories(subcategories);
		category.setTags(tags);
		
		saved = categoryService.save(category);
		
		categoryService.delete(saved);
		
		super.authenticate(null);
		
		System.out.println("Category  deleted");	
	}
	
	@Test
	public void testFindOneCategory(){
		Category category;
		
		super.authenticate("Administrator1");
		
		category = categoryService.findOne(101);
		
		super.authenticate(null);
		
		System.out.println("Category " + category.getId() + "found");
	}
	
	@Test
	public void testFindAllCategories(){
		Collection<Category> categories;
		
		super.authenticate("Administrator1");
		
		categories = categoryService.findAll();
		
		super.authenticate(null);
		
		for(Category c : categories){
			System.out.println("Category " + c.getId() + "found");
		}
		
		
	}
	

}
