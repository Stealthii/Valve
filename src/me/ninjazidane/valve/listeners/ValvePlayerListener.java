/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ninjazidane.valve.listeners;

import java.util.HashMap;
import java.util.Map;
import me.ninjazidane.valve.Valve;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 *
 * @author Chris
 */
public class ValvePlayerListener extends PlayerListener{
    
    private Valve plugin;
    private static Map<Player, Boolean> kickedPlayers = new HashMap<Player,Boolean>();
    
    public ValvePlayerListener (Valve plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        
        SpoutPlayer spoutPlayer, otherPlayer;
        
        spoutPlayer = (SpoutPlayer) event.getPlayer();
        
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