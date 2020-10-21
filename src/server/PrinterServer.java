package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrinterServer {

	public static final int PORT = 5099;
	
	
	public static void main(String[] args) throws RemoteException {
		Registry registry = LocateRegistry.createRegistry(PrinterServer.PORT);
		registry.rebind("printer", new PrinterServant());
		
	}
	
}
