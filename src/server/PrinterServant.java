package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.PrinterServiceInterface;

public class PrinterServant extends UnicastRemoteObject implements PrinterServiceInterface {

	protected PrinterServant() throws RemoteException {
		super();
	}

	@Override
	public boolean print(String filename, String printer) throws RemoteException {
		System.out.println("Client requested "+filename+" on printer: "+printer);
		return true;
	}

	@Override
	public String queue(String printer) throws RemoteException {
		System.out.println("Client requested the queue on following printer: "+printer);
		return "Printer queue data.";
	}

	@Override
	public boolean topQueue(String printer, int job) throws RemoteException {
		System.out.println("Putting job #"+job+" at the top on printer: "+printer);
		return true;
	}

	@Override
	public boolean start() throws RemoteException {
		System.out.println("Starting the printer service.");
		return true;
	}

	@Override
	public boolean stop() throws RemoteException {
		System.out.println("Stopping the printer service.");
		return true;
	}

	@Override
	public boolean restart() throws RemoteException {
		System.out.println("Restarting the printer service.");
		return true;
	}

	@Override
	public String status(String printer) throws RemoteException {
		System.out.println("Client requested status of printer: "+printer);
		return "Status of printer data.";
	}

	@Override
	public String readConfig(String parameter) throws RemoteException {
		System.out.println("Client requested to read config of parameter.");
		return "Config parameter data.";
	}

	@Override
	public boolean setConfig(String parameter, String value) throws RemoteException {
		System.out.println("Client setting config parameter: "+parameter+" with value: "+value);
		return true;
	}

	
	

}
