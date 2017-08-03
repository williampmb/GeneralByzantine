/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author willi
 */
public class GeneralDAO {

    private static int id = 0;

    private Map<Integer, Socket> generals = null;

    public GeneralDAO() {
        generals = new Hashtable<Integer, Socket>();
    }

    public int registerGeneral(Socket general) {

        int registerId = id;
        id++;
        generals.put(registerId, general);
        return registerId;
    }

    Map<Integer, Socket> getAll() {
        return generals;
    }
}
