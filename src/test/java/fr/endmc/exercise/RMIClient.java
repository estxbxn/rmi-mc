package fr.endmc.exercise;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, UnknownHostException {
        Registry registry = LocateRegistry.getRegistry(1099);

        /*
         * Get player information
         */
        Player remotePlayer = (Player) registry.lookup("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/Player");
        System.out.println("Player ID: " + remotePlayer.getUniqueId());
        System.out.println("Player Name: " + remotePlayer.getName());
        remotePlayer.sendMessage("§aHello from remotePlayer");
        // remotePlayer.teleport("world", -7, 64, -1);
        remotePlayer.spawnParticle("world", "lava", -7, 64, -1);

        /*
         * Get server information
         */
        Server remoteServer = (Server) registry.lookup("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/Server");

        // Player targetPlayer = remoteServer.getPlayer(UUID.fromString("c8271ff5-88bb-40ca-9cb9-fb8475bd5c07"));
        // targetPlayer.sendMessage("§cHello from remoteServer");

        System.out.println("Players : " + remoteServer.getPlayers());
        System.out.println("MaxPlayers : " + remoteServer.getMaxPlayers());

        remoteServer.setBlock("world", -7, 64, -1, "DIAMOND_BLOCK");
        remoteServer.setBlock("world", -7, 64, -1, "WOOL");

        System.out.println("Block type at (-7, 64, -1): " + remoteServer.getBlock("world", -7, 64, -1));
    }
}
