package me.itzkiba.pluginMST;

//import com.comphenix.protocol.PacketType;
//import com.comphenix.protocol.ProtocolLibrary;
//import com.comphenix.protocol.ProtocolManager;
//import com.comphenix.protocol.events.ListenerPriority;
//import com.comphenix.protocol.events.PacketAdapter;
//import com.comphenix.protocol.events.PacketContainer;
//import com.comphenix.protocol.events.PacketEvent;
import me.itzkiba.pluginMST.commands.addability.AddAbilityCommand;
import me.itzkiba.pluginMST.commands.addability.AddAbilityTabCompleter;
import me.itzkiba.pluginMST.commands.createcustomitem.CreateCustomItemCommand;
import me.itzkiba.pluginMST.commands.createcustomitem.CreateCustomItemTabCompleter;
import me.itzkiba.pluginMST.commands.renameitem.RenameItemCommand;
import me.itzkiba.pluginMST.commands.selectclass.SelectClassCommand;
import me.itzkiba.pluginMST.commands.setitemstat.SetItemStatCommand;
import me.itzkiba.pluginMST.commands.setitemstat.SetItemStatTabCompleter;
import me.itzkiba.pluginMST.commands.setmana.SetPlayerManaCommand;
import me.itzkiba.pluginMST.commands.setplayerlevel.SetPlayerLevelCommand;
import me.itzkiba.pluginMST.commands.showitem.ShowItemCommand;
import me.itzkiba.pluginMST.commands.stats.StatsCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class PluginMST extends JavaPlugin {

    private static PluginMST plugin;
    private static Team uTeam;
    private static Team rTeam;
    private static Team eTeam;
    private static Team lTeam;
    private static Team mTeam;
    private static Scoreboard board;

    @Override
    public void onEnable() {
//        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
//        RemoveParticle(manager);
        // Plugin startup logic
        plugin = this;
        registerListeners();
        registerCommands();

        board = Bukkit.getScoreboardManager().getMainScoreboard();

        if (board.getTeam("uTeam") == null) {
            uTeam = board.registerNewTeam("uTeam");
            rTeam = board.registerNewTeam("rTeam");
            eTeam = board.registerNewTeam("eTeam");
            lTeam = board.registerNewTeam("lTeam");
            mTeam = board.registerNewTeam("mTeam");
        } else {
            uTeam = board.getTeam("uTeam");
            rTeam = board.getTeam("rTeam");
            eTeam = board.getTeam("eTeam");
            lTeam = board.getTeam("lTeam");
            mTeam = board.getTeam("mTeam");
        }

        uTeam.setColor(ChatColor.GREEN);

        rTeam.setColor(ChatColor.BLUE);

        eTeam.setColor(ChatColor.DARK_PURPLE);

        lTeam.setColor(ChatColor.GOLD);

        mTeam.setColor(ChatColor.DARK_RED);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if (uTeam != null) {
            uTeam.unregister();
            rTeam.unregister();
            eTeam.unregister();
            lTeam.unregister();
            mTeam.unregister();
        }
    }

    private void registerListeners()
    {
        String packageName = getClass().getPackage().getName();

        for (Class<?> c : new Reflections(packageName + ".listeners").getSubTypesOf(Listener.class))
        {
            try {
                Listener listener = (Listener) c.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerCommands()
    {
        getCommand("setitemstat").setExecutor(new SetItemStatCommand());
        getCommand("setitemstat").setTabCompleter(new SetItemStatTabCompleter());

        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("renameitem").setExecutor(new RenameItemCommand());
        getCommand("setlevel").setExecutor(new SetPlayerLevelCommand());

        getCommand("createcustomitem").setExecutor(new CreateCustomItemCommand());
        getCommand("createcustomitem").setTabCompleter(new CreateCustomItemTabCompleter());

        getCommand("addability").setExecutor(new AddAbilityCommand());
        getCommand("addability").setTabCompleter(new AddAbilityTabCompleter());

        getCommand("setmana").setExecutor(new SetPlayerManaCommand());

        getCommand("class").setExecutor(new SelectClassCommand());
        getCommand("showitem").setExecutor(new ShowItemCommand());
    }

    public static PluginMST getPlugin()
    {
        return plugin;
    }

    public static Team getuTeam() {
        return uTeam;
    }
    public static Team getrTeam() {
        return rTeam;
    }
    public static Team geteTeam() {
        return eTeam;
    }
    public static Team getlTeam() {
        return lTeam;
    }
    public static Team getmTeam() {
        return mTeam;
    }

//    public void RemoveParticle(ProtocolManager manager)
//    {
//        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.HIGH, PacketType.Play.Server.WORLD_PARTICLES) {
//            @Override
//            public void onPacketSending(PacketEvent event) {
//                PacketContainer packet = event.getPacket();
//                if (event.getPacketType() != PacketType.Play.Server.WORLD_PARTICLES)
//                    return;
//
//                if (packet.getNewParticles().read(0).getParticle() == Particle.DAMAGE_INDICATOR)
//                    event.setCancelled(true);
//            }
//        });
//    }
}
