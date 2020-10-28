package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ServerHasher {

	private static MessageDigest md;	
	
	public static String hashWithSha512(String password, byte[] salt) {
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			
			//Convert password to bytes
			byte[] passBytes = password.getBytes(StandardCharsets.UTF_8);
			
			//Get hashed password from password + salt
			byte[] hashedPassword = md.digest(passBytes);
			
			//Create into string
			String hashedString = new String(hashedPassword, StandardCharsets.UTF_8);
			
			return hashedString;
			
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	
	//username hashedpassword salt
	//user -g dfg 
	
	//Main for generating passwords / salts for the passwords
	public static void main(String[] args) throws IOException {
		File file = new File("PASSWORD.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		PrintWriter writer = new PrintWriter(new File("PASSWORD.txt"));
		
		
		
		
		String[] users = new String[] {"Robin", "Morten", "Lukas", "Sebastian"};
		String[] passwords = new String[] {"niboR", "netroM", "sakuL", "naitsabeS"};
		
		for(int i = 0; i < users.length; i++) {
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			String byteString = "";
			for(int j = 0; j < salt.length; j++) {
				byteString += salt[j]+",";
			}
			String saltedString = new String(salt, StandardCharsets.UTF_8);
			System.out.println("Used salt: "+saltedString+" vs in bytes: "+byteString);
			
			String hashedProduct = hashWithSha512(passwords[i], salt);
			
			System.out.println("Hashed password: "+hashedProduct);
			writer.println(users[i]+" "+hashedProduct+" "+saltedString);
		}
		writer.close();
		
	}
	
}
