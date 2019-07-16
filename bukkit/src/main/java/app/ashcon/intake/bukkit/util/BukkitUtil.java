package app.ashcon.intake.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Utility methods for accessing various {@link Bukkit} APIs.
 */
public class BukkitUtil {

    public static World getWorld(CommandSender sender) {
        if (sender instanceof Player) {
            return ((Player) sender).getWorld();
        }
        else if (sender instanceof Block) {
            return ((Block) sender).getWorld();
        }
        else if (sender instanceof Entity) {
            return ((Entity) sender).getWorld();
        }
        else {
            return null;
        }
    }

}
