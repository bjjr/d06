package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class UserServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private UserService userService;

	// Tests

	@Test
	public void testCreateUser() {
		User user;

		user = userService.create();

		System.out.println("User" + user.getId() + "created");
	}

	@Test
	public void testSaveUser() {
		User user, saved;

		user = userService.findOne(53);
		saved = userService.save(user);
		userService.flush();

		System.out.println("User" + saved.getId() + "saved");
	}

	@Test
	public void testFindOneUser() {
		User user;

		user = userService.findOne(53);

		System.out.println("User" + user.getId() + "found");
	}

	@Test
	public void testFindAllUsers() {
		Collection<User> users;

		users = userService.findAll();

		for (User u : users) {
			System.out.println("User" + u.getId() + "found");
		}
	}

	@Test
	public void testFindUsersAuthoredMoreRecipes() {
		Collection<User> users;

		super.authenticate("Administrator1");

		users = userService.findUsersAuthoredMoreRecipes();

		super.authenticate(null);

		for (User u : users) {
			System.out.println("User" + u.getId() + "found");
		}
	}

	@Test
	public void testListUsersPopularity() {
		Collection<User> users;

		super.authenticate("Administrator1");

		users = userService.listUsersPopularity();

		super.authenticate(null);

		for (User u : users) {
			System.out.println("User" + u.getId() + "found");
		}
	}

	@Test
	public void testListUsersAverageLikes() {
		Collection<User> usersAvg;

		super.authenticate("Administrator1");

		usersAvg = userService.listUsersAverageLikes();

		super.authenticate(null);

		for (User u : usersAvg) {
			System.out.println("User" + u.getId() + "found");
		}
	}

	@Test
	public void testListUsersAverageDislikes() {
		Collection<User> usersAvg;

		super.authenticate("Administrator1");

		usersAvg = userService.listUsersAverageDislikes();

		super.authenticate(null);

		for (User u : usersAvg) {
			System.out.println("User" + u.getId() + "found");
		}
	}

	@Test
	public void testFindUserByKeyword() {
		Collection<User> users;

		super.authenticate("Administrator1");

		users = userService.findByKeyword("nameUser4");

		super.authenticate(null);

		for (User u : users) {
			System.out.println("User" + u.getId() + "found");
		}
	}

}
