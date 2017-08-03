/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author willi
 */
public class ScoutDAO {

    private List<String> msgs = new ArrayList<>();

    public void addMessage(String msg) {
        msgs.add(msg);
    }

    String getMessage() {
        String msg = null;
        System.out.println("Numb msgs: " + msgs.size());
        if(!msgs.isEmpty()){
            msg = msgs.get(0);
            msgs.remove(0);
        }
        return msg;
    }
    
    
}
