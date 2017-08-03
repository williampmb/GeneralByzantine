/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 *
 * @author willi
 */
class Scout {

    int from;
    String msgType;
    String msg;
    String split = "->";

    Scout(String scoutMsg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Scout() {

    }

    public Scout(int registerGeneral, String tag) {
        this.msg = tag + split + registerGeneral;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

  

    public static byte[] intToBytes(final int i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }

    public static byte[] concatenateBytes(byte[] lengthBytes, byte[] msgOutBytes) {
        byte[] result = new byte[lengthBytes.length + msgOutBytes.length];
        System.arraycopy(lengthBytes, 0, result, 0, lengthBytes.length);
        System.arraycopy(msgOutBytes, 0, result, lengthBytes.length, msgOutBytes.length);
        return result;
    }
    
    public static String processRead(InputStream is) throws IOException {
        
        byte[] bufferSize = new byte[4];
        int byteSize = is.read(bufferSize);
        
        ByteBuffer wrapped = ByteBuffer.wrap(bufferSize);
        int size = wrapped.getInt();
            
        byte[] bufferMsg = new byte[size];
        int byteMsg = is.read(bufferMsg);
        String msgIn = new String(bufferMsg,0,byteMsg);
    
      
        return msgIn;
    }

    byte[] translateMessageToBytes(int registerGeneral, String tag) {
        String msg = tag + split + registerGeneral;
        
        int lenght = msg.getBytes().length;
        byte[] lenghtInBytes = intToBytes(lenght);
        byte[] msgInBytes = msg.getBytes();
        return concatenateBytes(lenghtInBytes, msgInBytes);
    }

}
