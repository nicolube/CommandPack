/*
 * Copyright (C) 2018 nicolube.de
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
package de.nicolube.commandpack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import de.nicolube.commandpack.config.Config;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author nicolube.de
 */
public class Util {

    public static Gson gson;

    public static void copyOutOfJar(String path, File out) {
        if (out.exists()) {
            return;
        }
        out.getParentFile().mkdirs();
        try {
            int read;
            URL src = Util.class.getResource(path);
            InputStream in;
            try (FileOutputStream os = new FileOutputStream(out)) {
                URLConnection connection = src.openConnection();
                connection.setUseCaches(false);
                in = connection.getInputStream();
                while ((read = in.read()) != -1) {
                    os.write(read);
                }
                os.flush();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setupMessageConfig(Config serverConfig, String name, Object o) {
        MongoDatabase db = serverConfig.getMongoDB().getMongoClient().getDatabase(serverConfig.getMongoDB().getGlobalDatabase());
        String messagesTag = serverConfig.getMessagesTag();
        Bson filter = Filters.and(Filters.eq("fileName", name), Filters.eq("tag", messagesTag));
        YamlConfiguration defaultConfig;
        try {
            defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Util.class.getResourceAsStream("/configs/" + name + ".yml"), "UTF8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        MongoCollection<Document> col;
        try {
            col = db.getCollection("CommandPack");
        } catch (IllegalArgumentException ex) {
            db.createCollection("CommandPack");
            col = db.getCollection("CommandPack");
        }
        Document config = col.find(filter).first();
        if (config == null) {
            config = new Document();
            config.append("fileName", name);
            config.append("tag", messagesTag);
            col.insertOne(config);
        }
        for (Field field : o.getClass().getFields()) {
            try {
                if (!Modifier.isTransient(field.getModifiers())) {
                    String defaultValue = defaultConfig.getString(field.getName().toLowerCase().replace("_", "."));
                    String value = config.getString(field.getName().toLowerCase());
                    if (value == null) {
                        if (defaultValue == null) {
                            System.out.println(name + " string not found not found " + field.getName().toLowerCase().replace("_", "."));
                            field.set(null, "§cMessage not found please report this to an Admin.");
                            continue;
                        }
                        config.append(field.getName().toLowerCase(), defaultValue);
                        field.set(null, defaultValue);
                        col.replaceOne(filter, config);
                        continue;
                    }
                    field.set(null, value);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(o.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static {
        Util.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
}
