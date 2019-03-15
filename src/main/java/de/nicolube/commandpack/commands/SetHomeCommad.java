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
