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
import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import de.nicolube.commandpack.server.Warp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author nicolube
 */
@CommandPermission("commandpack.command.warp")
@CommandAlias("warp|warps")
public class WarpCommand extends BaseCommand {
    
    private final Main plugin;
    
    public WarpCommand(Main plugin) {
        this.plugin = plugin;
    }
    
    @Default
    @CommandPermission("commandpack.command.warp.list")
    public void onDefault(CommandSender sender) {
        StringBuilder list = new StringBuilder("§aname §8- §adisplayName");
        this.plugin.getWarpManager().getWarps().forEach((n, w) -> {
            list.append("\n§7").append(w.getName()).append(" §8- §7").append(w.getDisplayName());
        });
        sender.sendMessage(Prefixes.DEFAULT + Msgs.WARP_LIST.replace("{0}", list));
    }
    
    @Default
    public void onWarp(Player player, @Single String warpName) {
        Warp warp = this.plugin.getWarpManager().getWarp(warpName);
        if (warp == null) {
            player.sendMessage(Prefixes.DEFAULT + Msgs.WARP_NOTFOUND.replace("{0}", warpName));
            return;
        }
        player.sendMessage(Prefixes.DEFAULT + Msgs.WARP_TELEPORT.replace("{0}", warp.getDisplayName()));
        warp.teleport(player);
    }
}
