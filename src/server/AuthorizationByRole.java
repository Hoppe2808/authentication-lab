package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class AuthorizationByRole {

    HashMap<String, HashSet<String>> permissions;
    HashMap<String, HashSet<String>> hierarchy;
    HashMap<String, String> userRole;

    public AuthorizationByRole() throws FileNotFoundException {
        /*
         * Loads all roles, permissions and users associated to roles.
         */
        File file = new File("ROLES_DESCRIPTION.txt");
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split(" | ");
            HashSet<String> parents = new HashSet<String>(Arrays.asList(data[1].split(" ")));
            hierarchy.put(data[0], parents);
            HashSet<String> roleRights = new HashSet<String>(Arrays.asList(data[2].split(" ")));
            permissions.put(data[0], roleRights);
        }
        reader.close();
        for (String role : permissions.keySet()) {
            exploreParentPermissions(role);
        }
        file = new File("ROLES_ATTRIBUTION.txt");
        reader = new Scanner(file);
        String[] line;
        while (reader.hasNextLine()) {
            line = reader.nextLine().split(" ");
            userRole.put(line[0], line[1]);
        }
        reader.close();
    }

    public HashSet<String> exploreParentPermissions(String childRoleName) {
        /*
         * Recursively iterate through the parents of a role to build all its
         * permissions.
         */
        if (this.hierarchy.get(childRoleName).size() == 0) {
            return this.permissions.get(childRoleName);
        } else {
            HashSet<String> additionalPermissions = new HashSet<String>();
            if (this.permissions.get(childRoleName).size() > 0) {
                additionalPermissions.addAll(this.permissions.get(childRoleName));
            }
            for (String parent : this.hierarchy.keySet()) {
                this.hierarchy.get(parent).remove(parent);
                additionalPermissions.addAll(exploreParentPermissions(parent));
                this.permissions.get(parent).addAll(additionalPermissions);
            }
            return additionalPermissions;
        }
    }

    public boolean checkPermission(String functionName, String role) {
        /*
         * Checks whether a role has a permission or not.
         */
        return this.permissions.get(role).contains(functionName);
    }

    public String getRoleForUser(String user) {
        if (this.userRole.containsKey(user)) {
            return this.userRole.get(user);
        }
        return "";
    }
}
