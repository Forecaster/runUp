package com.forecaster.runup;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;

@SideOnly(Side.CLIENT)
public class ConstructingHandler
{
  @SubscribeEvent
  public void onEntityConstructing(EntityEvent.EntityConstructing event)
  {
    if (event.entity instanceof EntityPlayer)
    {
      TickHandler.worldIsRemoteTicks = 0;
      ConfigHandler.tempAlwaysOn = false;
    }
  }
}
