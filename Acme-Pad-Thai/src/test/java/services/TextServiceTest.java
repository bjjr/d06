package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Text;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class TextServiceTest extends AbstractTest {
	
	// Service under test ---------------------
	
	@Autowired
	private TextService textService;
	
	// Tests ----------------------------------
	
	@Test
	public void testCreate() {
		authenticate("cook1");
		
		Text res;
		res = textService.create();
		
		Assert.isTrue(res.getAbstractText() == null);
		Assert.isTrue(res.getBody() == null);
		Assert.isTrue(res.getTitle() == null);
		Assert.notNull(res.getAttachments());
		
		unauthenticate();
	}
	
	@Test
	public void testSave() {
		authenticate("cook1");
		
		Text t;
		t = textService.create();
		
		t.setTitle("title");
		t.setBody("body");
		t.setAbstractText("abstracttext");
		
		Text saved;
		saved = textService.save(t);
		textService.flush();
		
		Assert.isTrue(textService.exists(saved));
		Assert.isTrue(t.getTitle().equals(saved.getTitle()));
		Assert.isTrue(t.getAbstractText().equals(saved.getAbstractText()));
		Assert.isTrue(t.getAttachments().equals(saved.getAttachments()));
		Assert.isTrue(t.getBody().equals(saved.getBody()));
		
		unauthenticate();
	}
	
	@Test
	public void testDelete() {
		authenticate("cook1");
		
		Text t;
		t = textService.create();
		
		t.setTitle("title");
		t.setBody("body");
		t.setAbstractText("abstracttext");
		
		Text saved;
		saved = textService.save(t);
		
		textService.delete(saved);
		
		Assert.isTrue(!textService.exists(saved));
		
		unauthenticate();
	}
	
	@Test
	public void testFindAvgNumText() {
		authenticate("administrator1");
		
		Double expected;
		expected = 0.75;
		
		Double res;
		res = textService.findAvgNumText();
		
		Assert.isTrue(expected.equals(res));
		
		unauthenticate();
	}

}
