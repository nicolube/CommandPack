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
