package server;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TokenManager {
	HashMap<String, UserData> tokens = new HashMap<>();

    public String generateToken(String username, String role) {
        UUID id = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();

        String token = id.toString() + timestamp + username;
        tokens.put(token, new UserData(username, role));
        return token;
    }

    public boolean validateToken(String token) {
        if (tokens.containsKey(token)) {
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime tokenTime = LocalDateTime.parse(token.substring(36, 59));

            if (currentTime.compareTo(tokenTime.plusMinutes(30)) > 0) {
                //Token expired
                tokens.remove(token);
                return false;
            }
            return true;
        }
        return false;
    }
    
    public UserData getDataOfToken(String token) {
	    if(tokens.containsKey(token)) {
	    	return tokens.get(token);
	    }
	    return null;
    }
}
