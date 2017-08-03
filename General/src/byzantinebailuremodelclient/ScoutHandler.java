/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byzantinebailuremodelclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
class ScoutHandler extends Thread {

    private General general;
    private Socket socket;
    private BufferedReader in;
    private PrintStream out;
    private PrintWriter pw = null;

    public ScoutHandler(Socket socket, General general) {
        this.socket = socket;

    }

    ScoutHandler(BufferedReader in, PrintStream out, General general, PrintWriter pw) {
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
            Logger.getLogger(ScoutHandler.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    private void AlmightyGeneralStuffs(PrintStream out, BufferedReader in) throws IOException, InterruptedException {
        while (true) {

            if (general.isReady()) {
                String sendOrder = "order" + Main.splitTag + general.getId() + Main.splitTag + general.getDecision();
                System.out.println("Sending: " + sendOrder);
                pw.append("Sending: " + sendOrder);
                out.println(sendOrder);

                break;
            }
            sleep(1000);
        }
    }

    private void CommumGeneralStuffs(PrintStream out, BufferedReader in) throws IOException, InterruptedException {
        String msg = "online" + Main.splitTag + general.getId();
        System.out.println("Sending: " + msg);
        pw.append("Sending: " + msg);
        out.println(msg);

        while (true) {
            if (general.receivedFromGeneral) {
                msg = "order" + Main.splitTag + general.getId() + Main.splitTag + general.thinkHisDecision(general.AlmightyOrder);
                System.out.println("Sending: " + msg);
                pw.append("Sending: " + msg);
                out.println(msg);
                break;
            }
            sleep(1000);
        }

    }

    private void readMessage(String msg) {
        String[] tk = msg.split(Main.splitTag);

        switch (tk[0]) {
            case "register":
                break;
            case "online":
                break;
            case "order":
                break;
            default:
        }
    }
}
