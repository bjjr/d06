package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.Endorser;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class CurriculumServiceTest extends AbstractTest{
	
	// Service under test --------------------------------
	
	@Autowired
	private CurriculumService curriculumService;
	
	// Tests ---------------------------------------------
	
	@Test
	public void testCreateCurriculum(){
		authenticate("nutritionist1");
		
		Curriculum res;
		
		res = curriculumService.create();
		
		Assert.isTrue(res.getPhoto() == null);
		Assert.isTrue(res.getEducationSection() == null);
		Assert.isTrue(res.getExperienceSection() == null);
		Assert.isTrue(res.getHobbiesSection() == null);
		Assert.isTrue(res.getEndorsers() == null);
		
		unauthenticate();
	}
	
	@Test
	public void testFindOneCurriculum(){
		authenticate("nutritionist1");
		
		Curriculum curriculum;
		int id = 49;
		
		curriculum = curriculumService.findOne(id);
		System.out.println("Curriculum whose id is " + id + " found: " + curriculum);
		
		unauthenticate();
	}
	
	@Test
	public void testFindAllCurriculum(){
		authenticate("nutritionist1");
		
		Collection<Curriculum> curricula;
		
		curricula = curriculumService.findAll();
		
		System.out.println(curricula.size() + " curricula found");
		
		unauthenticate();
	}
	
	@Test
	public void testSaveCurriculum(){
		authenticate("nutritionist1");
		
		Curriculum curriculum;
		Curriculum saved;
		Collection<Curriculum> curricula;
		Collection<Endorser> endorsers;
		
		curriculum = curriculumService.create();
		endorsers = new ArrayList<Endorser>();
		
		curriculum.setPhoto("http://www.photoTest.com");
		curriculum.setEducationSection("educationSectionTest");
		curriculum.setExperienceSection("experienceSectionTest");
		curriculum.setHobbiesSection("hobbiesSectionTest");
		curriculum.setEndorsers(endorsers);
		
		saved = curriculumService.save(curriculum);
		curriculumService.flush();
		
		curricula = curriculumService.findAll();
		
		Assert.isTrue(curricula.contains(saved));
		
		System.out.println("Curriculum saved");
		
		unauthenticate();
	}
	
	@Test
	public void testDeleteCurriculum(){
		authenticate("nutritionist1");
		
		Curriculum curriculum;

		curriculum = curriculumService.findOne(49);
		
		curriculumService.delete(curriculum);
		
		Assert.isTrue(!curriculumService.findAll().contains(curriculum));
		
		System.out.println("Curriculum deleted correctly");
		
		unauthenticate();
	}

}
