/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nicolube.commandpack.models;

import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.PostLoad;
import org.mongodb.morphia.annotations.PreSave;

/**
 *
 * @author nicolube
 */
public class LocationModel extends Model{
    
    private Map<String, Object> location;
    private transient Location nLocation;

    public LocationModel() {
    }

    public LocationModel(Location location) {
        this.nLocation = location;
    }

    public LocationModel(Location location, Datastore datastore) {
        super(datastore);
        this.nLocation = location;
    }
    
    
    
    public void teleport(Entity entity) {
        entity.teleport(this.nLocation);
    }
    
    public Location getnLocation() {
        return nLocation;
    }

    public void setnLocation(Location nLocation) {
        this.nLocation = nLocation;
    }

    @PreSave
    public void preSave() {
        this.location = this.nLocation.serialize();
    }

    @PostLoad
    public void postLoad() {
        this.nLocation = Location.deserialize(this.location);
    }
}