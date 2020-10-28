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
	
	//This function has been made with inspiration from stackoverflow
	public static String getSHA512SecurePassword(String passwordToHash, String salt){
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < bytes.length; i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public static String byte2string(byte[] bytes){
		BigInteger number = new BigInteger(1, bytes);
		String stringText = number.toString(16);
			
		while(stringText.length() < 32) {
			stringText = "0" + stringText;
		}
		return stringText;
	}
	
	
	
	//This function is used for generating hashing the passwords with the generated salts, and saving them in a file
	public static void main(String[] args) throws IOException {
		File file = new File("PASSWORD.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		PrintWriter writer = new PrintWriter(new File("PASSWORD.txt"));
		
		String[] users = new String[] {/*INSERT USERNAMES*/};
		String[] passwords = new String[] {/*INSERT PASSWORDS*/};
		
		for(int i = 0; i < users.length; i++) {
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			salt = byte2string(salt).getBytes();
			String hashedProduct = getSHA512SecurePassword(passwords[i], byte2string(salt));
			writer.println(users[i]+" "+hashedProduct+" "+ byte2string(salt));
				
		}
		writer.close();
	}
	
}
