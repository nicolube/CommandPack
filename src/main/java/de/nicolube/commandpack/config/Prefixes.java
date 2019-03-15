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

import com.mongodb.client.MongoDatabase;
import de.nicolube.commandpack.Util;

/**
 *
 * @author nicolube.de
 */
public class Prefixes {

    public static String DEFAULT;
    public static String ADMIN;

    public Prefixes(Config config) {
        Util.setupMessageConfig(config, "prefixes", this);
    }
}
