/* 
 * Copyright (C) 2019 nicolube
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
        
        @CommandPermission("commandpack.command.lore.remove")
        @Subcommand("remove")
        public void onRemove(Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null || item.getType().equals(Material.AIR)) {
                player.sendMessage(Prefixes.DEFAULT + Msgs.LORE_NOITEM);
                return;
            }
            ItemMeta meta = item.getItemMeta();
            List<String> loreData = meta.getLore();
            if (loreData == null) {
                loreData = new ArrayList<>();
            }
            loreData.clear();
            meta.setLore(loreData);
            item.setItemMeta(meta);
            player.sendMessage(Prefixes.DEFAULT + Msgs.LORE_REMOVE.replace("{0}", item.getType().name()));
        }
}
