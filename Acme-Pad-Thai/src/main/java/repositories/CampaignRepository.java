package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

	@Query("select min(s.campaigns.size) from Sponsor s")
	Integer minCampignsPerSponsor();
	
	@Query("select max(s.campaigns.size) from Sponsor s")
	Integer maxCampignsPerSponsor();
	
	@Query("select avg(s.campaigns.size) from Sponsor s")
	Double avgCampignsPerSponsor();

	@Query("select min(s.campaigns.size) from Sponsor s join s.campaigns c where c.startMoment <= current_date and c.endMoment >= current_date")
	Integer minActiveCampignsPerSponsor();
	
	@Query("select max(s.campaigns.size) from Sponsor s join s.campaigns c where c.startMoment <= current_date and c.endMoment >= current_date")
	Integer maxActiveCampignsPerSponsor();
	
	@Query("select count(c)*1.0/(select count(s) from Sponsor s) from Sponsor s join s.campaigns c where c.startMoment <= current_date and c.endMoment >= current_date")
	Double avgActiveCampignsPerSponsor();
	
}
