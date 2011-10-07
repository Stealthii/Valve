package me.ninjazidane.spoututils;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;

//TODO Wait for Spout Tool API
public class CustomBlock extends GenericCubeCustomBlock {
    
    public CustomBlock (Plugin plugin, String blockName, String blockLOC, int width, int length, int spriteSize) {
        super(plugin, blockName, true, new GenericCubeBlockDesign(plugin, new Texture(blockLOC, width, length, spriteSize), 0), 0);
    }
}