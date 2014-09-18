package com.forecaster.runup;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Iterator;

/**
 * Created by Forecaster on 2014-09-18.
 */
@SideOnly(Side.CLIENT)
public class TickHandler
{
  int clock = 0;

  @SubscribeEvent
  public void onPlayerTick(TickEvent.PlayerTickEvent event)
  {
    Boolean blockMatch;
    EntityPlayer player = event.player;

    if (ConfigHandler.alwaysOn == true)
    {
      player.stepHeight = 1F;
    }
    else
    {
      if (clock % ConfigHandler.checkInterval == 0)
      {
        World world = player.getEntityWorld();

        int x = (int) Math.floor(player.posX);
        int y = (int) Math.floor(player.posY);
        int z = (int) Math.floor(player.posZ);

        Block block = world.getBlock(x, y - 1, z);

        //int id = Block.getIdFromBlock(block);

        //Block testblock = Block.getBlockFromName("minecraft:stone");

        String[] blocklist = ConfigHandler.blocklist;
        blockMatch = false;

        for(int i = 0; i < blocklist.length -1; i++)
        {
          String name = blocklist[i];
          Block testblock = Block.getBlockFromName(name);

          if (block == testblock)
          {
            blockMatch = true;
          }
        }

        /*if (ConfigHandler.blacklist == false)
        {
          if (blockMatch)
            player.stepHeight = 1F;
          else
            player.stepHeight = 0.5F;
        }
        else
        {
          if (!blockMatch)
            player.stepHeight = 1F;
          else
            player.stepHeight = 0.5F;
        }*/

        System.out.println(player.stepHeight);
      }

      clock++;
    }
  }
}
