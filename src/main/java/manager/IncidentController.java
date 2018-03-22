package manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mongodb.util.JSON;

import manager.incidents.InciValidator;
import manager.incidents.Incident;
import manager.producers.KafkaProducer;

@Controller
public class IncidentController {

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private InciValidator inciValidator;

    @RequestMapping("/")
    public String landing(Model model) {
        model.addAttribute("message", new Message());
        return "index";
    }
    
    @RequestMapping("/send")
    public String send(Model model, @ModelAttribute Message message) {
    	
    	Incident incident=null;// Need to change thi when receiving object
    	inciValidator.isEmergency(incident);
        kafkaProducer.send("exampleTopic", message.getMessage());
        return "data";
    }

}