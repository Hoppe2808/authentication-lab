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
	
	
	public static String getSHA512SecurePassword(String passwordToHash, String salt){
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			System.out.println(salt);
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public static String byte2string(byte[] bytes){
		BigInteger number = new BigInteger(1, bytes);
			
			String hashedText = number.toString(16);
			
			while(hashedText.length() < 32) {
				hashedText = "0" + hashedText;
			}
			return hashedText;
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
		String[] passwords = new String[] {"niboR", "netroM", "sakuL", "naitsabeS"};
		
		for(int i = 0; i < users.length; i++) {
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			salt = byte2string(salt).getBytes();
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
			
			String hashedProduct = getSHA512SecurePassword(passwords[i], byte2string(salt));
			
			
			writer.println(users[i]+" "+hashedProduct+" "+ byte2string(salt));
			
			
		}
		//outputStream.close();
		writer.close();
	}
	
}
