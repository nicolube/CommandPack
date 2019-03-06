/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nicolube.commandpack.users;

import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.models.LocationModel;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author nicolube
 */

@Entity("UserHome")
public class UserHome extends LocationModel {
    private final String name;

    public UserHome() {
        this.name = null;
    }
    
    public UserHome(String name, Location location) {
        super(location);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void teleport(Player player) {
        Main.getPlugin(Main.class).getLogger().log(Level.INFO, "{0} has been teleported to his home: {1}", new Object[]{player.getName(), this.name});
        super.teleport(player);
    }
    
    
}
