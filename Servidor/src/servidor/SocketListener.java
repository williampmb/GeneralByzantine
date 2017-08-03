/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author willi
 */
class SocketListener extends Thread {

    private Socket socket;

    SocketListener(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            PrintStream out = new PrintStream(os);

            System.out.println("** REGISTERING GENERAL ** ");
            int registerGeneral = Server.generalService.registerGeneral(socket);
            System.out.println("** GENERAL REGISTERED AS " + registerGeneral + "** ");

            Scout messager = new Scout(registerGeneral, "register");

            System.out.println("** SENDING ID FOR GENERAL ** ");
            out.println(messager.getMsg());
            System.out.println("** MESSAGE DELIVERED ** ");

            while (true) {
                String msg = in.readLine();

                if (msg != null) {
                    System.out.println("Received: " + msg);
                    Server.scoutService.addMessage(msg);
                }

            }

//            System.out.println("Encerrando conexão.");
//
//            in.close();
//            out.close();
//            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
