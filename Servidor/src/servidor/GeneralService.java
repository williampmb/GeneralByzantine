/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.net.Socket;
import java.util.Map;

/**
 *
 * @author willi
 */
class GeneralService {
    
    private GeneralDAO db = null;
    
    public GeneralService(){
        db = new GeneralDAO();
    }
    
    public int registerGeneral(Socket socket) {
        int id = db.registerGeneral(socket);
        return id;
    }

    static void registerGeneral(Socket socket, String identification) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    Map<Integer, Socket> getAll() {
        return db.getAll();
    }
    
}
