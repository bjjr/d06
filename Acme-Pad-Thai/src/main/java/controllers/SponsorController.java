package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.FolderService;
import services.SponsorService;
import domain.Folder;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {
	// Services ----------------------------------------------------------

	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private FolderService folderService;

	// Constructors ----------------------------------------------------------

	public SponsorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Sponsor sponsor;

		sponsor = sponsorService.create();
		result = createEditModelAndView(sponsor);

		return result;
	}

	// Edition ----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Sponsor sponsor;
		
		sponsor = sponsorService.findOne(sponsorService.findByPrincipal().getId());
		Assert.notNull(sponsor);
		result = createEditModelAndView(sponsor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding) {
		ModelAndView result;
		String password,newPassword;
		Md5PasswordEncoder encoder;
		Collection<Folder> folders;
		
		if (sponsor.getId() == 0) {
			folders = folderService.createFolderObligatory(sponsor);
			sponsor.setFolders(folders);
		}
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(sponsor);
		} else {
			try {
				encoder = new Md5PasswordEncoder();
				password = sponsor.getUserAccount().getPassword();
				newPassword = encoder.encodePassword(password, null);
				sponsor.getUserAccount().setPassword(newPassword);

				sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/security/logout.do");
				result.addObject("message", "sponsor.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(sponsor, "sponsor.commit.error");
			}
		}

		return result;
	}

	// Ancillary Methods
	// ----------------------------------------------------------

	protected ModelAndView createEditModelAndView(Sponsor sponsor) {
		ModelAndView result;

		result = createEditModelAndView(sponsor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Sponsor sponsor,
			String message) {
		ModelAndView result;
		Collection<Authority> authorities;
		Authority authority;
		
		authority = new Authority();
		authority.setAuthority("SPONSOR");
		authorities = new ArrayList<Authority>();
		authorities.add(authority);

		result = new ModelAndView("sponsor/edit");
		//result.addObject("requestURI", "sponsor/edit.do");
		result.addObject("sponsor", sponsor);
		result.addObject("authorities",authorities);
		//result.addObject(sponsor);
		result.addObject("message", message);

		return result;
	}
}
