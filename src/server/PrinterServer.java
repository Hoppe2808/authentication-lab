package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrinterServer {

	public static final int PORT = 5099;

	public static void main(String[] args) throws RemoteException {
		System.out.println(authenticate("Robin", "niboR"));
		Registry registry = LocateRegistry.createRegistry(PrinterServer.PORT);
		registry.rebind("printer", new PrinterServant());

	}

	public static boolean authenticate(String username, String password) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("PASSWORD.txt"));
			String line = reader.readLine();
			while (line != null) {
				String[] combination = line.split(" ");
				if (combination[0].equals(username)
						&& ServerHasher.getSHA512SecurePassword(password, combination[2]).equals(combination[1])) {
					reader.close();
					return true;
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException error) {
			error.printStackTrace();
		}
		return false;

	}
}
