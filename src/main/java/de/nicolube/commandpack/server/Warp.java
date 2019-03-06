/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nicolube.commandpack.server;

import de.nicolube.commandpack.Main;
import de.nicolube.commandpack.models.LocationModel;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author nicolube
 */

@Entity("ServerWarps")
public class Warp extends LocationModel {
    private final String name;
    private String displayName;

    public Warp() {
        this.name = null;
    }
    
    public Warp(String name, Location location, Datastore datastore) {
        super(location, datastore);
        this.name = name;
        this.displayName = name;
    }
    
    public void teleport(Player player) {
        Main.getPlugin(Main.class).getLogger().log(Level.INFO, "{0} was warped to {1}", new Object[]{player.getName(), this.name});
        super.teleport(player);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void save() {
        super.preSave();
        super.save();
    }
    
    
}
