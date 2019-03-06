/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nicolube.commandpack.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Single;
import co.aikar.commands.annotation.UnknownHandler;
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
    
    @UnknownHandler
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
