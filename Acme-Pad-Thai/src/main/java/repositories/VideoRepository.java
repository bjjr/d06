package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

	@Query("select (select count(l) from MasterClass m join m.learningMaterials l where type(l) = domain.Video)/count(m) + 0.0 from MasterClass m")
	Double findAvgNumVideo();
}
