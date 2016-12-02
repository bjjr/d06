package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cook;

@Repository
public interface CookRepository extends JpaRepository<Cook, Integer> {

	@Query("select m.cook from MasterClass m where m.promoted = true group by(m.cook) order by count(m)")
	Collection<Cook> findAllOrderByNumPromotedMasterClasses();
	
	@Query("select min(c.masterClassesTeach.size) from Cook c")
	Integer findMinTeachedMasterClasses();
	
	@Query("select avg(c.masterClassesTeach.size) from Cook c")
	Double findAvgTeachedMasterClasses();
	
	@Query("select max(c.masterClassesTeach.size) from Cook c")
	Integer findMaxTeachedMasterClasses();
	
	@Query("select stddev(c.masterClassesTeach.size) from Cook c")
	Double findStddevTeachedMasterClasses();
	
	@Query("select (select count(m) from MasterClass m where m.promoted = true)/count(c)*1.0 from Cook c")
	Double findAvgPromotedMasterClasses();
	
	@Query("select (select count(m) from MasterClass m where m.promoted = false)/count(c)*1.0 from Cook c")
	Double findAvgDemotedMasterClasses();
	
	@Query("select c from Cook c where c.userAccount.id = ?1")
	Cook findByUserAccountId(int userAccountId);
	
}
