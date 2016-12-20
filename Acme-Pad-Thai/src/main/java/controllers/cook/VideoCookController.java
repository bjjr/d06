package controllers.cook;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.VideoService;
import controllers.AbstractController;
import domain.Video;

@Controller
@RequestMapping("/video")
public class VideoCookController extends AbstractController {

	// Services ------------------------------------------

	@Autowired
	private VideoService videoService;
	
	// Constructor ---------------------------------------

	public VideoCookController() {
		super();
	}
	
	// Create --------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int masterClassId) {
		ModelAndView res;
		Video video;
		
		video = videoService.create();
		
		res = createEditModelAndView(video, masterClassId);
		
		return res;
	}

	// Editing -------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int masterClassId, @RequestParam int videoId) {
		ModelAndView res;
		Video video;

		video = videoService.findOne(masterClassId, videoId);

		res = createEditModelAndView(video, masterClassId);

		return res;
	}
	
	// Save ---------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Video video, BindingResult binding, @RequestParam int masterClassId) {
		ModelAndView res;
		
		if (binding.hasErrors()) {
			res = createEditModelAndView(video, masterClassId);
		} else {
			try {
				videoService.save(video, masterClassId);
				res = new ModelAndView("redirect:/learningMaterial/cook/list.do?masterClassId=" + masterClassId);
			} catch (Throwable th) {
				res = createEditModelAndView(video, masterClassId, "video.commit.error");
			}
		}
		
		return res;
	}
	
	// Delete -------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView edit(Video video, BindingResult binding, @RequestParam int masterClassId) {
		ModelAndView res;
		
		try {
			videoService.delete(video, masterClassId);
			res = new ModelAndView("redirect:/learningMaterial/cook/list.do?masterClassId=" + masterClassId);
		} catch (Throwable th) {
			res = createEditModelAndView(video, masterClassId, "video.commit.error");
		}
		
		return res;
	}

	// Ancillary methods --------------------------------

	protected ModelAndView createEditModelAndView(Video video, int masterClassId) {
		ModelAndView res;

		res = createEditModelAndView(video, masterClassId, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Video video, int masterClassId, String message) {
		ModelAndView res;

		res = new ModelAndView("video/edit");
		res.addObject("video", video);
		res.addObject("message", message);
		res.addObject("masterClassId", masterClassId);

		return res;
	}
}
