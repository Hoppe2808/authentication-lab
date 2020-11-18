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
    HashMap<String, HashSet<String>> userRole;

    public AuthorizationByRole() throws FileNotFoundException {
        /*
         * Loads all roles, permissions and users associated to roles.
         */
        permissions = new HashMap<String, HashSet<String>>();
        hierarchy = new HashMap<String, HashSet<String>>();
        userRole = new HashMap<String, HashSet<String>>();
        File file = new File("ROLES_DESCRIPTION.txt");
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split(":");
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
            userRole.put(line[0], new HashSet<String>());
            for (int i = 1; i < line.length; i++) {
                userRole.get(line[0]).add(line[i]);
            }
        }
        reader.close();
    }

    public HashSet<String> exploreParentPermissions(String childRoleName) {
        /*
         * Recursively iterate through the parents of a role to build all its
         * permissions.
         */
        if (this.hierarchy.get(childRoleName) != null) {
            if (this.hierarchy.get(childRoleName).size() == 0) {
                return this.permissions.get(childRoleName);
            } else {
                HashSet<String> additionalPermissions = new HashSet<String>();
                if (this.permissions.get(childRoleName).size() > 0) {
                    additionalPermissions.addAll(this.permissions.get(childRoleName));
                }
                for (String parent : this.hierarchy.get(childRoleName)) {
                    additionalPermissions.addAll(exploreParentPermissions(parent));
                    this.permissions.get(childRoleName).addAll(additionalPermissions);
                }
                return additionalPermissions;
            }
        }
        return new HashSet<String>();
    }

    public boolean checkPermission(String functionName, HashSet<String> roles) {
        /*
         * Checks whether a role has a permission or not.
         */
        return roles.stream().anyMatch(x -> this.permissions.get(x).contains(functionName));
    }

    public HashSet<String> getRoleForUser(String user) {
        user = user.toLowerCase();
        if (this.userRole.containsKey(user)) {
            return this.userRole.get(user);
        }
        return new HashSet<String>();
    }
}
