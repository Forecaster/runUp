package com.forecaster.runup;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class TickHandler
{
  int clock = 0;
  Float targetHeight = 0.5F;

  @SubscribeEvent
  public void onPlayerTick(TickEvent.PlayerTickEvent event)
  {
    Boolean blockMatch;
    EntityPlayer player = event.player;

    if (ConfigHandler.alwaysOn)
    {
      if (ConfigHandler.enabled)
      {
        if (player.stepHeight != 1F)
        {
          setStepHeight(player, 1F);
          System.out.println("Set stepheight to 1");
        }
      }
      System.out.println(player.stepHeight);
    }
    else
    {
      if (ConfigHandler.enabled)
      {
        if (clock % ConfigHandler.checkInterval == 0)
        {
          World world = player.getEntityWorld();

          int x = (int) Math.floor(player.posX);
          int y = (int) Math.floor(player.posY);
          int z = (int) Math.floor(player.posZ);

          Block block = world.getBlock(x, y - 1, z);

          if (block != null && block != Block.getBlockFromName("minecraft:air"))
          {
            //int id = Block.getIdFromBlock(block);

            //Block testBlock = Block.getBlockFromName("minecraft:stone");

            String[] blockList = ConfigHandler.blocklist;
            blockMatch = false;
            int i = 0;

            System.out.println("Block test started!");
            while (!blockMatch && i < blockList.length)
            {
              Block testBlock = Block.getBlockFromName(blockList[i]);

              if (block == testBlock)
              {
                blockMatch = true;
              }

              i++;
            }

            if (!ConfigHandler.blacklist)
            {
              if (blockMatch)
                targetHeight = 1F;
              else
                targetHeight = 0.5F;
            }
            else
            {
              if (!blockMatch)
                targetHeight = 1F;
              else
                targetHeight = 0.5F;
            }
          }
        }
        setStepHeight(player, targetHeight);
        clock++;
      }
    }
  }

  public boolean setStepHeight(EntityPlayer player, Float height)
  {
    if (player.stepHeight != height)
    {
      player.stepHeight = height;
      player.addChatComponentMessage(new ChatComponentText("[" + References.MODNAME + "]: StepHeight was set to " + height));
      return true;
    }
    else
      return false;
  }
}
