package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{
	
	@Query("select avg(r.quantities.size) from Recipe r")
	Double findAvgIngredientPerRecipe();
	
	@Query("select stddev(r.quantities.size) from Recipe r")
	Double findStandardDeviationIngredientPerRecipe();
	
	@Query("select i from Recipe r join r.quantities q join q.ingredient i group by i")
	Collection<Ingredient> findAllUsedIngredients();

}
