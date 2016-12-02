package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	// select sum(b.cost)/(select count(b) from Bill b), sqrt(sum((b.cost -
	// (select sum(b.cost)/(select count(b) from Bill b) from Bill b where
	// b.paidMoment != null))*(b.cost - (select sum(b.cost)/(select count(b)
	// from Bill b) from Bill b where b.paidMoment != null)))/(select count(b)
	// from Bill b)) from Bill b where b.paidMoment != null;
	@Query("select avg(b.cost) from Bill b) from Bill b where b.paidMoment != null")
	Double avgPaidBills();

	@Query("select stddev(b.cost) from Bill b where b.paidMoment != null")
	Double stddevPaidBills();
	
	@Query("select avg(b.cost) from Bill b where b.paidMoment = null and (b.creationMoment + 30) >= (CURRENT_DATE)")
	Double avgUnpaidBills();
	
	@Query("select stddev(b.cost) from Bill b where b.paidMoment = null and (b.creationMoment + 30) >= (CURRENT_DATE)")
	Double stddevUnpaidBills();
}
