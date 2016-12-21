package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContestRepository;
import domain.Contest;
import domain.RecipeCopy;

@Service
@Transactional
public class ContestService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ContestRepository contestRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private RecipeCopyService recipeCopyService;

	// Constructors -----------------------------------------------------------
	public ContestService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Contest create() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could create contest");

		Contest res;

		res = new Contest();

		return res;
	}

	public Contest save(Contest contest) {
		Assert.notNull(contest);
		if (contest.getId() == 0) {
			Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
					"Only an admin could save contest");
		} else {
			Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR")
					|| actorService.checkAuthority("USER"),
					"Only an admin or user could edit contest");

		}
		Assert.isTrue(
				contest.getOpeningTime().compareTo(contest.getClosingTime()) < 0,
				"Opening time must be before than closing time");
		return contestRepository.save(contest);
	}

	public void flush() {
		contestRepository.flush();
	}

	public void delete(Contest contest) {
		Assert.notNull(contest);
		Assert.isTrue(
				contestRepository.findRecipeCopiesByContest(contest.getId())
						.isEmpty(), "Could not delete contest with recipes");
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could delete contest");

		contestRepository.delete(contest);
	}

	public Collection<Contest> findAll() {
		Collection<Contest> result;

		result = contestRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Contest findOne(int id) {
		Contest result;

		result = contestRepository.findOne(id);
		Assert.notNull(result);

		return result;
	}

	public Boolean exist(int id) {
		Boolean res;
		res = contestRepository.exists(id);
		return res;
	}

	// Other business methods -------------------------------------------------
	public Integer minRecipeCopyPerContest() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could use this method");
		Integer res;
		res = contestRepository.minRecipeCopiesPerContest();

		return res;
	}

	public Integer maxRecipeCopyPerContest() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could use this method");
		Integer res;
		res = contestRepository.maxRecipeCopiesPerContest();

		return res;
	}

	public Double avgRecipeCopyPerContest() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"),
				"Only an admin could use this method");
		Double res;
		res = contestRepository.avgRecipeCopiesPerContest();

		return res;
	}

	/**
	 * Concurso con mas recetas presentadas
	 **/
	public String findContestMoreRecipesQualified() {
		String result;
		result = contestRepository.findContestMoreRecipesQualified();
		return result;
	}

	/**
	 * Copias de receta de un concurso
	 **/
	public Collection<RecipeCopy> findRecipeCopiesByContest(int id) {
		Collection<RecipeCopy> res;
		res = contestRepository.findRecipeCopiesByContest(id);
		return res;
	}

	public Collection<RecipeCopy> findRecipeWinnerByContest(int id) {
		Collection<RecipeCopy> res;
		res = contestRepository.findRecipeWinnerByContest(id);
		return res;
	}

	public Collection<Contest> findOpenContests() {
		Collection<Contest> res;
		res = contestRepository.findOpenContests();
		return res;
	}

	public ArrayList<RecipeCopy> recipeCopyByContestOrdered(int id) {
		ArrayList<RecipeCopy> res;
		res = contestRepository.recipesCopiesByContestOrdered(id);
		return res;
	}

	public void setWinners() {
		Collection<Contest> contests;
		contests = findAll();
		contests.removeAll(contestRepository.findClosedContestsWinners());
		for (Contest c : contests) {
			ArrayList<RecipeCopy> r = (ArrayList<RecipeCopy>) contestRepository
					.recipesCopiesByContestOrdered(c.getId());
			if (!r.isEmpty()) {
				for (Integer i = 0; i <= 2; i++) {
					r.get(i).setWinner(true);
					recipeCopyService.save(r.get(i));
				}
			}
		}
	}
}
