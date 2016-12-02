package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>{
	
	@Query("select min(u.recipes.size) from User u")
	Double findMinRecipesUser();
	
	@Query("select avg(u.recipes.size) from User u")
	Double findAvgRecipesUser();
	
	@Query("select max(u.recipes.size) from User u")
	Double findMaxRecipesUser();
	
	@Query("select r from Recipe r join r.categories c group by c.name")
	Collection<Recipe> findAllRecipesGroupByCategory();
	
	@Query("select r from Recipe r where r.ticker like %?1% or r.title like %?1% or r.summary like %?1% ")
	Recipe findByKeyword(String keyword);


}
