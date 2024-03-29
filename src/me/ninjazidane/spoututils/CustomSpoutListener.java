
package me.ninjazidane.spoututils;

import org.bukkit.Material;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;
import org.getspout.spoutapi.player.SpoutPlayer;

public class CustomSpoutListener extends SpoutListener {
    
    @Override
    public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {
        SpoutPlayer spoutPlayer = (SpoutPlayer) event.getPlayer();
        spoutPlayer.setTexturePack("http://dl.dropbox.com/u/37060654/Bukkit/Texture%20Packs/Misa.zip");
        spoutPlayer.sendNotification("InspireNXE Publio", spoutPlayer.getWorld().getName(), Material.ENDER_PEARL);
    }
}