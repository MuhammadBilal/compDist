
package cdpractica1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class Receptor extends Thread{
    
    private Aplicacion app;
    
    private int puerto;
    private String nombreGrupo;
    
    private InetAddress grupo;
    private MulticastSocket socket;
    
    private ArrayList<String> mensajes;
    
    public Receptor(int puerto, String nombreGrupo, Aplicacion app){
        this.puerto = puerto;
        this.nombreGrupo = nombreGrupo;
        this.app = app;
        
        this.mensajes = new ArrayList();
    }
    
    public void run(){
        recibirMensajes();
    }
    
    public void recibirMensajes(){
        byte[] buffer = new byte[10000];
        
        try{
            this.grupo = InetAddress.getByName(nombreGrupo);
            this.socket = new MulticastSocket(puerto);
            
            socket.joinGroup(grupo);
            
            while(true){
                DatagramPacket mEntrada = new DatagramPacket(buffer, buffer.length);
                
                socket.receive(mEntrada);
                //String ip = mEntrada.getAddress().getHostAddress();
                String msg = new String(mEntrada.getData());
                
                // ip += ": "+msg;
                
                //System.out.println("Recibido: "+msg);   //..
                
                //this.mensajes.add(msg);
                app.areaChat.append("\n"+msg);
            }
            
            //socket.leaveGroup(grupo);
        }catch(IOException e){
            System.out.println("Error recepcion: "+e.getMessage());
            app.areaChat.append("Error reception: "+e.getMessage());
        }finally{
            if(socket != null){ socket.close(); }
        }
        
    }
    
    public ArrayList<String> getMensajes(){
        return this.mensajes;
    }
    
}
