package com.forecaster.runup;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import org.apache.logging.log4j.Level;

public class Util
{
  public static void modLogOut(Level level, String message)
  {
    FMLLog.log(level, "[" + References.MODNAME + "] " + message);
  }

  public static void sendChatMessage(EntityPlayer player, String message)
  {
    player.addChatComponentMessage(new ChatComponentText("[" + References.MODNAME + "]: " + message));
  }
}
