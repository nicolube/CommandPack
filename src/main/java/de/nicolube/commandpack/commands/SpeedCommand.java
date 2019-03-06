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
import co.aikar.commands.annotation.Values;
import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import org.bukkit.entity.Player;

/**
 *
 * @author nicolube
 */
@CommandAlias("speed")
@CommandPermission("commandpack.command.speed")
public class SpeedCommand extends BaseCommand {

    private final Main plugin;

    public SpeedCommand(Main plugin) {
        this.plugin = plugin;
    }
    
    @Default
    @Syntax("<speed>")
    public void onSpeed(Player player, @Values("@range:0-10") int speed) {
        if (player.isFlying()) {
            player.setFlySpeed((float) speed / 10);
            player.sendMessage(Prefixes.DEFAULT+Msgs.SPEED_FLY.replace("{0}", String.valueOf(speed)));
        } else {
            player.setWalkSpeed((float) speed / 10);
            player.sendMessage(Prefixes.DEFAULT+Msgs.SPEED_WALK.replace("{0}", String.valueOf(speed)));
        }
    }
}
