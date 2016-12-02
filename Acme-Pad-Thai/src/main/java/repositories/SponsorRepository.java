package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;
import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {
	
	@Query("select s.companyName from Sponsor s group by s.companyName order by s.campaigns.size DESC")
	Collection<String> companiesByNumCampaigns();
	
	@Query("select s.companyName from Sponsor s join s.campaigns c join c.bills b group by s.companyName order by sum(b.cost) DESC")
	Collection<String> companiesByNumBills();

	@Query("select s from Sponsor s join s.campaigns c where ((MONTH(CURRENT_DATE)-MONTH(c.endMoment))=3 and YEAR(c.endMoment) <= YEAR(CURRENT_DATE) and DAY(c.endMoment) <= DAY(CURRENT_DATE)) or ((MONTH(CURRENT_DATE)-MONTH(c.endMoment))>3 and YEAR(c.endMoment) <= YEAR(CURRENT_DATE))")
	Collection<Sponsor> inactiveSponsors();
	
	@Query("select s.companyName from Sponsor s join s.campaigns c join c.bills b where b.paidMoment != null group by s.companyName having sum(b.cost) < (select avg(b.cost) from Sponsor s join s.campaigns c join c.bills b where b.paidMoment != null)")
	Collection<String> companiesSpentLessThanAverage();
	
	@Query("select s.companyName from Sponsor s join s.campaigns c join c.bills b where b.paidMoment != null group by s.companyName having sum(b.cost) >= (select sum(b.cost) from Sponsor s join s.campaigns c join c.bills b where b.paidMoment != null group by s having sum(b.cost) >= ALL(select sum(b.cost) from Sponsor s join s.campaigns c join c.bills b where b.paidMoment != null group by s))*0.9")
	Collection<String> companiesSpentAtLeastNinety();
	
	@Query("select s from Sponsor s where s.userAccount.id = ?1")
	Sponsor findByUserAccountId(int id);
	
	@Query("select b from Sponsor s join s.campaigns c join c.bills b where s.id = ?1")
	Collection<Bill> findBillsBySponsor(int id);
}
