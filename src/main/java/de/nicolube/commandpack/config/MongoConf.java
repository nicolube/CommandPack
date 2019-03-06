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

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicolue.de
 */
public class MongoConf {
    private List<MongoAuth> auths;
    private List<ServerAddress> addresses;
    
    private String localDatabase;
    private String globalDatabase; 
    
    private transient MongoClient mongoClient;
    
    public MongoClient createMongoDB() {
        List<MongoCredential> credentials = new ArrayList<>();
        this.auths.forEach(a -> credentials.add(MongoCredential.createCredential(a.getUser(), a.getDatabase(), a.getPassword().toCharArray())));
        this.mongoClient = new MongoClient(addresses, credentials);
        return mongoClient;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public List<MongoAuth> getAuths() {
        return auths;
    }

    public List<ServerAddress> getAddresses() {
        return addresses;
    }

    public String getGlobalDatabase() {
        return globalDatabase;
    }

    public String getLocalDatabase() {
        return localDatabase;
    }
}
