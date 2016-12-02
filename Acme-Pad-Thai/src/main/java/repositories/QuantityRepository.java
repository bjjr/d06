package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Quantity;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity, Integer>{

}
