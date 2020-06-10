package sp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import chat.ChatRoom;
import chat.User;

public interface SP {
	public void sendMessage(String salon, String pseudo, String message) throws RemoteException;
	public String getSalons();
	public void connectSalon(String salon, String pseudo) throws RemoteException;
	public void disconnectSalon(String salon, String pseudo) throws RemoteException;
}
