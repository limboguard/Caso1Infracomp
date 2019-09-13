package caso1;

public class Servidor extends Thread{

	private Buffer buffer;
	
	public Servidor(Buffer buffer) {
		this.buffer = buffer;
	}
	
	
	public void run() {
		while(buffer.hayClientes()) {
			Mensaje m;
			while( (m = buffer.retirar()) == null) {
				if(!buffer.hayClientes())break;
				yield();
			}
			
			if(m != null) {
				synchronized(m) {
					m.procesarRespuesta();
					m.notify();
				}					
			}
		}
	}
}
