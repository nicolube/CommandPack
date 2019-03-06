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
import co.aikar.commands.annotation.UnknownHandler;
import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import de.nicolube.commandpack.users.UserHome;
import org.bukkit.entity.Player;

/**
 *
 * @author nicolube
 */
@CommandPermission("commandpack.command.home")
@CommandAlias("home|homes")
public class HomeCommand extends BaseCommand {
    
    private final Main plugin;
    
    public HomeCommand(Main plugin) {
        this.plugin = plugin;
    }
    
    @Default
    public void onDefault(Player player) {
        StringBuilder list = new StringBuilder("§aname §8- §adisplayName\n");
        this.plugin.getUserManager().getUser(player).getHomes().forEach((k, n) -> {
            list.append(n.getName()).append(", ");
        });
        player.sendMessage(Prefixes.DEFAULT + Msgs.HOME_LIST.replace("{0}", list));
    }
    
    @UnknownHandler
    @Syntax("<home>")
    public void onHome(Player player, @Single String homeName) {
        UserHome home = this.plugin.getUserManager().getUser(player).getHome(homeName);
        if (home == null) {
            player.sendMessage(Prefixes.DEFAULT + Msgs.HOME_NOTFOUND.replace("{0}", homeName));
            return;
        }
        player.sendMessage(Prefixes.DEFAULT + Msgs.HOME_TELEPORT.replace("{0}", home.getName()));
        home.teleport(player);
    }
}
