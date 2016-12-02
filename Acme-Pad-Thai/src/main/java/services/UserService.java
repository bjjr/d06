package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Folder;
import domain.User;

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class UserService {

	// Managed repository
	@Autowired
	private UserRepository userRepository;

	// Supporting services
	@Autowired
	private FolderService folderService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private UserAccountService userAccountService;

	// Constructors
	public UserService() {
		super();
	}

	// Simple CRUD methods
	public User create() {
		Assert.isTrue(!(actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("USER")
				|| actorService.checkAuthority("NUTRITIONIST")
				|| actorService.checkAuthority("SPONSOR") || actorService
				.checkAuthority("COOK")));

		User result;
		UserAccount ua;
		Collection<Folder> folders;

		ua = userAccountService.create("USER");
		result = new User();
		folders = folderService.createFolderObligatory(result);
		result.setFolders(folders);
		result.setUserAccount(ua);

		return result;
	}

	public User save(User user) {
		Assert.isTrue(!(actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("USER")
				|| actorService.checkAuthority("NUTRITIONIST")
				|| actorService.checkAuthority("SPONSOR") || actorService
				.checkAuthority("COOK")) || actorService.checkAuthority("USER")
				|| actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(user);

		User result;

		result = userRepository.save(user);

		return result;
	}

	public void flush() {
		userRepository.flush();
	}

	public Collection<User> findAll() {
		Collection<User> result;

		result = userRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public User findOne(int id) {
		Assert.notNull(id);
		Assert.isTrue(id != 0);

		User result;

		result = userRepository.findOne(id);
		Assert.notNull(result);

		return result;
	}

	// Other business methods
	public User findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		User result;

		result = userRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public User findByPrincipal() {
		User result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Collection<User> findUsersAuthoredMoreRecipes() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Collection<User> result;

		result = userRepository.findUsersAuthoredMoreRecipes();
		Assert.notNull(result);

		return result;
	}

	public Collection<User> listUsersPopularity() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Collection<User> result;

		result = userRepository.listUsersPopularity();
		Assert.notNull(result);

		return result;
	}

	public Collection<User> listUsersAverageLikes() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Collection<User> result;
		Collection<Object[]> par;

		result = new ArrayList<User>();
		par = userRepository.listUsersAverageLikes();
		Assert.notNull(par);

		for (Object[] o : par) {
			result.add((User) o[0]);
		}

		return result;
	}

	public Collection<User> listUsersAverageDislikes() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Collection<User> result;
		Collection<Object[]> par;

		result = new ArrayList<User>();
		par = userRepository.listUsersAverageDislikes();
		Assert.notNull(par);

		for (Object[] o : par) {
			result.add((User) o[0]);
		}

		return result;
	}

	public Collection<User> findByKeyword(String keyword) {
		Assert.notNull(keyword);

		Collection<User> result;

		result = userRepository.findByKeyword(keyword);
		Assert.notNull(result);

		return result;

	}

}
