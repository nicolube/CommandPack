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
package de.nicolube.commandpack.config;

import de.nicolube.commandpack.Util;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author nicolube.de
 */
public class Msgs {

    public static String RENAME_NOITEM;
    public static String RENAME_RENAMED;

    public static String LORE_NOITEM;
    public static String LORE_ADD;
    public static String LORE_REMOVE;

    public static String WARP_NOTFOUND;
    public static String WARP_TELEPORT;
    public static String WARP_DELETE;
    public static String WARP_SET;
    public static String WARP_ALLYEXIST;
    public static String WARP_RESET;
    public static String WARP_LIST;

    public static String HOME_NOTFOUND;
    public static String HOME_TELEPORT;
    public static String HOME_DELETE;
    public static String HOME_SET;
    public static String HOME_ALLYEXIST;
    public static String HOME_LIST;

    public static String SPEED_WALK;
    public static String SPEED_FLY;

    public static String FLY_ON;
    public static String FLY_OFF;
    public static String FLY_OTHER_ON;
    public static String FLY_OTHER_OFF;

    public static String GAMEMODE_SURVIVAL;
    public static String GAMEMODE_CREATIVE;
    public static String GAMEMODE_ADVENTURE;
    public static String GAMEMODE_SPECTATOR;
    public static String GAMEMODE_OTHER_SURVIVAL;
    public static String GAMEMODE_OTHER_CREATIVE;
    public static String GAMEMODE_OTHER_ADVENTURE;
    public static String GAMEMODE_OTHER_SPECTATOR;
    public static String GAMEMODE_UNKNOWN;

    private static transient FileConfiguration config;

    public Msgs(Config config) {
        this.config = Util.setupMessageConfig(config, "messages", this);
    }

    public static FileConfiguration getConfig() {
        return config;
    }
}
