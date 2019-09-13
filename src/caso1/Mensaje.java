package caso1;


public class Mensaje {
	
	private int contenido;
	
	private int respuesta;
	
	private Buffer buffer;
		
	public Mensaje(int contenido , Buffer buffer) {
		this.contenido = contenido;
		this.buffer = buffer;
	}
	
	public void almacenar() {
		synchronized(System.out) {
			System.out.println("almacenando mensaje " + contenido);
		}
		buffer.almacenar(this);
	}
	
	public void procesarRespuesta() {
		this.respuesta = contenido+1;
	}
	
	public int getRespuesta() {
		return respuesta;
	}
	
	public int getContenido() {
		return contenido;
	}
}
