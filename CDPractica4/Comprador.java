
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.*;

public class Comprador extends Agent {

	private String tituloLibro;													// Titulo del libro que desea adquirir
	private int precioMax;														// Precio maximo del libro por el que esta dispuesto a pujar
	private AID[] vendedores;													// Lista de los vendedores

	protected void setup(){

		cargarDatos();

		//buscarSubastas(10000);
		
	}

	protected void takeDown(){
		System.out.println(getLocalName()+": Finaliza su ejecucion");
	}

	// METODOS =================================================================

	public void cargarDatos(){
		Object[] args = this.getArguments();

		if(args != null && args.length == 2){
			this.tituloLibro = (String) args[0];
			this.precioMax = Integer.parseInt((String) args[1]);
		}else{
			System.out.println("ERROR: Introducir por argumento titulo e importe maximo");
		}
	}

	private void buscarSubastas(int periodo){									// Busca subastas cada x segundos
		addBehaviour(new TickerBehaviour(this, periodo){
			protected void onTick(){
				DFAgentDescription plantilla = new DFAgentDescription();
				ServiceDescription servicio = new ServiceDescription();

				servicio.setType("subasta");
				plantilla.addServices(servicio);

				try{															// Busca los agentes que oferten el servicio de subasta
					DFAgentDescription[] resultado = DFService.search(myAgent, plantilla);

					System.out.println("Encontrados subastadores:");
					vendedores = new AID[resultado.length];

					for(int i=0; i < resultado.length; i++){
						vendedores[i] = resultado[i].getName();
						System.out.println(vendedores[i].getName());
					}
				}catch(FIPAException e){
					e.printStackTrace();
				}
			}

		});
	}

	// COMPORTAMIENTOS =========================================================
}