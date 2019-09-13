package caso1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("./docs/datos.txt"));
		
		String s;
		String[] word;
		int nClientes = 0 , nServidores = 0, tBuffer = 0;
		for(int i = 0 ; i < 3 ; ++i) {
			s = in.readLine();
			word = s.split("=");
			if(word[0].trim().toLowerCase().equals("clientes"))
				nClientes = Integer.parseInt(word[1].trim());
			else if(word[0].trim().toLowerCase().equals("servidores"))
				nServidores = Integer.parseInt(word[1].trim());
			else if(word[0].trim().toLowerCase().equals("buffer"))
				tBuffer = Integer.parseInt(word[1].trim());
		}
		
		Buffer buffer = new Buffer(tBuffer , nClientes);
		synchronized(System.out) {
			System.out.println("tamaño buffer " + tBuffer);
			System.out.println("nClientes " + nClientes);
			System.out.println("nServidores " + nServidores);
		}
		
		for(int i = 0 ; i < nClientes ; ++i) {
			s = in.readLine();
			word = s.split("=");
			Cliente c = new Cliente(i , Integer.parseInt(word[1].trim()) , buffer);
			c.start();
		}
		
		for(int i = 0 ; i < nServidores ; ++i) {
			Servidor serv = new Servidor(buffer);
			serv.start();
		}
		
		in.close();
	}

}