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
package de.nicolube.commandpack;

import de.nicolube.commandpack.config.Msgs;
import de.nicolube.commandpack.config.Prefixes;
import co.aikar.commands.BukkitCommandManager;
import com.mongodb.MongoClient;
import de.nicolube.commandpack.commands.DelHomeCommand;
import de.nicolube.commandpack.commands.DelWarpCommand;
import de.nicolube.commandpack.commands.FlyCommand;
import de.nicolube.commandpack.commands.GamemodeCommand;
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
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
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
    private FileConfiguration messages;

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
        this.commandManager.addSupportedLanguage(Locale.ROOT);
        this.commandManager.getLocales().loadLanguage(messages, Locale.ROOT);
        this.commandManager.getLocales().setDefaultLocale(Locale.ROOT);
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
        getLogger().info("Add command: fly");
        this.commandManager.registerCommand(new FlyCommand(this));
        getLogger().info("Add command: gamemode");
        this.commandManager.registerCommand(new GamemodeCommand(this));

        getLogger().info("Setup warpmanager...");
        this.warpManager = new WarpManager(this);
        
        getLogger().info("Setup usermanager...");
        this.userManager = new UserManager(this);
        Bukkit.getPluginManager().registerEvents(this.userManager, this);
    }

    private void loadConfigs() {
        new Prefixes(this.cConfig);
        this.messages = new Msgs(this.cConfig).getConfig();
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

    public BukkitCommandManager getCommandManager() {
        return commandManager;
    }
}
