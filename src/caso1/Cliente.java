package caso1;

public class Cliente extends Thread {

	private int id;
	
	private int numeroMensajes;
	
	private Buffer buffer;
	
	public Cliente(int id , int nMensajes , Buffer buffer) {
		this.id = id;
		this.numeroMensajes = nMensajes;
		this.buffer = buffer;
	}
	
	public void run() {
		
		for(int i = 1 ; i <= numeroMensajes ; ++i) {
			Mensaje m = new Mensaje(id*100 + i*2 , buffer);
			m.almacenar();
			synchronized(System.out) {
				System.out.println("respuesta recibida " + m.getRespuesta());
			}
		}
		buffer.retirarCliente();
	}
}
