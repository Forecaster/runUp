package com.forecaster.runup;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraftforge.common.MinecraftForge;
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
    if (ConfigHandler.debug)
      Util.modLogOut(Level.INFO, "Debug mode enabled");
    if (ConfigHandler.enabled && ConfigHandler.debug)
      Util.modLogOut(Level.INFO, "Mod is enabled!");
    else
      Util.modLogOut(Level.INFO, "Mod is disabled!");
    if (ConfigHandler.alwaysOn && ConfigHandler.debug)
      Util.modLogOut(Level.INFO, "AlwaysOn mode is enabled! Whitelist/blacklist will be ignored. StepHeight will be maintained at 1.");
    if (ConfigHandler.blacklist && ConfigHandler.debug)
      Util.modLogOut(Level.INFO, "Blacklist mode enabled!");
    if (ConfigHandler.rotationMatters && ConfigHandler.debug)
      //TODO Change this message once rotation mode has been implemented
      Util.modLogOut(Level.INFO, "RotationMatters mode is enabled! However this has no effect at the moment!");
  }

  @Mod.EventHandler
  @SideOnly(Side.CLIENT)
  public void init(FMLInitializationEvent event)
  {
    FMLCommonHandler.instance().bus().register(new TickHandler());
    MinecraftForge.EVENT_BUS.register(new ConstructingHandler());
    //SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel("ConnectionHandler");
    //channel.
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event)
  {

  }
}
