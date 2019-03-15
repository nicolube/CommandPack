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
