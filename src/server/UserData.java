package server;

import java.util.HashSet;

public class UserData {

	public String username;
	public HashSet<String> roles;

	public UserData(String username, HashSet<String> roles) {
		this.username = username;
		this.roles = roles;
	}

}
