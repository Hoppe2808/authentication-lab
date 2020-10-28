package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
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
			byte[] passBytes = password.getBytes();
			
			//Get hashed password from password + salt
			byte[] hashedPassword = md.digest(passBytes);
			
			BigInteger number = new BigInteger(1, hashedPassword);
			
			String hashedText = number.toString(16);
			
			while(hashedText.length() < 32) {
				hashedText = "0" + hashedText;
			}
			
			//Create into string
			//String hashedString = new String(hashedPassword, StandardCharsets.UTF_8);
			
			return hashedText;
			
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return "";//new byte[0];
	}
	
	
	//username hashedpassword salt
	//user -g dfg 
	
	//Main for generating passwords / salts for the passwords
	public static void main(String[] args) throws IOException {
		File file = new File("PASSWORD.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		//OutputStream outputStream = new FileOutputStream("PASSWORD.txt");
		PrintWriter writer = new PrintWriter(new File("PASSWORD.txt"));
		
		
		
		String[] users = new String[] {"Robin", "Morten", "Lukas", "Sebastian"};
		String[] passwords = new String[] {"nibR", "netroM", "sakuL", "naitsabeS"};
		
		for(int i = 0; i < users.length; i++) {
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			String byteString = "";
			for(int j = 0; j < salt.length; j++) {
				byteString += salt[j]+",";
			}
			System.out.println("Used salt: "+byteString);
			
			//byte[] hashedProduct = hashWithSha512(passwords[i], salt);
			
			//outputStream.write(users[i].getBytes());
			//outputStream.write(' ');
			//outputStream.write(hashedProduct);
			//outputStream.write(' ');
			//outputStream.write(salt);
			//outputStream.write('\n');
			
			String hashedProduct = hashWithSha512(passwords[i], salt);
			BigInteger number = new BigInteger(1, salt);
			
			String saltedText = number.toString(16);
			
			while(saltedText.length() < 32) {
				saltedText = "0" + saltedText;
			}
			writer.println(users[i]+" "+hashedProduct+" "+saltedText);
			
			
		}
		//outputStream.close();
		writer.close();
	}
	
}
