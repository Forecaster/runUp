package forecaster.runup;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import forecaster.runup.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Iterator;

public class tickHandler implements ITickHandler {
  int i = 0;
  int a = 0;
  private boolean foundMatch;

  @Override
  public void tickStart(EnumSet<TickType> tickTypes, Object... objects) {
    //nothing here
  }

  @Override
  public void tickEnd(EnumSet<TickType> tickTypes, Object... objects) {
    EntityPlayer player = (EntityPlayer) objects[0];

    int perTick = 20 / Config.getChecksPerTick();

    if (i == perTick) {

      World world = player.getEntityWorld();

      int x = (int) Math.floor(player.posX);
      int y = (int) Math.floor(player.posY);
      int z = (int) Math.floor(player.posZ);

      y -= 1;

      if (Config.isDebugOutput() && Config.isDebugPosition()) {
        player.addChatMessage("["+Reference.MOD_NAME+"]Position x.y.z: " + x + "." + y + "." + z);
      }

      int ID = world.getBlockId(x, (y - 1), z);

      if (Config.isDebugOutput() && Config.isDebugBlockID()) {
        player.addChatMessage("["+Reference.MOD_NAME+"]ID: " + ID);
      }

      if (Config.isDebugOutput()) {System.out.println("["+Reference.MOD_NAME+"]isInvertList is currently " + Config.isInvertList()); }

      if (!Config.isInvertList())
      {
        foundMatch = false;
      }
      else
      {
        foundMatch = true;
      }

      if (Config.isDebugOutput()) {System.out.println("["+Reference.MOD_NAME+"]FoundMatch is currently " + foundMatch); }

      if (!Config.isIgnoreList() && ID != 0)
      {
        Iterator<Integer> iterator = Config.getBlockIds().iterator();
        while (iterator.hasNext()) {
          if (!Config.isInvertList())
          {
            if (ID == iterator.next())
            {
              foundMatch = true;
              if (Config.isDebugOutput() && Config.isDebugMatch()) {System.out.println("["+Reference.MOD_NAME+"]Match! foundMatch is " + foundMatch); }
            }
          }
          else
          {
            if (ID == iterator.next())
            {
              foundMatch = false;
              if (Config.isDebugOutput() && Config.isDebugMatch()) {System.out.println("["+Reference.MOD_NAME+"]Inverted match! foundMatch is " + foundMatch); }
            }
          }
        }
      }
      else
      {
        foundMatch = true;
        if (Config.isIgnoreList() && Config.isDebugOutput()) {System.out.println("["+Reference.MOD_NAME+"]Ignored list! foundMatch is " + foundMatch); }
        if (ID == 0 && Config.isDebugOutput()) {System.out.println("["+Reference.MOD_NAME+"]Ignored air block!"); }
      }

      if (foundMatch && player.stepHeight == 0.5F)
      {
        player.stepHeight = 1F;
        if (Config.isDebugOutput() && Config.isDebugUpdate())
        {
          player.addChatMessage("["+Reference.MOD_NAME+"]Detected block " + ID + "! Set stepHeight to " + String.valueOf(player.stepHeight));
        }
      }
      else if (!foundMatch && player.stepHeight == 1.0F)
      {
        player.stepHeight = 0.5F;
        if (Config.isDebugOutput() && Config.isDebugUpdate())
        {
          player.addChatMessage("["+Reference.MOD_NAME+"]Detected block " + ID + "! Set stepHeight to " + String.valueOf(player.stepHeight));
        }
      }
      i = 0;
    }
    else
    { i++; }
  }

  @Override
  public EnumSet<TickType> ticks()
  {
    return EnumSet.of(TickType.PLAYER);
  }

  @Override
  public String getLabel()
  {
    return "StepUp - StepHeight Updater";
  }
}
