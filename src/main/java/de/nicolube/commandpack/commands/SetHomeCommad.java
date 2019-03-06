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
import de.nicolube.commandpack.users.UserHome;
import org.bukkit.entity.Player;

/**
 *
 * @author nicolube
 */
@CommandAlias("sethome|shome")
public class SetHomeCommad extends BaseCommand {

    private final Main plugin;

    public SetHomeCommad(Main plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("commandpack.command.sethome")
    @Syntax("<home>")
    public void onDefault(Player player, @Single String name) {
        UserHome home = this.plugin.getUserManager().getUser(player).addHome(name, player.getLocation());
        if (home == null) {
            player.sendMessage(Prefixes.DEFAULT + Msgs.HOME_ALLYEXIST.replace("{0}", name));
            return;
        }
        player.sendMessage(Prefixes.DEFAULT + Msgs.HOME_SET.replace("{0}", home.getName()));
    }
}
