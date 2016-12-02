package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Text;

@Repository
public interface TextRepository extends JpaRepository<Text, Integer> {

	@Query("select (select count(l) from MasterClass m join m.learningMaterials l where type(l) = domain.Text)/count(m) + 0.0 from MasterClass m")
	Double findAvgNumText();
}
