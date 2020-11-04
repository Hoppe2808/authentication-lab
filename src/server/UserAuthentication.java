package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserAuthentication {
    public boolean checkPermission(String username, String action) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("UserPermissions.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] combination = line.split(" ");
                if (combination[0].equals(username)) {
                    for (String permission : combination) {
                        if (permission.equals(action)) {
                            reader.close();
                            return true;
                        }
                    }
                    reader.close();
                    return false;
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
