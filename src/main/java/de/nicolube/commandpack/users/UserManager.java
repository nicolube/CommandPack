/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nicolube.commandpack.users;

import de.nicolube.commandpack.Main;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author nicolube
 */
public class UserManager implements Listener {

    private final Map<UUID, User> users;
    private final Main plugin;

    public UserManager(Main plugin) {
        this.plugin = plugin;
        this.users = new HashMap<>();
        load();
    }

    public void load() {
        this.users.clear();
        Bukkit.getOnlinePlayers().forEach(player -> this.users.put(player.getUniqueId(), User.load(player, this.plugin)));
    }

    public User getUser(Player player) {
        return this.users.get(player.getUniqueId());
    }

    public Map<UUID, User> getUsers() {
        return users;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try {
            this.users.put(player.getUniqueId(), User.load(player, this.plugin));
        } catch (Exception ex) {
            this.plugin.getLogger().log(Level.WARNING, "Can''t load Player {0} ({1})\n{2}", new Object[]{player.getName(), player.getUniqueId(), ex.getMessage()});
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.users.remove(player.getUniqueId());
    }
}
