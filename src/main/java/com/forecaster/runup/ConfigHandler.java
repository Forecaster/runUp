package com.forecaster.runup;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

public class ConfigHandler
{
  static boolean enabled = true;
  static int checkInterval = 2;
  static boolean alwaysOn = false;
  static boolean rotationMatters = true;
  static boolean blacklist = false;
  static String[] blocklist = new String[] {};

  public static void init(File configFile)
  {

    Configuration configuration = new Configuration(configFile);

    try
    {
      configuration.load();

      enabled = configuration.get("Enabled", "enabled", true, "Set to false to disable mod without uninstalling. Default: true").getBoolean(true);
      checkInterval = configuration.get(Configuration.CATEGORY_GENERAL, "checkInterval", 10, "Check every x tick. A value of 1 equals a check every tick (May impact performance). Default: 10").getInt();
      alwaysOn = configuration.get(Configuration.CATEGORY_GENERAL, "alwaysOn", false, "Ignore the list below. Ensure that step height is always 1. Default: false").getBoolean();
      rotationMatters = configuration.get(Configuration.CATEGORY_GENERAL, "rotationMatters", true, "Enables also checking the block in front of you, allowing for improved control. Default: true").getBoolean();
      blacklist = configuration.get(Configuration.CATEGORY_GENERAL, "blacklist", false, "Turn the whitelist below into a blacklist. Default: false").getBoolean();
      blocklist = configuration.get(Configuration.CATEGORY_GENERAL, "blocklist", new String[]{"minecraft:stone", "minecraft:grass", "minecraft:sand", "minecraft:gravel", "minecraft:snow_layer", "minecraft:ice", "minecraft:snow", "minecraft:netherrack", "minecraft:soul_sand", "minecraft:mycelium", "minecraft:end_stone"}, "The blocks that will cause your step height to be set by default, or which will not if blacklist is true. Default: minecraft:stone, minecraft:grass, minecraft:sand, minecraft:gravel, minecraft:snow_layer, minecraft:ice, minecraft:snow, minecraft:netherrack, minecraft:soul_sand, minecraft:mycelium, minecraft:end_stone").getStringList();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      configuration.save();
    }

    RunUp.modLogOut(Level.INFO, "Config loaded!");
  }
}
