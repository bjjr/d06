package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.recipes.size = (select max(u.recipes.size) from User u)")
	Collection<User> findUsersAuthoredMoreRecipes();
	
	@Query("select u from User u order by u.followers.size DESC")
	Collection<User> listUsersPopularity();
	
	@Query("select u, ((count(l)/u.recipes.size)*1.0) AS average from User u join u.recipes r " +
			"join r.likesSA l where l.likeSA = true group by u order by average DESC")
	Collection<Object[]> listUsersAverageLikes();
	
	@Query("select u, ((count(l)/u.recipes.size)*1.0) AS average from User u join u.recipes r " +
			"join r.likesSA l where l.likeSA = false group by u order by average DESC")
	Collection<Object[]> listUsersAverageDislikes();
	
	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);
	
	@Query("select u from User u where u.name like %?1%")
	Collection<User> findByKeyword(String keyword);

}
