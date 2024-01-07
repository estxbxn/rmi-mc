package fr.endmc.exercise;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public class ServerImpl implements Server, Serializable {

	private final List<Player> players;

	public ServerImpl(List<Player> players) {
		this.players = players;
	}

	@Override
	public Player getPlayer(UUID uuid) throws RemoteException {
		for (Player player : players) {
			if (player.getUniqueId() == uuid) return player;
		}
		return null;
	}

	@Override
	public List<Player> getPlayers() throws RemoteException {
		return players;
	}

	@Override
	public int getMaxPlayers() throws RemoteException {
		return players.size();
	}

	@Override
	public void setBlock(String worldName, int x, int y, int z, String type) throws RemoteException {
		// Define the location
		Location location = new Location(Bukkit.getWorld(worldName), x, y, z);

		// Define the block type
		Material blockType = Material.valueOf(type);

		// Set block have to be async
		Bukkit.getScheduler().runTask(RMIServer.get(), () -> location.getBlock().setType(blockType));
	}

	@Override
	public String getBlock(String worldName, int x, int y, int z) throws RemoteException {
		// Define the location
		Location blockLocation = new Location(Bukkit.getWorld(worldName), x, y, z);

		// Return the block type
		return blockLocation.getBlock().getType().name();
	}
}