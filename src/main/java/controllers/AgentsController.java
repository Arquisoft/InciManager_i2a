package controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Agent;
import domain.AgentInfo;
import domain.AgentInfoAdapter;
import domain.AgentLoginData;
import services.AgentsService;
import util.JasyptEncryptor;

/**
 * Created by Nicolás on 08/02/2017. Modified by Javier on 15/02/2018 to match
 * the new requirements.
 */
@Controller
public class AgentsController {

	private final AgentsService part;

	AgentsController(AgentsService part) {
		this.part = part;
	}

	// The first page shown will be login.html.
	@GetMapping(value = "/")
	public String getParticipantInfo(Model model, HttpSession session) {
		AgentLoginData data = new AgentLoginData();
		model.addAttribute("userinfo", data);
		return "login";
	}

	// This method process an POST html request once fulfilled the login.html
	// form (clicking in the "Enter" button).
	@RequestMapping(value = "/userForm", method = RequestMethod.POST)
	public String showInfo(Model model, @ModelAttribute AgentLoginData data,
			HttpSession session, BindingResult result) {
		Agent user = part.getAgent(data.getLogin(), data.getPassword(),
				data.getKind());
		if (user == null) {
			//UserLoginData data = new UserLoginData();
			model.addAttribute("userinfo", data);
			model.addAttribute("error",true);
			return "login";
		} else {
			AgentInfoAdapter adapter = new AgentInfoAdapter(user);
			AgentInfo info = adapter.userToInfo();
			model.addAttribute("name", info.getName());
			model.addAttribute("location", info.getLocation());
			model.addAttribute("email", info.getEmail());
			model.addAttribute("kind", info.getKind());
			model.addAttribute("kindCode", info.getKindCode());
			model.addAttribute("user", user);
			session.setAttribute("user", user);
			return "data";
		}
	}
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String data(Model model, HttpSession session) {
		Agent agentSession = (Agent) session.getAttribute("user");
		model.addAttribute("name", agentSession.getUsername());
		model.addAttribute("location", agentSession.getLocation());
		model.addAttribute("email", agentSession.getEmail());
		model.addAttribute("kind", agentSession.getKind());
		return "data";
	}

	@RequestMapping(value = "/passMenu", method = RequestMethod.GET)
	public String showMenu(Model model, HttpSession session) {
		// Just in case there must be more processing.
		return "changePassword";
	}

	@RequestMapping(value = "/userChangePassword", method = RequestMethod.POST)
	public String changePassword(Model model, @RequestParam String password,
			@RequestParam String newPassword,
			@RequestParam String newPasswordConfirm, HttpSession session) {
		JasyptEncryptor encryptor = new JasyptEncryptor();
		Agent loggedUser = (Agent) session.getAttribute("user");
		if (encryptor.checkPassword(password, loggedUser.getPassword())
				&& newPassword.equals(newPasswordConfirm)) {
			part.updateInfo(loggedUser, newPassword);
			return "data";
		}
		return "changePassword";
	}
	
}
