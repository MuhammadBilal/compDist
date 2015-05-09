
import jade.core.AID;

public class Subasta {

	private String tituloLibro;													// Titulo del libro subastado
	private Integer precioSalida;												// Precio del que parte la subasta
	private AID ganador;														// Identificador del agente ganador
	private Integer incremento;													// Incremento entre una puja y la siguiente
	private Integer precioActual;												// Precio actual de la subasta
	private Boolean terminada;													// Indica si la subasta ha finalizado
	private Integer numeroPujas;												// Numero de pujas que se han realizado

	public Subasta(String tituloLibro, Integer precioSalida, Integer incremento){
		this.tituloLibro = tituloLibro;
		this.precioSalida = precioSalida;
		this.incremento = incremento;
		precioActual = precioSalida;
		terminada = false;
		numeroPujas = 0;
	}

	public void incrementar(){
		precioActual += incremento;
		numeroPujas++;
	}

	// GETTERS && SETTERS =====================================================

	public String getTituloLibro(){ 	return tituloLibro; }
	public Integer getPrecioSalida(){ 	return precioSalida; }
	public AID getGanador(){ 			return ganador; }
	public Integer getIncremento(){ 	return incremento; }
	public Integer getPrecioActual(){ 	return precioActual; }
	public Boolean terminada(){			return terminada; }
	public Integer getNumeroPujas(){	return numeroPujas; }

	public void setTituloLibro(String tituloLibro){ 	this.tituloLibro = tituloLibro; }
	public void setPrecioSalida(Integer precioSalida){ 	this.precioSalida = precioSalida; }
	public void setGanador(AID ganador){ 				this.ganador = ganador; } 
	public void setIncremento(Integer incremento){		this.incremento = incremento; }
	public void terminada(Boolean terminada){			this.terminada = terminada; }

}