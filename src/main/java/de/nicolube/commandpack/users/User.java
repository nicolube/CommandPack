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
import de.nicolube.commandpack.models.Model;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author nicolube
 */
@Entity("Users")
public class User extends Model {

    private String uuid;

    @Embedded
    private final Map<String, UserHome> homes;
    private boolean flying;
    private GameMode gamemode;

    private transient Main plugin;
    private transient Player player;

    public User() {
        this.homes = new HashMap<>();
        this.gamemode = Bukkit.getDefaultGameMode();
    }

    public User(Player player, Main plugin) {
        super(plugin.getLocalDatastorage());
        this.plugin = plugin;
        this.player = player;
        this.uuid = player.getUniqueId().toString();
        this.homes = new HashMap<>();
        this.flying = false;
        save();
    }

    public static User load(Player player, Main plugin) {

        User user = plugin.getLocalDatastorage().find(User.class).filter("uuid", player.getUniqueId().toString()).get();
        if (user == null) {
            return new User(player, plugin);
        }
        user.plugin = plugin;
        user.player = player;
        user.setDatastore(plugin.getLocalDatastorage());
        return user;
    }

    public void updateFlying() {
        this.player.setAllowFlight(this.flying);
        if (this.flying && !this.player.isOnGround()) {
            this.player.setFlying(true);
        }
    }

    public void updateGamemode() {
        player.setGameMode(gamemode);
    }

    public void flySafeLogin(Location loc) {
        if (gamemode.equals(GameMode.CREATIVE) || gamemode.equals(GameMode.SPECTATOR))
            return;
        if (player.hasPermission("commandpack.fly.safelogin")) {
            if (loc.getWorld().getHighestBlockYAt(loc) < loc.getY() - 4) {
                setFlying(true);
            }
        }
    }

    public UserHome removeHome(String name) {
        UserHome home = this.homes.remove(name.toLowerCase());
        if (home == null) {
            return null;
        }
        save();
        return home;
    }

    public UserHome addHome(String name, Location location) {
        if (!homes.containsKey(name.toLowerCase())) {
            UserHome home = new UserHome(name, location);
            this.homes.put(name.toLowerCase(), home);
            save();
            return home;
        }
        return null;
    }

    @Override
    public void save() {
        this.homes.forEach((n, h) -> h.preSave());
        super.save();
    }

    //Events
    protected void onJoin(PlayerJoinEvent event) {
        updateGamemode();
        flySafeLogin(event.getPlayer().getLocation());
        if (!player.isOnGround() && !player.getAllowFlight()) {
            Location loc = player.getLocation();
            loc.setY(loc.getWorld().getHighestBlockYAt(player.getLocation()) + 1);
            player.teleport(loc);
        }
    }

    protected void onQuit(PlayerQuitEvent event) {
        if (!player.hasPermission("commandpack.gamemode.save")) {
            Bukkit.getDefaultGameMode();
        }
        save();
    }

    protected void onTeleport(PlayerTeleportEvent event) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                updateFlying();
                flySafeLogin(event.getTo());
            }, 0);
    }

    void onGamemodeChange(PlayerGameModeChangeEvent event) {
        GameMode gamemode = event.getNewGameMode();
        if (gamemode.equals(GameMode.SURVIVAL) || gamemode.equals(GameMode.ADVENTURE)) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                updateFlying();
                flySafeLogin(player.getLocation());
            }, 0);
        }
    }

    //Getter Setter

    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
        updateGamemode();
        super.save();
    }
    
    public void setFlying(boolean flying) {
        this.flying = flying;
        updateFlying();
        super.save();
    }

    public boolean isFlying() {
        return flying;
    }

    public UserHome getHome(String name) {
        return this.homes.get(name.toLowerCase());
    }

    public Map<String, UserHome> getHomes() {
        return homes;
    }
}
