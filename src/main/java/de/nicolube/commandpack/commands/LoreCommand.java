/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nicolube.commandpack.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author nicolube
 */
@CommandPermission("commandpack.command.lore")
public class LoreCommand extends BaseCommand {

    public LoreCommand() {
        super("lore");
    }

    @Subcommand("add")
    public void onAdd(Player player, String lore) {
        lore = ChatColor.translateAlternateColorCodes('&', lore);
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType().equals(Material.AIR)) {
            player.sendMessage(Prefixes.DEFAULT + Msgs.LORE_NOITEM);
            return;
        }
        ItemMeta meta = item.getItemMeta();
        List<String> loreData = meta.getLore();
        if (loreData == null) loreData = new ArrayList<>();
        loreData.add(lore);
        meta.setLore(loreData);
        item.setItemMeta(meta);
        player.sendMessage(Prefixes.DEFAULT + Msgs.LORE_ADD.replace("{0}", item.getType().name()).replace("{1}", lore));
        
    }
}
