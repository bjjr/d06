package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.LikeSA;

@Repository
public interface LikeSARepository extends JpaRepository<LikeSA, Integer>{

}
