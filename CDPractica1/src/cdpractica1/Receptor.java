
package cdpractica1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receptor extends Thread{
    
    private Aplicacion app;
    
    private int puerto;
    private String nombreGrupo;
    
    private InetAddress grupo;
    private MulticastSocket socket;

    
    public Receptor(int puerto, String nombreGrupo, Aplicacion app){
        this.puerto = puerto;
        this.nombreGrupo = nombreGrupo;
        this.app = app;
    }
    
    public void run(){
        recibirMensajes();
    }
    
    public void recibirMensajes(){
        
        
        try{
            this.grupo = InetAddress.getByName(nombreGrupo);
            this.socket = new MulticastSocket(puerto);
            
            socket.joinGroup(grupo);
            
            while(true){
                byte[] buffer = new byte[10000];
                DatagramPacket mEntrada = new DatagramPacket(buffer, buffer.length);
                
                socket.receive(mEntrada);
                String m2 = new String(mEntrada.getAddress().getHostAddress()); //obtiene la direccion del emisor
                String msg = new String(mEntrada.getData());
                
                m2 += ": "+msg;
                
                System.out.println("Recibido: "+m2);   //..
                
                app.areaChat.append(m2+"\n");
            }
            
            //socket.leaveGroup(grupo);
        }catch(IOException e){
            System.out.println("Error recepcion: "+e.getMessage());
            app.areaChat.append("Error recepcion: "+e.getMessage());
        }finally{
            if(socket != null){ socket.close(); }
        }
        
    }

}
