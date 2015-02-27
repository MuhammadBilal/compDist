
package cdpractica1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Emisor extends Thread{
    
    private int puerto;
    private String nombreGrupo;
    
    private InetAddress grupo;
    private MulticastSocket socket;
    
    public Emisor(int puerto, String nombreGrupo){
        this.puerto = puerto;
        this.nombreGrupo = nombreGrupo;
    }
    
    public void enviarMensaje(String msg){
        
        try{
            this.grupo = InetAddress.getByName(nombreGrupo);
            this.socket = new MulticastSocket(puerto);
            
            byte[] m = msg.getBytes();
            
            socket.joinGroup(grupo);
            
            DatagramPacket mSalida = new DatagramPacket(m, m.length, grupo, puerto);
            
            socket.send(mSalida);
            
            socket.leaveGroup(grupo);
            
        }catch(IOException e){
            System.out.println("Error emision: "+e.getMessage());
            
        }finally{
            if(socket !=null){ socket.close(); } 
        }
    }
}
