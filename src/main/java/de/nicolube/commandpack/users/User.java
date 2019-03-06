/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nicolube.commandpack.users;

import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.models.Model;
import de.nicolube.commandpack.server.Warp;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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

    private transient Main plugin;
    private transient Player player;

    public User() {
        this.homes = new HashMap<>();
    }

    public User(Player player, Main plugin) {
        super(plugin.getLocalDatastorage());
        this.plugin = plugin;
        this.player = player;
        this.uuid = player.getUniqueId().toString();
        this.homes = new HashMap<>();
        save();
    }

    public static User load(Player player, Main plugin) {
        Location loc = player.getLocation();
        loc.setY(loc.getWorld().getHighestBlockYAt(player.getLocation()) + 1);
        player.teleport(loc);
        User user = plugin.getLocalDatastorage().find(User.class).filter("uuid", player.getUniqueId().toString()).get();
        if (user == null) {
            return new User(player, plugin);
        }
        user.plugin = plugin;
        user.setDatastore(plugin.getLocalDatastorage());
        return user;
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

    public UserHome getHome(String name) {
        return this.homes.get(name.toLowerCase());
    }

    public Map<String, UserHome> getHomes() {
        return homes;
    }
}
