package me.ninjazidane.valve;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.ninjazidane.spoututils.CustomSpoutListener;
import me.ninjazidane.valve.listeners.ValvePlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.FileManager;

public class Valve extends JavaPlugin {

    Logger log = Logger.getLogger("minecraft");
    
    protected static Configuration pCONF = null;
    
    PluginDescriptionFile info = null;
    
    PluginManager pm = null;
    
    private final ValvePlayerListener pListener = new ValvePlayerListener();
    private final CustomSpoutListener sListener = new CustomSpoutListener();
    
    @Override
    public void onDisable() {
        log.log(Level.INFO, "[Valve] Disabled");
    }

    @Override
    public void onEnable() {
        
        info = getDescription();
        
        pm = this.getServer().getPluginManager();
        
        //Check to see if Spout is installed on the server...download if allowed or disable
        if (pm.getPlugin("Spout") == null) {
            log.log(Level.SEVERE, "[Valve] Spout was not found! Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        
        FileManager fm = SpoutManager.getFileManager();
        fm.addToPreLoginCache(this, "http://dl.dropbox.com/u/37060654/Bukkit/Texture%20Packs/Misa.zip");
            
        pm.registerEvent(Event.Type.CUSTOM_EVENT, sListener, Priority.Normal, this);
            
        pm.registerEvent(Event.Type.PLAYER_JOIN, pListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_KICK, pListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_PORTAL, pListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, pListener, Priority.Monitor, this);
            
        log.log(Level.INFO, "[Valve] Enabled");
    }
}