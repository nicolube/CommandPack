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
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Private;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import java.util.Arrays;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author nicolube
 */
@CommandAlias("gamemode|gm")
@CommandPermission("commandpack.command.gamemode")
public class GamemodeCommand extends BaseCommand {
    
    private final Main plugin;
    
    public GamemodeCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommandManager().getCommandCompletions().registerStaticCompletion("gamemodes", Arrays.asList("survival", "creative", "adventure", "spectator"));
    }
    
    @Default
    @CommandCompletion("@gamemodes")
    public void onDefault(Player player, String gamemode) {
        gamemode = gamemode.toLowerCase();
        switch (gamemode) {
            case "0":
            case "s":
            case "survival":
                if (hasPermission(player, gamemode)) {
                    onSurvival(player);
                }
                break;
            case "1":
            case "c":
            case "creative":
                if (hasPermission(player, gamemode)) {
                    onCreateive(player);
                }
                break;
            case "2":
            case "a":
            case "adventure":
                if (hasPermission(player, gamemode)) {
                    onAdventure(player);
                }
                break;
            case "3":
            case "sp":
            case "spectator":
                if (hasPermission(player, gamemode)) {
                    onSpectator(player);
                }
                break;
            default:
                player.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_UNKNOWN);
        }
    }
    
    @Default
    @CommandCompletion("@gamemodes  @players")
    public void onDefault(CommandSender sender, String gamemode, OnlinePlayer onlinePlayer) {
        Player player = onlinePlayer.getPlayer();
        gamemode = gamemode.toLowerCase();
        switch (gamemode) {
            case "0":
            case "s":
            case "survival":
                if (hasPermissionOther(sender, gamemode)) {
                    onSurvival(sender, onlinePlayer);
                }
                break;
            case "1":
            case "c":
            case "creative":
                if (hasPermissionOther(sender, gamemode)) {
                    onCreateive(sender, onlinePlayer);
                }
                break;
            case "2":
            case "a":
            case "adventure":
                if (hasPermissionOther(sender, gamemode)) {
                    onAdventure(player, onlinePlayer);
                }
                break;
            case "3":
            case "sp":
            case "spectator":
                if (hasPermissionOther(sender, gamemode)) {
                    onSpectator(player, onlinePlayer);
                }
                break;
            default:
                sender.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_UNKNOWN);
        }
    }
    
    @Private
    @CommandAlias("gms|gm0")
    @CommandPermission("commandpack.command.gamemode.survival")
    public void onSurvival(Player player) {
        this.plugin.getUserManager().getUser(player).setGamemode(GameMode.SURVIVAL);
        player.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_SURVIVAL);
    }
    
    @Private
    @CommandAlias("gms|gm0")
    @CommandCompletion("@players")
    @CommandPermission("commandpack.command.gamemode.other.survival")
    public void onSurvival(CommandSender sender, OnlinePlayer player) {
        onCreateive(player.getPlayer());
        sender.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_OTHER_SURVIVAL.replace("{0}", player.getPlayer().getName()));
    }
    
    @Private
    @CommandAlias("gmc|gm1")
    @CommandPermission("commandpack.command.gamemode.creative")
    public void onCreateive(Player player) {
        this.plugin.getUserManager().getUser(player).setGamemode(GameMode.CREATIVE);
        player.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_CREATIVE);
    }
    
    @Private
    @CommandAlias("gmc|gm1")
    @CommandCompletion("@players")
    @CommandPermission("commandpack.command.gamemode.other.creative")
    public void onCreateive(CommandSender sender, OnlinePlayer player) {
        onCreateive(player.getPlayer());
        sender.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_OTHER_CREATIVE.replace("{0}", player.getPlayer().getName()));
    }
    
    @Private
    @CommandAlias("gma|gm2")
    @CommandPermission("commandpack.command.gamemode.creative")
    public void onAdventure(Player player) {
        this.plugin.getUserManager().getUser(player).setGamemode(GameMode.ADVENTURE);
        player.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_ADVENTURE);
    }
    
    @Private
    @CommandAlias("gma|gm2")
    @CommandCompletion("@players")
    @CommandPermission("commandpack.command.gamemode.other.creative")
    public void onAdventure(CommandSender sender, OnlinePlayer player) {
        onCreateive(player.getPlayer());;
        sender.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_OTHER_ADVENTURE.replace("{0}", player.getPlayer().getName()));
    }
    
    @Private
    @CommandAlias("gmsp|gm3")
    @CommandPermission("commandpack.command.gamemode.creative")
    public void onSpectator(Player player) {
        this.plugin.getUserManager().getUser(player).setGamemode(GameMode.ADVENTURE);
        player.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_SPECTATOR);
    }
    
    @Private
    @CommandAlias("gmsp|gm3")
    @CommandCompletion("@players")
    @CommandPermission("commandpack.command.gamemode.other.creative")
    public void onSpectator(CommandSender sender, OnlinePlayer player) {
        onCreateive(player.getPlayer());;
        sender.sendMessage(Prefixes.DEFAULT + Msgs.GAMEMODE_OTHER_SPECTATOR.replace("{0}", player.getPlayer().getName()));
    }
    
    private boolean hasPermission(Player player, String permExt) {
        if (player.hasPermission("commandpack.command.gamemode." + permExt)) {
            return true;
        }
        player.sendMessage(Msgs.getConfig().getString("acf-core.permission_denied"));
        return false;
    }
    
    private boolean hasPermissionOther(CommandSender player, String permExt) {
        if (player.hasPermission("commandpack.command.gamemode.other." + permExt)) {
            return true;
        }
        player.sendMessage(Msgs.getConfig().getString("acf-core.permission_denied"));
        return false;
    }
}
