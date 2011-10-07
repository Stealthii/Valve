package me.ninjazidane.spoututils;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.material.item.GenericCustomItem;

public class CustomItem extends GenericCustomItem {
    
    public CustomItem (Plugin plugin, String itemName) {
        super (plugin, itemName);
    }
}