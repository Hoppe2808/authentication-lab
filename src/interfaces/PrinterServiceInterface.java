package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrinterServiceInterface extends Remote{

	public String login(String username, String password) throws RemoteException;

	public boolean print(String filename, String printer, String accessToken) throws RemoteException;   // prints file filename on the specified printer
	
	public String queue(String printer, String accessToken) throws RemoteException;   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
	
	public boolean topQueue(String printer, int job, String accessToken) throws RemoteException;   // moves job to the top of the queue
	
	public boolean start(String accessToken) throws RemoteException;   // starts the print server
	
	public boolean stop(String accessToken) throws RemoteException;   // stops the print server
	
	public boolean restart(String accessToken) throws RemoteException;   // stops the print server, clears the print queue and starts the print server again
	
	public String status(String printer, String accessToken) throws RemoteException;  // prints status of printer on the user's display
	
	public String readConfig(String parameter, String accessToken) throws RemoteException;   // prints the value of the parameter on the user's display
	
	public boolean setConfig(String parameter, String value, String accessToken) throws RemoteException;   // sets the parameter to value
	
}
