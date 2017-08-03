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
public class ScoutService {

    ScoutDAO scouts = null;

    public ScoutService() {
        scouts = new ScoutDAO();
    }

    void addMessage(String msg) {
        scouts.addMessage(msg);
    }

    String getMessage() {
        return scouts.getMessage();
    }

}
