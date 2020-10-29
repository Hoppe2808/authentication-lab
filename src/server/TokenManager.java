package server;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class TokenManager {
    ArrayList<String> tokens = new ArrayList<>();

    public String generateToken(String username) {
        UUID id = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();

        String token = id.toString() + timestamp + username;
        tokens.add(token);
        return token;
    }

    public boolean validateToken(String token) {
        if (tokens.contains(token)) {
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
}
