package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

public class AuthorizationByRole {

    HashMap<String, String[]> permissions;

    public void initPermissions() throws FileNotFoundException {

        File file = new File("ROLES_DESCRIPTION.txt");
        Scanner reader = new Scanner(file);
        HashMap<String, String[]> roles = new HashMap<>();
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split(" | ");
            roles.put(data[0], data[1].split(" "));
            permissions.put(data[0], data[2].split(" "));
        }
        for (String role : permissions.keys())
            for (String child : permissions) {
                permissions.put(role, permissions.get(role) + roles.get())
            }
    }

    public static boolean checkPermission(String functionName, String role) throws FileNotFoundException {

    }

    public String getRoleForUser(String user) throws FileNotFoundException {
        File file = new File("ROLES_ATTRIBUTION.txt");
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(" ");
            if (line[0].equals(user)) {
                return line[1];
            }
        }
        reader.close();
        return "";
    }

}
