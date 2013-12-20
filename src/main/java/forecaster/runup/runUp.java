package forecaster.runup;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import forecaster.runup.lib.Reference;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptedMinecraftVersions = "1.6.x")
@NetworkMod(clientSideRequired = true)
public class runUp {

  @EventHandler
  public void PreInit(FMLPreInitializationEvent event) {
    //System.out.println("PreInit");
    Config.initConfiguration(event);
  }

  @EventHandler
  public void Init(FMLInitializationEvent event) {
    //System.out.println("Init");
    TickRegistry.registerTickHandler(new tickHandler(), Side.CLIENT);
  }

  @EventHandler
  public void PostInit(FMLPostInitializationEvent event) {
    //System.out.println("PostInit");
  }

}
