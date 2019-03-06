/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
