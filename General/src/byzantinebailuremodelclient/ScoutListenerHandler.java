/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byzantinebailuremodelclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
public class ScoutListenerHandler extends Thread {

    private General general;
    private Socket socket;
    private BufferedReader in;
    private PrintStream out;
    private PrintWriter pw = null;

    ScoutListenerHandler(BufferedReader in, PrintStream out, General general, PrintWriter pw) {
        this.general = general;
        this.in = in;
        this.out = out;
        this.pw = pw;
    }

    @Override
    public void run() {
        try {
            if (general.getId() == 0) {
                AlmightyGeneralStuffs(out, in);

            } else {
                CommumGeneralStuffs(out, in);
            }
        } catch (Exception e) {
            Logger.getLogger(ScoutListenerHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void AlmightyGeneralStuffs(PrintStream out, BufferedReader in) throws IOException, InterruptedException {
        while (true) {

            String msg = in.readLine();
            System.out.println("Received: " + msg);
            pw.append("Received: " + msg + "\n");
            
            String[] tk = msg.split(Main.splitTag);
            switch (tk[0]) {
                case "online":
                    general.setOnlineById(Integer.parseInt(tk[1]));
                    general.checkReady();
                    break;
                case "order":
                    int idFrom = Integer.valueOf(tk[1]);
                    general.confirmation[idFrom] = Integer.valueOf(tk[2]);
                    break;
                default:

            }
            sleep(1000);
        }
    }

    private void CommumGeneralStuffs(PrintStream out, BufferedReader in) throws IOException, InterruptedException {
        while (true) {

            String msg = in.readLine();
            System.out.println("Received: " + msg);
            pw.append("Received: " + msg + "\n");
            String[] tk = msg.split(Main.splitTag);
            switch (tk[0]) {
                case "order":
                    int idFrom = Integer.valueOf(tk[1]);
                    if (idFrom == 0) {
                        general.AlmightyOrder = tk[2];
                        general.receivedFromGeneral = true;
                    }
                    general.confirmation[idFrom] = Integer.valueOf(tk[2]);

                    break;
                default:

            }
            sleep(1000);
        }
    }
}
