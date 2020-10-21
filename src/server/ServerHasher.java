package server;

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
	
	//Main for generating passwords / salts for the passwords
	public static void main(String[] args) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String byteString = "";
		for(int i = 0; i < salt.length; i++) {
			byteString += salt[i]+",";
		}
		System.out.println("Used salt: "+new String(salt, StandardCharsets.UTF_8)+" vs in bytes: "+byteString);
		
		String hashedProduct = hashWithSha512("asdfghjklqwertyui", salt);
		System.out.println("Hashed password: "+hashedProduct);
		
		
	}
	
}
