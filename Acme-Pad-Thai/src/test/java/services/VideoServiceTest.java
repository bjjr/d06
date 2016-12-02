package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Video;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class VideoServiceTest extends AbstractTest {

	// Service under test ---------------------

	@Autowired
	private VideoService videoService;

	// Tests ----------------------------------

	@Test
	public void testCreate() {
		authenticate("cook1");

		Video res;
		res = videoService.create();

		Assert.isTrue(res.getAbstractText() == null);
		Assert.isTrue(res.getTitle() == null);
		Assert.notNull(res.getAttachments());
		Assert.isTrue(res.getIdentifier() == null);

		unauthenticate();
	}

	@Test
	public void testSave() {
		authenticate("cook1");

		Video v;
		v = videoService.create();

		v.setTitle("title");
		v.setIdentifier("https://www.youtube.com/watch?v=RSyV9E2ib2s");
		v.setAbstractText("abstracttext");

		Video saved;
		saved = videoService.save(v);
		videoService.flush();

		Assert.isTrue(videoService.exists(saved));
		Assert.isTrue(v.getTitle().equals(saved.getTitle()));
		Assert.isTrue(v.getAbstractText().equals(saved.getAbstractText()));
		Assert.isTrue(v.getAttachments().equals(saved.getAttachments()));
		Assert.isTrue(v.getIdentifier().equals(saved.getIdentifier()));

		unauthenticate();
	}

	@Test
	public void testDelete() {
		authenticate("cook1");

		Video v;
		v = videoService.create();

		v.setTitle("title");
		v.setIdentifier("https://www.youtube.com/watch?v=RSyV9E2ib2s");
		v.setAbstractText("abstracttext");

		Video saved;
		saved = videoService.save(v);

		videoService.delete(saved);

		Assert.isTrue(!videoService.exists(saved));

		unauthenticate();
	}

	@Test
	public void testFindAvgNumText() {
		authenticate("administrator1");

		Double expected;
		expected = 0.75;

		Double res;
		res = videoService.findAvgNumVideo();

		Assert.isTrue(expected.equals(res));

		unauthenticate();
	}

}
