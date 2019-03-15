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
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

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
            User user = User.load(player, this.plugin);
            this.users.put(player.getUniqueId(), user);
            user.onJoin(event);
        } catch (Exception ex) {
            this.plugin.getLogger().log(Level.WARNING, "Can''t load Player {0} ({1})\n{2}", new Object[]{player.getName(), player.getUniqueId(), ex.getMessage()});
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.users.remove(player.getUniqueId()).onQuit(event);
    }
    
    @EventHandler
    private void onTeleport(PlayerTeleportEvent event) {
        this.users.get(event.getPlayer().getUniqueId()).onTeleport(event);
    }
    
    @EventHandler
    private void onGamemodeChange(PlayerGameModeChangeEvent event) {
        this.users.get(event.getPlayer().getUniqueId()).onGamemodeChange(event);
    }
    
}
