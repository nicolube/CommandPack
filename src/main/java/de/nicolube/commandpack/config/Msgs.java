/*
 * Copyright (C) 2018 Owner
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package de.nicolube.commandpack.config;

import de.nicolube.commandpack.Util;

/**
 *
 * @author nicolube.de
 */
public class Msgs {

    public static String RENAME_NOITEM;
    public static String RENAME_RENAMED;
    
    public static String LORE_NOITEM;
    public static String LORE_ADD;
    
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

    public Msgs(Config config) {
        Util.setupMessageConfig(config, "messages", this);
    }
}
