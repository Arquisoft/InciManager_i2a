package controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import domain.AgentInfo;
import domain.AgentLoginData;

/**
 * Created by Nicolás on 08/02/2017. Modified by Javier on 15/02/2018 to match
 * the new requirements.
 */
@Controller
public class AgentsController {
	
	@Value("${url.agentsmodule}")
	private String agentsURL;

	AgentsController() {
		
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
			HttpSession session, BindingResult result) throws ClientProtocolException, IOException {
		String postUrl = agentsURL+"/checkAgent";
		Gson gson = new Gson();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity(gson.toJson(data));
		
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);
		
		HttpEntity entity = response.getEntity();
		// Read the contents of an entity and return it as a String.
        String content = EntityUtils.toString(entity);
		
        String name = "", location = "",email = "", userid = "",kind = "";
        int kindCode = -1;
		JSONObject jsonobj;
		try {
			jsonobj = new JSONObject(content);
			
			name = jsonobj.getString("name");
			location = jsonobj.getString("location"); 
	        email = jsonobj.getString("email"); 
	        userid = jsonobj.getString("id"); 
	        kind = jsonobj.getString("kind"); 
	        kindCode = jsonobj.getInt("kindCode"); 
		} catch (JSONException e1) {
			e1.printStackTrace();
		} 

        AgentInfo agent = new AgentInfo(name,location,email,userid,kind,kindCode);

		if (response.getStatusLine().getStatusCode() != 200) {
			model.addAttribute("userinfo", data);
			model.addAttribute("error",true);
			return "login";
		} else {
			model.addAttribute("name", agent.getName());
			model.addAttribute("location", agent.getLocation());
			model.addAttribute("email", agent.getEmail());
			model.addAttribute("kind", agent.getKind());
			model.addAttribute("kindCode", agent.getKindCode());
			model.addAttribute("user", agent);
			session.setAttribute("user", agent);
			return "data";
		}
	}
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String data(Model model, HttpSession session) {
		AgentInfo agentSession = (AgentInfo) session.getAttribute("user");
		model.addAttribute("name", agentSession.getName());
		model.addAttribute("location", agentSession.getLocation());
		model.addAttribute("email", agentSession.getEmail());
		model.addAttribute("kind", agentSession.getKind());
		return "data";
	}
	
}
