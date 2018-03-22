package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbmanagement.Database;
import domain.Agent;
import util.JasyptEncryptor;

/**
 * Created by Nicolás on 14/02/2017.
 */
@Service
public class AgentsServiceImpl implements AgentsService {

    private final Database dat;
    private final JasyptEncryptor encryptor = new JasyptEncryptor();

    @Autowired
    AgentsServiceImpl(Database dat){
        this.dat = dat;
    }

    @Override
    public Agent getAgent(String name, String password, String kind) {
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
}
