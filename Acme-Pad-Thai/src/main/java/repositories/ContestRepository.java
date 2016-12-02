package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Contest;
import domain.RecipeCopy;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Integer>{
	
	@Query("select min(c.recipeCopies.size) from Contest c")
	Integer minRecipeCopiesPerContest();
	
	@Query("select max(c.recipeCopies.size) from Contest c")
	Integer maxRecipeCopiesPerContest();
	
	@Query("select avg(c.recipeCopies.size) from Contest c")
	Double avgRecipeCopiesPerContest();
	
	@Query("select c from Contest c where c.recipeCopies.size = " +
			"(select max(c.recipeCopies.size) from Contest c)")
	Contest findContestMoreRecipesQualified();
	
	@Query("select c.recipeCopies from Contest c where c.id = ?1")
	Collection<RecipeCopy> findRecipeCopiesByContest(int id);
	
	@Query("select r from Contest c join c.recipeCopies r where c.id = ?1 and r.winner = true")
	Collection<RecipeCopy> findRecipeWinnerByContest(int id);
	
}
