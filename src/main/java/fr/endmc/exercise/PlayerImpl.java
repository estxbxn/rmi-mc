package fr.endmc.exercise;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

public class PlayerImpl implements Player, Serializable {

	private final UUID uniqueId;
	private final String name;

	public PlayerImpl(UUID uniqueId, String name) {
		this.uniqueId = uniqueId;
		this.name = name;
	}

	@Override
	public UUID getUniqueId() throws RemoteException {
		return uniqueId;
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public void sendMessage(String message) throws RemoteException {
		org.bukkit.entity.Player player = Bukkit.getPlayer(uniqueId);

		// Check if the player exists
		if (player == null || !player.isOnline()) {
			System.out.println("Player is not online or does not exist!");
			return;
		}

		// Send the message to the player
		player.sendMessage(message);
	}

	@Override
	public void teleport(String worldName, double x, double y, double z) throws RemoteException {
		org.bukkit.entity.Player player = Bukkit.getPlayer(uniqueId);

		// Check if the player exists
		if (player == null || !player.isOnline()) {
			System.out.println("Player is not online or does not exist!");
			return;
		}

		// Define the location
		Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
		if (location.getWorld() == null || !location.isWorldLoaded()) {
			System.out.println("The world is not loaded or does not exist!");
			return;
		}

		// Teleport the player to the location have to be async
		Bukkit.getScheduler().runTask(RMIServer.get(), () -> player.teleport(location));
	}

	@Override
	public void spawnParticle(String particleType, String worldName, double x, double y, double z) throws RemoteException {
		org.bukkit.entity.Player player = Bukkit.getPlayer(uniqueId);

		// Check if the player exists
		if (player == null || !player.isOnline()) {
			System.out.println("Player is not online or does not exist!");
			return;
		}

		// Define the location
		Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
		if (location.getWorld() == null || !location.isWorldLoaded()) {
			System.out.println("The world is not loaded or does not exist!");
			return;
		}

		// Define the particle
		Particle particle = Particle.valueOf(particleType);
		int particleCount = 10;

		// Spawn the particle to the location
		player.spawnParticle(particle, location, particleCount);
	}
}