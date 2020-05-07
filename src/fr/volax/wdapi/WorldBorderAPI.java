package fr.volax.wdapi;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class WorldBorderAPI extends JavaPlugin{
    private static double borderSize;
    private static WorldBorder border;
    private static WorldBorderAPI instance;
    private static int task;
    private static int timer;

    @Override
    public void onEnable() {
        instance = this;
    }



    public static WorldBorderAPI worldBorderAction(World world, int timeToReduceBorderInSeconds, int sizeAtTheEndOfTimer) {
        if(!isValidWorld(world)) return null;

        timer = timeToReduceBorderInSeconds;
        borderSize = (getWorldBorderSize(world) - sizeAtTheEndOfTimer) / timeToReduceBorderInSeconds;
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), () -> {
                timer--;
                setWorldBorderSize(world, getWorldBorderSize(world) - borderSize);
                System.out.println( );

                if (timer == 0) {
                    Bukkit.getScheduler().cancelTask(task);
                }
        }, 0 ,20);
        return null;
    }

    public static WorldBorderAPI worldBorderInit(World world, int center1, int center2, double size) {
        borderSize = size;
        setWorldBorderCenter(world, center1, center2);
        setWorldBorderSize(world, borderSize);
        return null;
    }

    public static WorldBorderAPI setWorldBorderCenter(World world, int center1, int center2) {
        world.getWorldBorder().setCenter(center1, center2);
        return null;
    }

    public static Location getWorldBorderCenter(World world) {
        return world.getWorldBorder().getCenter();
    }

    public static WorldBorderAPI setWorldBorderSize(World world, double size) {
        borderSize = size;
        world.getWorldBorder().setSize(borderSize);
        return null;
    }

    public static double getWorldBorderSize(World world) {
        return world.getWorldBorder().getSize();
    }

    public static WorldBorder getWorldBorder(World world) {
        return world.getWorldBorder();
    }

    public static List<World> getWorlds(){
        return getInstance().getServer().getWorlds();
    }

    public static boolean isValidWorld(World world){
        return getWorlds().contains(world);
    }

    private static WorldBorderAPI getInstance() {
        return instance;
    }

}