package me.ninjazidane.valve;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.ninjazidane.valve.listeners.ValvePlayerListener;
import me.ninjazidane.valve.listeners.ValveSpoutListener;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.FileManager;

public class Valve extends JavaPlugin {

    Logger log = Logger.getLogger("minecraft");
    
    protected static Configuration pCONF = null;
    
    PluginDescriptionFile info = null;
    
    PluginManager pm = null;
    
    private final ValvePlayerListener pListener = new ValvePlayerListener(this);
    private final ValveSpoutListener sListener = new ValveSpoutListener(this);
    
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
            log.log(Level.SEVERE, "[Valve] Spout was not found!");
            //if (pCONF.getBoolean("autodownload-spout", true)) {
                try {
                    download(log, new URL("http://ci.getspout.org/job/Spout/Recommended/artifact/target/Spout.jar"), new File("plugins/Spout.jar"));
                    try {
                        pm.loadPlugin(new File("plugins/Spout.jar"));
                    } catch (InvalidPluginException ex) {
                        Logger.getLogger(Valve.class.getName()).log(Level.SEVERE, "[Valve] Error while loading Spout!\n" + ex);
                    } catch (InvalidDescriptionException ex) {
                        Logger.getLogger(Valve.class.getName()).log(Level.SEVERE, "[Valve] Error while loading Spout!\n" + ex);
                    } catch (UnknownDependencyException ex) {
                        Logger.getLogger(Valve.class.getName()).log(Level.SEVERE, "[Valve] Error while loading Spout!\n" + ex);
                    }
                    
                    pm.enablePlugin(pm.getPlugin("Spout"));
                
                } catch (IOException ex) {
                    Logger.getLogger(Valve.class.getName()).log(Level.SEVERE, "[Valve] Error while downloading Spout!\n" + ex);
                    
                }
                
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
            
//        } else {
//            
//            
//            pm.disablePlugin(this);
//        }
    }
    
    public static void download(Logger log, URL url, File file) throws IOException {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
                }
		if (file.exists()) {
			file.delete();
                }
		file.createNewFile();
		final int size = url.openConnection().getContentLength();
		log.info("Downloading " + file.getName() + " (" + size / 1024 + "kb)...");
		final InputStream in = url.openStream();
		final OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		final byte[] buffer = new byte[1024];
		int len, downloaded = 0, msgs = 0;
		final long start = System.currentTimeMillis();
		while ((len = in.read(buffer)) >= 0) {
			out.write(buffer, 0, len);
			downloaded += len;
			if ((int)((System.currentTimeMillis() - start) / 500) > msgs) {
				log.info((int)(downloaded / (double)size * 100d) + "%");
				msgs++;
			}
		}
		in.close();
		out.close();
		log.info("Download finished");
    }
}