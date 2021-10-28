package spagetik.testplugin;

import org.bukkit.plugin.java.JavaPlugin;
import spagetik.testplugin.command.Verify;
import spagetik.testplugin.sql.VerifyDataBase;

public final class DisVerify extends JavaPlugin {

    public VerifyDataBase db = new VerifyDataBase(this.getConfig().getString("database.host"),
            this.getConfig().getString("database.port"),
            this.getConfig().getString("database.tablename"),
            this.getConfig().getString("database.username"),
            this.getConfig().getString("database.password"));

    private static DisVerify instance;

    public static DisVerify getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        new Verify();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
