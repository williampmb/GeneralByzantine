/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byzantinebailuremodelclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;

/**
 *
 * @author willi
 */
public class Main {

    static private General general;
    static String splitTag = "->";
    static private Socket socket;
    static int numberOfGenerals;
    static int probabilityOfTraitors;

    //args[0] = ip to connect on the broadcast
    //args[1] = number of generals
    //args[2] = probability of traitors
    public static void main(String[] args) throws IOException, Exception {
       
        System.out.println(" ** Trying to Connect ** ");
        System.out.println(" ** Version 3.0 ** ");
       
        socket = new Socket(args[0], 9000);

        numberOfGenerals = Integer.valueOf(args[1]);
        probabilityOfTraitors = Integer.valueOf(args[2]);

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintStream out = new PrintStream(output);

        String msg = in.readLine();

        System.out.println(msg);

        general = new General(msg);
        
        File log = new File("general"+general.getId()+".txt");
        if(!log.exists()){
            log.createNewFile();
        }
        
        PrintWriter pw = new PrintWriter(new FileOutputStream(log, true));
        
        
        pw.append(" ** Trying to Connect ** \n");
        pw.append(" ** Version 3.0 ** \n");
        
        System.out.println("General " + general.getId() + " Conencted");
        pw.append("General " + general.getId() + " Conencted \n");
       
        ScoutHandler sendMessages = new ScoutHandler(in, out, general,pw);
        sendMessages.start();

        ScoutListenerHandler receiveMessages = new ScoutListenerHandler(in, out, general,pw);
        receiveMessages.start();

        while (true) {
            // thinking if it has enough votes to attack or not
            boolean commomSense = general.analiseVotes();

            if (commomSense) {
                System.out.println(general.getId() + ": " + general.getDecision());
                pw.append(general.getId() + ": " + general.getDecision()+"\n");
                break;
            }
            sleep(1000);
        }
        pw.close();
    }
}
