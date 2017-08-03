/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author willi
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static ServerSocket server;
    public static List<Scout> msgs = new ArrayList<Scout>();
    public static GeneralService generalService = null;
    public static ScoutService scoutService = null;

    public static void main(String[] args) throws IOException {
        try {
            System.out.println("** Start Server **");

            server = new ServerSocket(9000);

            System.out.println("** Waiting for Connection **");
            
            generalService = new GeneralService();
            scoutService = new ScoutService();
            
            BroadcastHandler broadcast = new BroadcastHandler();
            broadcast.start();

            while (true) {
                Socket socket = waitForConnection();

                SocketListener listener = new SocketListener(socket);
                listener.start();

            }
        } catch (Exception e) {

        }

      
        System.out.println("** Stop Server **");

    }

    private static Socket waitForConnection() {
        Socket socket = null;
        try {
            socket = server.accept();
            System.out.println("** " + socket.getRemoteSocketAddress().toString() + "  Connected **");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static Scout getNextOrder() {
        if(msgs.isEmpty()){
            return null;
        }
        
        Scout scout = msgs.get(0);
        msgs.remove(0);
        return scout;
    }

}
