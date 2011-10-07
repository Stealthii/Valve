package me.ninjazidane.valve.listeners;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ValvePlayerListener extends PlayerListener{
    
    private static Map<Player, Boolean> kickedPlayers = new HashMap<Player,Boolean>();
    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        
        SpoutPlayer otherPlayer;
               
        //Broadcast to other players that someone joined
        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
            otherPlayer = (SpoutPlayer) player;
            otherPlayer.sendNotification(event.getPlayer().getName(), "Joined " + event.getPlayer().getWorld().getName(), Material.CAKE);
        }
    }
    
    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        
        SpoutPlayer spoutPlayer;
        
        //If the player was kicked, don't display more than one notification
        if (kickedPlayers.containsKey(event.getPlayer())) {
            kickedPlayers.remove(event.getPlayer());
            return;
        }
        
        //Broadcast to other players that someone joined
        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
            spoutPlayer = (SpoutPlayer) player;
            
            if (spoutPlayer.isSpoutCraftEnabled()) {
                spoutPlayer.sendNotification(event.getPlayer().getName(), "Quit", Material.APPLE);
            }
        }
    }
    
    @Override
    public void onPlayerKick(PlayerKickEvent event) {
        
        SpoutPlayer spoutPlayer = (SpoutPlayer) event.getPlayer();
        
        //Register this player to the kick event
        kickedPlayers.put(event.getPlayer(), true);
        
        //Broadcast to other players that someone was kicked
        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
            spoutPlayer = (SpoutPlayer) player;
            
            if (spoutPlayer.isSpoutCraftEnabled()) {
                spoutPlayer.sendNotification(event.getPlayer().getName(), "kicked!", Material.APPLE);
            }
        }   
    }
   
   @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        
        SpoutPlayer spoutPlayer;
        
        /**
         * TODO: Implement WorldChangeEvent from Bukkit for this
         */
        if (!event.getFrom().getWorld().equals(event.getTo().getWorld())) {
            for (Player player: Bukkit.getServer().getOnlinePlayers()) {
                spoutPlayer = (SpoutPlayer) player;
                spoutPlayer.sendNotification(event.getPlayer().getName(), "switched to " + event.getTo().getWorld().getName(), Material.GOLD_BLOCK);
            }
        }
   }
   
   @Override
    public void onPlayerPortal(PlayerPortalEvent event) {
        
        SpoutPlayer spoutPlayer;
        /**
         * TODO: Implement WorldChangeEvent from Bukkit for this
         */
        SpoutManager.getAppearanceManager().setGlobalCloak(event.getPlayer(), "http://dl.dropbox.com/u/37060654/Bukkit/Cloaks/Redstone.png");
        SpoutManager.getAppearanceManager().setGlobalTitle(event.getPlayer(), "BananaPeople");
        
        if (!event.getFrom().getWorld().equals(event.getTo().getWorld())) {
            for (Player player: Bukkit.getServer().getOnlinePlayers()) {
                spoutPlayer = (SpoutPlayer) player;
                spoutPlayer.sendNotification(event.getPlayer().getName(), "switched to " + event.getTo().getWorld().getName(), Material.GOLD_BLOCK);
            }
        }
   }
}