package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.PrinterServiceInterface;

public class PrinterServant extends UnicastRemoteObject implements PrinterServiceInterface {

	protected PrinterServant() throws RemoteException {
		super();
	}

	@Override
	public String login(String username, String password) throws RemoteException {
		if (PrinterServer.authenticate(username, password)) {
			// TODO SEBASTIAN TOKENS
			return "SomeRandomAccessToken";
		}
		return "";
	}

	@Override
	public boolean print(String filename, String printer, String accessToken) throws RemoteException {
		// TODO SEBASTIAN TOKENS (maybe pull all these calls to service?
		if(accessToken != "checksomerandom accessToken") {
			throw new RemoteException("Access not allowed");
		}
		System.out.println("Client requested "+filename+" on printer: "+printer);
		return true;
	}

	@Override
	public String queue(String printer, String accessToken) throws RemoteException {
		// TODO SEBASTIAN TOKENS
		if(accessToken != "checksomerandom accessToken") {
			throw new RemoteException("Access not allowed");
		}
		System.out.println("Client requested the queue on following printer: "+printer);
		return "Printer queue data.";
	}

	@Override
	public boolean topQueue(String printer, int job, String accessToken) throws RemoteException {
		// TODO SEBASTIAN TOKENS
		if(accessToken != "checksomerandom accessToken") {
			throw new RemoteException("Access not allowed");
		}
		System.out.println("Putting job #"+job+" at the top on printer: "+printer);
		return true;
	}

	@Override
	public boolean start(String accessToken) throws RemoteException {
		// TODO SEBASTIAN TOKENS
		if(accessToken != "checksomerandom accessToken") {
			throw new RemoteException("Access not allowed");
		}
		System.out.println("Starting the printer service.");
		return true;
	}

	@Override
	public boolean stop(String accessToken) throws RemoteException {
		// TODO SEBASTIAN TOKENS
		if(accessToken != "checksomerandom accessToken") {
			throw new RemoteException("Access not allowed");
		}
		System.out.println("Stopping the printer service.");
		return true;
	}

	@Override
	public boolean restart(String accessToken) throws RemoteException {
		// TODO SEBASTIAN TOKENS
		if(accessToken != "checksomerandom accessToken") {
			throw new RemoteException("Access not allowed");
		}
		System.out.println("Restarting the printer service.");
		return true;
	}

	@Override
	public String status(String printer, String accessToken) throws RemoteException {
		// TODO SEBASTIAN TOKENS
		if(accessToken != "checksomerandom accessToken") {
			throw new RemoteException("Access not allowed");
		}
		System.out.println("Client requested status of printer: "+printer);
		return "Status of printer data.";
	}

	@Override
	public String readConfig(String parameter, String accessToken) throws RemoteException {
		// TODO SEBASTIAN TOKENS
		if(accessToken != "checksomerandom accessToken") {
			throw new RemoteException("Access not allowed");
		}
		System.out.println("Client requested to read config of parameter.");
		return "Config parameter data.";
	}

	@Override
	public boolean setConfig(String parameter, String value, String accessToken) throws RemoteException {
		// TODO SEBASTIAN TOKENS
		if(accessToken != "checksomerandom accessToken") {
			throw new RemoteException("Access not allowed");
		}
		System.out.println("Client setting config parameter: "+parameter+" with value: "+value);
		return true;
	}

	
	

}
