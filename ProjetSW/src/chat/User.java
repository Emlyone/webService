package chat;

import java.io.Serializable;

public class User implements ChatUser, Serializable {
	String pseudo;
	
	public User(String pseudo){
		this.pseudo = pseudo;
	}

	public void displayMessage(String message) {
		
		
	}
	
}
