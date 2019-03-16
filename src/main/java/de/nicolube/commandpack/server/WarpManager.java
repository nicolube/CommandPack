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
package de.nicolube.commandpack.server;

import de.nicolube.commandpack.Main;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 *
 * @author nicolube
 */
public class WarpManager {

    private final Map<String, Warp> warps;
    private final Main plugin;

    public WarpManager(Main plugin) {
        this.plugin = plugin;
        this.warps = new HashMap<>();
        load();
        plugin.getCommandManager().getCommandCompletions().registerAsyncCompletion("warps", (context) -> {
            return this.warps.keySet();
        });
    }

    public void load() {
        this.warps.clear();
        this.plugin.getLocalDatastorage().find(Warp.class).asList().forEach(w -> this.warps.put(w.getName().toLowerCase(), w.setDatastore(this.plugin.getLocalDatastorage())));
    }

    public Warp removeWarp(String name) {
        Warp warp = this.warps.remove(name.toLowerCase());
        if (warp == null) {
            return null;
        }
        warp.delete(Warp.class);
        return warp;
    }

    public Warp addWarp(String name, Location location) {
        return addWarp(name, name, location);
    }

    public Warp addWarp(String name, String displayName, Location location) {
        displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        if (!warps.containsKey(name.toLowerCase())) {
            Warp warp = new Warp(name, location, this.plugin.getLocalDatastorage());
            warp.setDisplayName(displayName);
            warp.save();
            this.warps.put(name.toLowerCase(), warp);
            return warp;
        }
        return null;
    }

    public Warp getWarp(String name) {
        return this.warps.get(name.toLowerCase());
    }

    public Map<String, Warp> getWarps() {
        return warps;
    }
}
