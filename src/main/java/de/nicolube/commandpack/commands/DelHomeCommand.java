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
