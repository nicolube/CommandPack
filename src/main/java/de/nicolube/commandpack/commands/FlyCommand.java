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
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import de.nicolube.commandpack.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author nicolube
 */
@CommandAlias("fly|cfly")
@CommandPermission("commandpack.command.fly")
public class FlyCommand extends BaseCommand {

    private final Main plugin;

    public FlyCommand(Main plugin) {
        this.plugin = plugin;
    }
        
    @Default
    public boolean onFlyCommand(Player player) {
        User user = this.plugin.getUserManager().getUser(player);
        boolean state = !user.isFlying();
        user.setFlying(state);
        if (state) {
            player.sendMessage(Prefixes.DEFAULT+Msgs.FLY_ON);
        } else {
            player.sendMessage(Prefixes.DEFAULT+Msgs.FLY_OFF);
        }
        return state;
    }

    @Default
    @CommandCompletion("@players")
    public void onFlyCommand(CommandSender sender, OnlinePlayer player) {
        if (onFlyCommand(player.getPlayer())) {
            sender.sendMessage(Prefixes.DEFAULT+Msgs.FLY_OTHER_ON.replace("{0}", player.getPlayer().getName()));
        } else {
            sender.sendMessage(Prefixes.DEFAULT+Msgs.FLY_OTHER_OFF.replace("{0}", player.getPlayer().getName()));
        }
    }
}
