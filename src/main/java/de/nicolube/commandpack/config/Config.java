/*
 * Copyright (C) 2018 nicolue.de
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

import de.nicolube.commandpack.config.MongoConf;
import java.io.File;

/**
 *
 * @author nicolue.de
 */
public class Config extends JsonSaveReadBase {
    
    private MongoConf mongoDB;
    private String messagesTag;
    
    public Config(File file) {
        super(file);
    }
    
    public static Config load(File file) {
        return JsonSaveReadBase.load(file,Config.class);
    }
    
    public MongoConf getMongoDB() {
        return mongoDB;
    }

    public String getMessagesTag() {
        return messagesTag;
    }
    
    
}
