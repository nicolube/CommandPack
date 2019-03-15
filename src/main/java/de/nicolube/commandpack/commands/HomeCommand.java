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
    
    @Default
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
