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
import co.aikar.commands.annotation.Syntax;
import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import de.nicolube.commandpack.server.Warp;
import org.bukkit.entity.Player;

/**
 *
 * @author nicolube
 */
@CommandAlias("delhome|dhome")
public class DelWarpCommand extends BaseCommand {

    private final Main plugin;

    public DelWarpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("commandpack.command.delhome")
    @Syntax("<home>")
    public void onDefault(Player player, String string) {
        Warp home = this.plugin.getWarpManager().removeWarp(string);
        if (home == null) {
            player.sendMessage(Prefixes.DEFAULT+Msgs.HOME_NOTFOUND.replace("{0}", string));
            return;
        }
        player.sendMessage(Prefixes.DEFAULT+Msgs.HOME_DELETE.replace("{0}", home.getName()));
    }

}
