package com.forecaster.runup;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

@Mod(modid = References.MODID, name = References.MODNAME, version = References.VERSION)
public class RunUp
{

  @Mod.Instance("runup")
  public static RunUp instance;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    ConfigHandler.init(event.getSuggestedConfigurationFile());
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event)
  {
    FMLCommonHandler.instance().bus().register(new TickHandler());
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event)
  {

  }

  public static void modLogOut(Level level, String message)
  {
    FMLLog.log(level, "[" + References.MODNAME + "] " + message);
  }
}
