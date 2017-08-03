/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
class BroadcastHandler extends Thread {

    static String splitTag = "->";

    @Override
    public void run() {
        try {
            while (true) {
                String msg = Server.scoutService.getMessage();
                if (msg != null) {
                    System.out.println("Sending: " + msg);
                    broadcastMsg(msg);
                }
                sleep(3000);
            }
        } catch (Exception ex) {
            Logger.getLogger(BroadcastHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void broadcastMsg(String msg) throws IOException {
        String[] tk = msg.split(splitTag);
        int msgFrom = Integer.valueOf(tk[1]);
        //String scout = tk[1];
        Map<Integer, Socket> generals = Server.generalService.getAll();

        Set<Integer> keySet = generals.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            int next = iterator.next();
            if (msgFrom != next) {
                Socket general = generals.get(next);
                PrintStream out = new PrintStream(general.getOutputStream());
                out.println(msg);
            }

        }
    }

}
