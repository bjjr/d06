package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.RecipeCopy;

@Repository
public interface RecipeCopyRepository extends JpaRepository<RecipeCopy, Integer> {

	
	
}
