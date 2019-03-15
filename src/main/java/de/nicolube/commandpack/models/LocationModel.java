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
package de.nicolube.commandpack.models;

import de.nicolube.commandpack.Main;
import java.util.Map;
import java.util.logging.Level;
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
        try {
            this.nLocation = Location.deserialize(this.location);
        } catch (IllegalArgumentException ex) {
            Main.getPlugin(Main.class).getLogger().log(Level.WARNING, "World not found: {0}", location.get("world"));
        }
    }
}