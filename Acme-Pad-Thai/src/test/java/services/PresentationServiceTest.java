package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Presentation;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class PresentationServiceTest extends AbstractTest {

	// Service under test ---------------------

	@Autowired
	private PresentationService presentationService;

	// Tests ----------------------------------

	@Test
	public void testCreate() {
		authenticate("cook1");

		Presentation res;
		res = presentationService.create();

		Assert.isTrue(res.getAbstractText() == null);
		Assert.isTrue(res.getTitle() == null);
		Assert.notNull(res.getAttachments());
		Assert.isTrue(res.getPath() == null);

		unauthenticate();
	}

	@Test
	public void testSave() {
		authenticate("cook1");

		Presentation t;
		t = presentationService.create();

		t.setTitle("title");
		t.setPath("http://www.slideshare.net/MelissaPerri/the-build-trap-66849122/20-lissijeanWhat_is_yourproduct_strategy");
		t.setAbstractText("abstracttext");

		Presentation saved;
		saved = presentationService.save(t);
		presentationService.flush();

		Assert.isTrue(presentationService.exists(saved));
		Assert.isTrue(t.getTitle().equals(saved.getTitle()));
		Assert.isTrue(t.getAbstractText().equals(saved.getAbstractText()));
		Assert.isTrue(t.getAttachments().equals(saved.getAttachments()));
		Assert.isTrue(t.getPath().equals(saved.getPath()));

		unauthenticate();
	}

	@Test
	public void testDelete() {
		authenticate("cook1");

		Presentation t;
		t = presentationService.create();

		t.setTitle("title");
		t.setPath("http://www.slideshare.net/MelissaPerri/the-build-trap-66849122/20-lissijeanWhat_is_yourproduct_strategy");
		t.setAbstractText("abstracttext");

		Presentation saved;
		saved = presentationService.save(t);

		presentationService.delete(saved);

		Assert.isTrue(!presentationService.exists(saved));

		unauthenticate();
	}

	@Test
	public void testFindAvgNumText() {
		authenticate("administrator1");

		Double expected;
		expected = 0.25;

		Double res;
		res = presentationService.findAvgNumPresentation();

		Assert.isTrue(expected.equals(res));

		unauthenticate();
	}

}
