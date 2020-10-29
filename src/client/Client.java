package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import interfaces.PrinterServiceInterface;
import server.PrinterServer;

public class Client {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

		String accessToken = "";

		PrinterServiceInterface service = (PrinterServiceInterface) Naming.lookup("rmi://localhost:"+PrinterServer.PORT+"/printer");
		Scanner in = new Scanner(System.in);

		System.out.println("Commands are: \n login username password, print filename printer, queue printer, topQueue printer job, start, stop, restart, status printer, readConfig parameter, setConfig parameter value");
		boolean running = true;
		while (running) {
			System.out.println("Enter commeand here: ");
			String input = in.nextLine();
			String[] input2 = input.split(" ");
			switch (input2[0]) {

				case "login":
					if (input2.length != 3){
						System.out.println("Follow standards for login, please...");
						break;
					}
					String username = input2[1];
					String password = input2[2];

					accessToken = service.login(username, password);
					break;

				case "print":
					if(input2.length != 3) {
						System.out.println("Follow standards for command: "+input2[0]);
						break;
					}

					try {
						System.out.println(service.print(input2[1], input2[2], accessToken));
					} catch (RemoteException e) {
						System.out.println("Something went wrong: "+ e.getMessage());
					}
					break;

				case "queue":
					if(input2.length != 2) {
						System.out.println("Follow standards for command: "+input2[0]);
						break;
					}
					try {
						System.out.println(service.queue(input2[1], accessToken));
					} catch (RemoteException e) {
						System.out.println("Something went wrong: "+ e.getMessage());
					}
					break;

				case "topQueue":
					if(input2.length != 3) {
						System.out.println("Follow standards for command: "+input2[0]);
						break;
					}
					try {
						System.out.println(service.topQueue(input2[1], Integer.parseInt(input2[2]), accessToken));
					} catch (RemoteException e) {
						System.out.println("Something went wrong: "+ e.getMessage());
					}
					break;

				case "start":
					try {
						System.out.println(service.start(accessToken));
					} catch (RemoteException e) {
						System.out.println("Something went wrong: "+ e.getMessage());
					}
					break;

				case "stop":
					try {
						System.out.println(service.stop(accessToken));
					} catch (RemoteException e) {
						System.out.println("Something went wrong: "+ e.getMessage());
					}
					System.out.println("Stopping");
					running = false;
					break;

				case "restart":
					try {
						System.out.println(service.restart(accessToken));
					} catch (RemoteException e) {
						System.out.println("Something went wrong: "+ e.getMessage());
					}
					break;

				case "status":
					if(input2.length != 2) {
						System.out.println("Follow standards for command: "+input2[0]);
						break;
					}
					try {
						System.out.println(service.status(input2[1], accessToken));
					} catch (RemoteException e) {
						System.out.println("Something went wrong: "+ e.getMessage());
					}
					break;

				case "readConfig":
					if(input2.length != 2) {
						System.out.println("Follow standards for command: "+input2[0]);
						break;
					}
					try {
						System.out.println(service.readConfig(input2[1], accessToken));
					} catch (RemoteException e) {
						System.out.println("Something went wrong: "+ e.getMessage());
					}
					break;

				case "setConfig":
					if(input2.length != 3) {
						System.out.println("Follow standards for command: "+input2[0]);
						break;
					}
					try {
						System.out.println(service.setConfig(input2[1], input2[2], accessToken));
					} catch (RemoteException e) {
						System.out.println("Something went wrong: "+ e.getMessage());
					}
					break;

				default:
					System.out.println("You entered something bad, try again\n");
					break;
			}
		}
	}
	
}
