package com.forecaster.runup;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Level;

/**
 * Created by Forecaster on 2014-09-18.
 */

@Mod(modid = "runup", name = "RunUp", version = "1.7.10-1.1")
public class RunUp
{
  public static String MODID = "runup";
  public static String MODNAME = "RunUp";

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
    FMLLog.log(level, "[" + RunUp.MODNAME + "] " + message, new Object[0]);
  }
}
