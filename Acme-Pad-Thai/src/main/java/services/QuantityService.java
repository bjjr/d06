package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Quantity;

import repositories.QuantityRepository;

@Service
@Transactional
public class QuantityService {
	
	//Managed repository
	@Autowired
	private QuantityRepository quantityRepository;
	
	// Supporting services
	
	//Constructors
	public QuantityService(){
		super();
	}
	
	// Simple CRUD methods
	public Quantity create(){
		Quantity result;
		
		result = new Quantity();
		
		return result;
	}
	
	public Quantity save(Quantity quantity){
		Assert.notNull(quantity);
		
		Quantity result;
		
		result = quantityRepository.save(quantity);
		
		return result;
	}
	
	public void flush() {
		quantityRepository.flush();
	}
	
	public void delete(Quantity quantity){
		Assert.notNull(quantity);
		Assert.isTrue(quantity.getId()!=0);
		Assert.isTrue(quantityRepository.exists(quantity.getId()));
		
		quantityRepository.delete(quantity);
	}
	
	public Quantity findOne(int id){
		Assert.notNull(id);
		Assert.isTrue(id!=0);
		
		Quantity result;
		
		result = quantityRepository.findOne(id);
		Assert.notNull(result);
		
		return result;
	}

}
