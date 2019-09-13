package caso1;

import java.util.LinkedList;

public class Buffer {
	
	private LinkedList<Mensaje> buff;
	
	private int n;
	
	Object lockAlmacenar, lockRetirar , lockCliente;
	
	private int clientesFaltantes;
	
	public Buffer (int n , int clientes) {
		this.n = n;
		buff = new LinkedList<>();
		lockAlmacenar = new Object();
		lockCliente = new Object();
		lockRetirar = new Object();
		clientesFaltantes = clientes;
	}
	
	public void almacenar ( Mensaje i ){
		synchronized( lockAlmacenar ){
			while ( buff.size( ) == n ){
				synchronized(System.out) {
					System.out.println("Buffer lleno, duermo " + i.getContenido());					
				}
				try { lockAlmacenar.wait(); }
				catch( InterruptedException e ){}
			}
		
			synchronized( this ) {
				buff.add( i );
				synchronized(System.out) {
					System.out.println("agregando a cola " + i.getContenido());
					System.out.println("tamano buffer " + buff.size());		
				}
			}
		}
		
		synchronized(i) {
			try {
				i.wait();
			} catch (InterruptedException e) {} 			
		}
	}
	
	public Mensaje retirar (){
		Mensaje i;

		synchronized( lockRetirar ){
			if(buff.size() == 0) {
				synchronized(System.out) {
					System.out.println("nada que retirar");
				}
				return null;
			}
		
			synchronized(this) {
				i = buff.poll();
				synchronized(System.out) {
					System.out.println("retirando " + i.getContenido());
					System.out.println("tamano buffer " + buff.size());		
				}				
			}
		}

		synchronized( lockAlmacenar ){ lockAlmacenar.notify( ); }
		
		return i;
	}
	
	public boolean hayClientes() {
		synchronized (lockCliente) {
			return clientesFaltantes > 0;
		}
	}
	
	public void retirarCliente() {
		synchronized(lockCliente) {
			synchronized(System.out) {
				System.out.println("Retiro un cliente");
			}
			clientesFaltantes--;
		}
	}
	
}