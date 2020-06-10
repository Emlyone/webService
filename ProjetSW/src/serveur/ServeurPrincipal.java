package serveur;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import chat.ChatRoom;
import chat.User;
import sp.SP;

@Path("SP")
public class ServeurPrincipal implements SP{
	static Registry registry;
	private static Hashtable<String,ChatRoom> listeServeurs = new Hashtable<String , ChatRoom>();
	
	
	@POST
	@Path("/send/{p1}/{p2}/{p3}")
	public void sendMessage(@PathParam("p1") String salonName,@PathParam("p2") String pseudo,@PathParam("p3") String message) throws RemoteException {
		ChatRoom salon = getSalon(salonName);
		salon.postMessage(pseudo, message);
	}
		
	@GET
	@Path("/refresh/{p}")
	public String getMessages(@PathParam("p") String SalonName){
		ChatRoom salon = getSalon(SalonName);
		return salon.getMessage();
	}
	 
	@GET
	@Path("/salons")
	public String getSalons() {
		getSalon("Salon1");
		getSalon("Salon2");
		String salonn = "";
		Enumeration<ChatRoom> tab = listeServeurs.elements();
		while(tab.hasMoreElements()){
			salonn += tab.nextElement().getTheNom();
		}
		return salonn;
	}
	

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/users/{p}")
	public String getUsersSalon(@PathParam("p") String salon) throws RemoteException {
		ChatRoom c = getSalon(salon);
		
		return c.getListeUsers().toString(); 
	}
	
	
	@POST
	@Produces(MediaType.TEXT_XML)
	@Path ("/connect/{p1}/{p2}")
	public void connectSalon(@PathParam("p1") String salonName,@PathParam("p2") String pseudo) throws RemoteException {
		ChatRoom salon = getSalon(salonName);
		User u = new User(pseudo);
		salon.subscribe(u, pseudo);
	}

		
	
	@POST
	@Produces(MediaType.TEXT_XML)
	@Path ("/deconnect/{p1}/{p2}")
	public void disconnectSalon(@PathParam("p1") String salonName,@PathParam("p2") String pseudo) throws RemoteException {
		ChatRoom salon = getSalon(salonName);
		salon.unsubscribe(pseudo);		
	}

	
	public ChatRoom getSalon(String salon){
		if(listeServeurs.containsKey(salon)){
			//System.out.println("je suis deja dedans");
			return listeServeurs.get(salon);
		} else {
		try { 
			registry = LocateRegistry.getRegistry(1099);
			ChatRoom chatroom = (ChatRoom) registry.lookup(salon);
			listeServeurs.putIfAbsent(chatroom.getTheNom(), chatroom);
			//System.out.println("Je ne suis pas dedans et charrrom.getnom = "+chatroom.getTheNom());
			System.err.println("Connecté au serveur");
			return chatroom;
		} catch (Exception e) {
			System.err.println("UserExport exception: " + e.toString()); e.printStackTrace();
		}
		return null;
    	
		}
	}

	
}
