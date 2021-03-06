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
