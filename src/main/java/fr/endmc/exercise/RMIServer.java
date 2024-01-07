package fr.endmc.exercise;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;

public class RMIServer extends JavaPlugin {

	private static RMIServer instance;

	@Override
	public void onEnable() {
		instance = this;

		try {
			// Create the registry if it doesn't exist
			Registry registry = LocateRegistry.createRegistry(1099);
			getLogger().info("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/");

			// Create a player
			Player player = new PlayerImpl(UUID.fromString("c8271ff5-88bb-40ca-9cb9-fb8475bd5c07"), "ZebulonFr");
			Remote playerRemote = UnicastRemoteObject.exportObject(player, 0);
			registry.bind("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/Player", playerRemote);

			// Create a server
			Server server = new ServerImpl(List.of(player));
			Remote serverRemote = UnicastRemoteObject.exportObject(server, 0);
			registry.bind("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/Server", serverRemote);

			getLogger().info("Plugin enabled successfully!");

			// I don't know why, but I have to wait 5 seconds to start the RMI Client to access to the RMI Server
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static RMIServer get() {
		return instance;
	}
}