package services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbmanagement.AgentsRepository;
import dbmanagement.Database;
import domain.Agent;
import domain.AgentKind;
import util.JasyptEncryptor;

/**
 * Created by Nicolás on 14/02/2017.
 */
@Service
public class AgentsServiceImpl implements AgentsService {
	
	@Autowired
	private AgentsRepository agentsRep;
    private final Database dat;
    private final JasyptEncryptor encryptor = new JasyptEncryptor();

    @Autowired
    AgentsServiceImpl(Database dat){
        this.dat = dat;
    }

    @Override
    public Agent getAgent(String name, String password) {
        Agent user = dat.getAgent(name);
        if(user != null && encryptor.checkPassword(password, user.getPassword()))
            return user;
        else return null;
    }

    @Override
    public void updateInfo(Agent user, String newPassword) {
    	//It is not necessary, done by the domain class itself.
    	user.setPassword(newPassword);
    	dat.updateInfo(user);
    }

	@Override
	public Agent getAgent(String name, String password, AgentKind kind) {
		Agent user = dat.getAgent(name);
        if(user != null && encryptor.checkPassword(password, user.getPassword()))
            return user;
        else return null;
	}

	@Override
	public Agent getAgentByName(String name) {
		return agentsRep.findByName(name);
	}

	@Override
	public Agent getAgentById(String id) {
		ObjectId obid = new ObjectId(id);
		
		return agentsRep.findById(obid);
	}
}
