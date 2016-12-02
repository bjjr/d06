package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PriorityRepository;
import domain.Priority;

@Service
@Transactional
public class PriorityService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PriorityRepository priorityRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public PriorityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Collection<Priority> findAll() {
		Collection<Priority> result;

		result = priorityRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Priority findOne(int id) {
		Priority result;

		result = priorityRepository.findOne(id);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

}
