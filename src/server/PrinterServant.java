package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.PrinterServiceInterface;

public class PrinterServant extends UnicastRemoteObject implements PrinterServiceInterface {
	TokenManager tokenManager;
	AuthorizationByRole roleManager;
	UserAuthentication userRoleManager;
	
	protected PrinterServant() throws RemoteException {
		super();
		tokenManager = new TokenManager();
		roleManager = new AuthorizationByRole();
		userRoleManager = new UserAuthentication();
	}

	@Override
	public String login(String username, String password) throws RemoteException {
		if (PrinterServer.authenticate(username, password)) {
			if(!PrinterServer.ROLE_HIARCHY) {
				return tokenManager.generateToken(username, "");
			} else {
				String role = AuthorizationByRole.getRoleForUser(username);
				if(!role.equals("")) {
					return tokenManager.generateToken(username, role);
				}
			}
		}
		return "";
	}

	@Override
	public boolean print(String filename, String printer, String accessToken) throws RemoteException {
		if(!clientAllowedAccess(accessToken, "print")) {
			return false;
		}
		System.out.println("Client requested "+filename+" on printer: "+printer);
		return true;
	}

	@Override
	public String queue(String printer, String accessToken) throws RemoteException {
		if(!clientAllowedAccess(accessToken, "queue")) {
			return "Permission denied.";
		}
		System.out.println("Client requested the queue on following printer: "+printer);
		return "Printer queue data.";
	}

	@Override
	public boolean topQueue(String printer, int job, String accessToken) throws RemoteException {
		if(!clientAllowedAccess(accessToken, "topQueue")) {
			return false;
		}
		System.out.println("Putting job #"+job+" at the top on printer: "+printer);
		return true;
	}

	@Override
	public boolean start(String accessToken) throws RemoteException {
		if(!clientAllowedAccess(accessToken, "start")) {
			return false;
		}
		System.out.println("Starting the printer service.");
		return true;
	}

	@Override
	public boolean stop(String accessToken) throws RemoteException {
		if(!clientAllowedAccess(accessToken, "stop")) {
			return false;
		}
		System.out.println("Stopping the printer service.");
		return true;
	}

	@Override
	public boolean restart(String accessToken) throws RemoteException {
		if(!clientAllowedAccess(accessToken, "restart")) {
			return false;
		}
		System.out.println("Restarting the printer service.");
		return true;
	}

	@Override
	public String status(String printer, String accessToken) throws RemoteException {
		if(!clientAllowedAccess(accessToken, "status")) {
			return "Permission denied.";
		}
		System.out.println("Client requested status of printer: "+printer);
		return "Status of printer data.";
	}

	@Override
	public String readConfig(String parameter, String accessToken) throws RemoteException {
		if(!clientAllowedAccess(accessToken, "readConfig")) {
			return "Permission denied.";
		}
		System.out.println("Client requested to read config of parameter.");
		return "Config parameter data.";
	}

	@Override
	public boolean setConfig(String parameter, String value, String accessToken) throws RemoteException {
		if(!clientAllowedAccess(accessToken, "setConfig")) {
			return false;
		}
		System.out.println("Client setting config parameter: "+parameter+" with value: "+value);
		return true;
	}
	
	public boolean clientAllowedAccess(String accessToken, String function) throws RemoteException{
		if(!tokenManager.validateToken(accessToken)) {
			throw new RemoteException("Access not allowed");
		}
		if(PrinterServer.ROLE_HIARCHY) {
			if(!roleManager.checkPermission(function, tokenManager.getDataOfToken(accessToken).role)) {
				return false;
			}
		}
		else {
			if(!userRoleManager.checkPermission(tokenManager.getDataOfToken(accessToken).username, function)) {
				return false;
			}
		}
		return true;
	}
}
