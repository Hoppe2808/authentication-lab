package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import interfaces.PrinterServiceInterface;
import server.PrinterServer;

public class Client {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		PrinterServiceInterface service = (PrinterServiceInterface) Naming.lookup("rmi://localhost:"+PrinterServer.PORT+"/printer");
		System.out.println("-----" + service.print("test", "printer1"));
	}
	
}
