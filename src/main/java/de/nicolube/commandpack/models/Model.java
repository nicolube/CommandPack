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
package de.nicolube.commandpack.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author nicolube
 */
public class Model {

    @Id
    private ObjectId _id;

    private transient Datastore datastore;

    public Model() {
    }

    public Model(Datastore datastore) {
        this.datastore = datastore;
    }

    public void save() {
        save(this.datastore);
    }

    public void save(Datastore datastore) {
        datastore.save(this);
    }

    public void delete(Class clazz) {
        delete(this.datastore, clazz);
    }

    public void delete(Datastore datastore, Class clazz) {
        datastore.delete(clazz, _id);
    }

    public <T extends Model> T setDatastore(Datastore datastore) {
        this.datastore = datastore;
        return (T) this;
    }

    public ObjectId getId() {
        return _id;
    }
}
