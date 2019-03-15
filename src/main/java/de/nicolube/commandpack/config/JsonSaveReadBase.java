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

import com.google.gson.stream.JsonReader;
import de.nicolube.commandpack.Util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nicolue.de
 */
public class JsonSaveReadBase {

    protected transient File file;

    public JsonSaveReadBase(File file) {
        this.file = file;
    }
    
    protected static <T extends JsonSaveReadBase> T load(File file, Class clazz) {
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(file));
            T kit = Util.gson.fromJson(reader, clazz);
            kit.file = file;
            return kit;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonSaveReadBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void save() {
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(Util.gson.toJson(this));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonSaveReadBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JsonSaveReadBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void delete() {
        this.file.delete();
    }
}
