package com.forecaster.runup;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

@SideOnly(Side.CLIENT)
public class TickHandler
{
  int clock = 0;
  Float targetHeight = 0.5F;
  boolean enabledWarningSent = false;
  boolean worldIsRemoteWarningSent = false;
  static int worldIsRemoteTicks = 0;
  String state;

  @SubscribeEvent
  public void onPlayerTick(TickEvent.PlayerTickEvent event)
  {
    Boolean blockMatch;
    EntityPlayer player = event.player;


    if (ConfigHandler.alwaysOn || ConfigHandler.tempAlwaysOn) //AlwaysOn mode
    {
      if (ConfigHandler.enabled)
      {
        if (player.stepHeight != 1F)
        {
          setStepHeight(player, 1F);
          if (ConfigHandler.debug)
            System.out.println("Set stepheight to 1");
        }
      }
      else if (!enabledWarningSent)
      {
        Util.sendChatMessage(player, "The mod is disabled! It will do nothing until enabled in the config.");
        enabledWarningSent = true;
      }
    }
    else //Normal mode
    {
      if (clock % ConfigHandler.checkInterval == 0)
      {
        if (ConfigHandler.enabled)
        {
          World world = player.getEntityWorld();

          if (world.isRemote)
            state = "Remote";
          else
            state = "Local";

          if (world.isRemote)
          {
            if (worldIsRemoteTicks > 10)
            {
              if (!worldIsRemoteWarningSent)
              {
                Util.sendChatMessage(player, "The world is remote. This means you are likely playing on a server, which means I can't get the block you are standing on from the server at the moment. Sorry! Because of this I've enabled \"AlwaysOn\" mode for this session for you! This functionality will come soon!");
                ConfigHandler.tempAlwaysOn = true;
                worldIsRemoteWarningSent = true;
              }
            }
            else if (worldIsRemoteTicks != -1)
              worldIsRemoteTicks++;
          }

          if (!world.isRemote)
          {
            if (worldIsRemoteTicks != -1)
            {
              if (ConfigHandler.debug) System.out.println("Recieved local world in " + worldIsRemoteTicks + " ticks!");
              worldIsRemoteTicks = -1;
            }

            int x = (int) Math.floor(player.posX);
            int y = (int) Math.floor(player.posY);
            int z = (int) Math.floor(player.posZ);

            Block block = world.getBlock(x, y - 1, z);

            if (block == null)
              if (ConfigHandler.debug)
                System.out.println("Block is null");
            else
              if (ConfigHandler.debug)
                System.out.println(block.getLocalizedName());

            if (block != null && block != Block.getBlockFromName("minecraft:air"))
            {
              //int id = Block.getIdFromBlock(block);

              //Block testBlock = Block.getBlockFromName("minecraft:stone");

              String[] blockList = ConfigHandler.blocklist;
              blockMatch = false;
              int i = 0;

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
              } else
              {
                if (!blockMatch)
                  targetHeight = 1F;
                else
                  targetHeight = 0.5F;
              }
            }
          }
          if (world.isRemote)
            setStepHeight(player, targetHeight);
        }
        else if (!enabledWarningSent)
        {
          Util.sendChatMessage(player, "The mod is disabled! It will do nothing until enabled in the config.");
          enabledWarningSent = true;
        }
      }
      clock++;
    }
  }

  public boolean setStepHeight(EntityPlayer player, Float height)
  {
    if (player.stepHeight != height)
    {
      player.stepHeight = height;
      if (ConfigHandler.debug)
        Util.sendChatMessage(player, "[" + this.state + "] StepHeight was set to " + height);
      return true;
    }
    else
      return false;
  }
}
