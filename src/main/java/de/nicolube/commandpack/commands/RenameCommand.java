/*
 * Copyright (C) 2018 nicolube.de
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package de.nicolube.commandpack.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.UnknownHandler;
import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author nicolue.de
 */
@CommandPermission("commandpack.command.rename")
public class RenameCommand extends BaseCommand {

    private final Main plugin;

    public RenameCommand(Main plugin) {
        super("rename");
        this.plugin = plugin;
    }

    @UnknownHandler
    public void onRename(Player player, String[] args) {
        StringBuilder nameBuilder = new StringBuilder();
        for (String arg : args) {
            nameBuilder.append(arg).append(" ");
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType().equals(Material.AIR)) {
            player.sendMessage(Prefixes.DEFAULT + Msgs.RENAME_NOITEM);
            return;
        }
        String name = ChatColor.translateAlternateColorCodes('&', nameBuilder.toString());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        player.sendMessage(Prefixes.DEFAULT + Msgs.RENAME_RENAMED.replace("{0}", item.getType().name()).replace("{1}", name));
    }

}
