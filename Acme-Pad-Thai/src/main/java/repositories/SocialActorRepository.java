package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SocialActor;

@Repository
public interface SocialActorRepository extends JpaRepository<SocialActor, Integer>{
	
	@Query("select sc from SocialActor sc where sc.userAccount.id = ?1")
	SocialActor findByUserAccountId(int userAccountId);

}
