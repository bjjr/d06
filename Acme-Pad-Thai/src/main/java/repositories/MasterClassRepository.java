package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.MasterClass;

@Repository
public interface MasterClassRepository extends JpaRepository<MasterClass, Integer> {
	
	@Query("select count(m) from MasterClass m where m.promoted = true")
	Long findNumPromotedMasterClasses();
	
	@Query("select m from MasterClass m where m.cook.id = ?1")
	Collection<MasterClass> findAllByCook(int cookId);
	
	@Query("select m.actors from MasterClass m where m.id = ?1")
	Collection<Actor> findAttenders(int masterClassId);
	
	@Query("select min(c.masterClassesTeach.size) from Cook c")
	Integer findMinNumMasterClassesPerCook();
	
	@Query("select max(c.masterClassesTeach.size) from Cook c")
	Integer findMaxNumMasterClassesPerCook();
	
	@Query("select avg(c.masterClassesTeach.size) from Cook c")
	Double findAvgNumMasterClassesPerCook();
	
	@Query("select stddev(c.masterClassesTeach.size) from Cook c")
	Double findStddevNumMasterClassesPerCook();
	
}
