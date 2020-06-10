package chat;

import java.rmi.*;
import java.util.ArrayList;
import java.util.Hashtable;


public interface ChatRoom extends Remote {
	public void subscribe(User user, String pseudo) throws RemoteException;
	public void unsubscribe(String pseudo) throws RemoteException;
	public String getTheNom();
	public void postMessage(String pseudo, String message) throws RemoteException;
	public Hashtable<String, User> getListeUsers() throws RemoteException;
	public String getMessage();
}
