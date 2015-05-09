
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Date;
import java.util.Vector;
import java.util.HashMap;

public class Vendedor extends Agent {

	private GUIVendedor interfaz;
	private int precioSalida;												// Precio de salida del libro
	private int incremento;													// Incremento entre dos pujas
	public boolean continuar = false;												
	public String titulo;

	private HashMap<String, Integer> catalogo;								// Catalogo de libros a la venta titulo-precio

	protected void setup(){
		//interfaz = new GUIVendedor(this);
		//interfaz.setVisible(true);

		//while(!continuar){}

		catalogo = new HashMap();

		//cargarDatos();													  // Obtiene los datos por parametros (titulo, precio, incremento)

		ServiceDescription servicio = new ServiceDescription();				// Crea el servicio - subasta del libro
		servicio.setType("subasta");
		servicio.setName("Subasta de libros");

		DFAgentDescription descripcion = new DFAgentDescription();
		descripcion.addLanguages("Español");
		descripcion.addServices(servicio);

		try{
			DFService.register(this, descripcion);							// Registra el servicio de subasta en las paginas amarillas
		}catch(FIPAException e){
			e.printStackTrace();
		}

		System.out.println(this.getLocalName()+": registrado en las paginas amarillas");
	}

	protected void takeDown(){
		try{
			DFService.deregister(this);
		} catch(FIPAException e){
			e.printStackTrace();
		}
		System.out.println(this.getLocalName()+": eliminado de las paginas amarillas");
	}

	// FUNCIONES =============================================================

	public void actualizarCatalogo(String titulo, int precio){				// Actualiza el catalogo del vendedor insertando un libro nuevo
		addBehaviour(new OneShotBehaviour(){
			public void action(){
				catalogo.put(titulo, new Integer(precio));
				System.out.println(myAgent.getLocalName()+": Añadido '"+titulo+"' al catalogo. Precio = "+precio);
			}
		});
	}

	public void cargarDatos(){
		Object[] args = this.getArguments();

		if(args != null && args.length == 3){
			this.titulo = (String) args[0];
			this.precioSalida = Integer.parseInt((String) args[1]);
			this.incremento = Integer.parseInt((String) args[2]);

		}else{
			System.out.println("ERROR: Introducir tres argumentos (titulo, precio, incremento)");
		}
	}

	public void setPrecioSalida(int precioSalida){
		this.precioSalida = precioSalida;
	}

	public void setIncremento(int incremento){
		this.incremento = incremento;
	}

	// COMPORTAMIENTOS=============================================================


}