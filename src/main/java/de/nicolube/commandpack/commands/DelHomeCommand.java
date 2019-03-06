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
import de.nicolube.commandpack.users.UserHome;
import org.bukkit.entity.Player;

/**
 *
 * @author nicolube
 */
@CommandAlias("delhome|dhome")
public class DelHomeCommand extends BaseCommand {

    private final Main plugin;

    public DelHomeCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("commandpack.command.delhome")
    @Syntax("<home>")
    public void onDefault(Player player, String homeName) {
        UserHome home = this.plugin.getUserManager().getUser(player).removeHome(homeName);
        if (home == null) {
            player.sendMessage(Prefixes.ADMIN+Msgs.WARP_NOTFOUND.replace("{0}", homeName));
            return;
        }
        player.sendMessage(Prefixes.ADMIN+Msgs.WARP_DELETE.replace("{0}", home.getName()));
    }

}
