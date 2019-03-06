/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nicolube.commandpack;

import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import co.aikar.commands.BukkitCommandManager;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import de.nicolube.commandpack.commands.DelHomeCommand;
import de.nicolube.commandpack.commands.DelWarpCommand;
import de.nicolube.commandpack.commands.HomeCommand;
import de.nicolube.commandpack.commands.LoreCommand;
import de.nicolube.commandpack.commands.RenameCommand;
import de.nicolube.commandpack.commands.SetHomeCommad;
import de.nicolube.commandpack.commands.SetWarpCommand;
import de.nicolube.commandpack.commands.SpeedCommand;
import de.nicolube.commandpack.commands.WarpCommand;
import de.nicolube.commandpack.config.Config;
import de.nicolube.commandpack.server.WarpManager;
import de.nicolube.commandpack.users.UserManager;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.DefaultCreator;

/**
 *
 * @author nicolue.de
 */
public class Main extends JavaPlugin {

    private BukkitCommandManager commandManager;
    private Config cConfig;
    private MongoClient mongoClient;
    private Datastore localDatastorage;
    private Datastore globalDatastorage;
    private WarpManager warpManager;
    private UserManager userManager;

    @Override
    public void onEnable() {
        Util.copyOutOfJar("/configs/config.json", new File(getDataFolder(), "config.json"));
        getLogger().info("Loading config.json...");
        this.cConfig = Config.load(new File(getDataFolder(), "config.json"));
         getLogger().info("Setup database..");
        this.mongoClient = this.cConfig.getMongoDB().createMongoDB();
        getLogger().info("Setup Morthia...");
        Morphia morphia = new Morphia(); 
        morphia.getMapper().getOptions().setObjectFactory(new DefaultCreator() {
            @Override
            protected ClassLoader getClassLoaderForClass() {
                return Main.class.getClassLoader();
            }
        });
        this.localDatastorage = morphia.createDatastore(mongoClient, this.cConfig.getMongoDB().getLocalDatabase());
        this.globalDatastorage = morphia.createDatastore(mongoClient, this.cConfig.getMongoDB().getGlobalDatabase());

        getLogger().info("Loading messages and prefixes...");
        loadConfigs();
        
        getLogger().info("Setup commandmanager...");
        this.commandManager = new BukkitCommandManager(this);
        getLogger().info("Add command: rename");
        this.commandManager.registerCommand(new RenameCommand(this));;
        getLogger().info("Add command: lore");
        this.commandManager.registerCommand(new LoreCommand());
        getLogger().info("Add command: warp");
        this.commandManager.registerCommand(new WarpCommand(this));
        getLogger().info("Add command: setwarp");
        this.commandManager.registerCommand(new SetWarpCommand(this));
        getLogger().info("Add command: delwarp");
        this.commandManager.registerCommand(new DelWarpCommand(this));
        getLogger().info("Add command: home");
        this.commandManager.registerCommand(new HomeCommand(this));
        getLogger().info("Add command: sethome");
        this.commandManager.registerCommand(new SetHomeCommad(this));
        getLogger().info("Add command: delhome");
        this.commandManager.registerCommand(new DelHomeCommand(this));
        getLogger().info("Add command: speed");
        this.commandManager.registerCommand(new SpeedCommand(this));

        getLogger().info("Setup warpmanager...");
        this.warpManager = new WarpManager(this);
        
        getLogger().info("Setup usermanager...");
        this.userManager = new UserManager(this);
    }

    private void loadConfigs() {
        MongoDatabase db = mongoClient.getDatabase(this.cConfig.getMongoDB().getGlobalDatabase());
        new Prefixes(this.cConfig);
        new Msgs(this.cConfig);
    }

    public Datastore getLocalDatastorage() {
        return localDatastorage;
    }

    public Datastore getGlobalDatastorage() {
        return globalDatastorage;
    }
    public WarpManager getWarpManager() {
        return warpManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
