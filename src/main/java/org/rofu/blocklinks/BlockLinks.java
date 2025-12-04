package org.rofu.blocklinks;

import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockLinks extends JavaPlugin {
    private List<String> commands;
    private List<String> actions;

    public BlockLinks() {
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.loadConfig();
        this.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aBlockLinks запущен!"));
    }

    private void loadConfig() {
        FileConfiguration config = this.getConfig();
        this.commands = config.getStringList("commands");
        this.actions = config.getStringList("actions");
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (this.commands.contains(command.getName().toLowerCase())) {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                this.executeActions(player);
                return true;
            } else {
                sender.sendMessage(this.colorize("&cЭта команда только для игроков!"));
                return true;
            }
        } else {
            return false;
        }
    }

    private void executeActions(Player player) {
        Iterator var2 = this.actions.iterator();

        while(var2.hasNext()) {
            String action = (String)var2.next();
            String message;
            if (action.startsWith("[command] ")) {
                message = action.substring(10).trim();
                if (message.contains("%player%")) {
                    message = message.replace("%player%", player.getName());
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), message);
            } else if (action.startsWith("[message] ")) {
                message = action.substring(10).trim();
                if (message.contains("%player%")) {
                    message = message.replace("%player%", player.getName());
                }

                player.sendMessage(this.colorize(message));
            }
        }

    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message).replace("§", "§").replace("&0", "§0").replace("&1", "§1").replace("&2", "§2").replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7").replace("&8", "§8").replace("&9", "§9").replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d").replace("&e", "§e").replace("&f", "§f").replace("&k", "§k").replace("&l", "§l").replace("&m", "§m").replace("&n", "§n").replace("&o", "§o").replace("&r", "§r");
    }

    public void onDisable() {
        this.getLogger().info("BlockLinks выключен! :(");
    }
}

