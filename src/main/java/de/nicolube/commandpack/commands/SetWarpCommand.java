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
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Single;
import co.aikar.commands.annotation.Syntax;
import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import de.nicolube.commandpack.server.Warp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author nicolube
 */
@CommandAlias("setwarp|swarp")
public class SetWarpCommand extends BaseCommand {

    private final Main plugin;

    public SetWarpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("commandpack.command.setwarp")
    @Syntax("<warp> <displayName>")
    public void onDefault(Player player, @Single String name, String displayName) {
        displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        Warp warp = this.plugin.getWarpManager().addWarp(name, displayName, player.getLocation());
        if (warp == null) {
            player.sendMessage(Prefixes.ADMIN + Msgs.WARP_ALLYEXIST.replace("{0}", name));
            return;
        }
        player.sendMessage(Prefixes.ADMIN + Msgs.WARP_SET.replace("{0}", warp.getName()).replace("{1}", warp.getDisplayName()));
    }
}
