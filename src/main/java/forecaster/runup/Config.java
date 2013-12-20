package forecaster.runup;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import java.util.ArrayList;
import java.util.List;

public class Config
{
  public static List<Integer> blockIDs = new ArrayList<Integer>();
  public static Property blockIdsProperty;
  public static Property checksPerTick;
  public static Property ignoreList;
  public static Property invertList;
  public static Property debugOutput;
  public static Property debugBlockID;
  public static Property debugPosition;
  public static Property debugUpdate;
  public static Property debugMatch;
  public static Configuration config;

  public static void initConfiguration(FMLPreInitializationEvent event)
  {
    config = new Configuration(event.getSuggestedConfigurationFile());
    config.load();

    ignoreList = config.get("General", "Ignore Block List", false, "If true, stepping is always on. If false it changes depending on the blocks below. Default: false");
    invertList = config.get("General", "Invert Block List", false, "Turns the block list below from a whitelist to a blacklist. This means blocks in the list will DISABLE stepping, instead of enabling it. Stepping will be enabled on all other blocks. Default: false");

    blockIdsProperty = config.get("General", "Step Blocks", new int[] {2, 12, 13, 78, 79, 87, 88, 110, 121}, "Block ids of blocks that allow stepping:");
    for (int i : blockIdsProperty.getIntList()) blockIDs.add(i);

    checksPerTick = config.get("General", "Checks per tick", 2, "Default 2, Min 1, Max 5. 0 To disable functionality completely. A higher number means more precision when moving across blocks, but more work overall.");

    debugOutput = config.get("Debug", "Output", false, "Disable/enable all debug output (prints to chat). Default: false");
    debugBlockID = config.get("Debug", "BlockID", true, "Prints the detected block id in chat each cycle. Default: true");
    debugPosition = config.get("Debug", "Position", true, "Prints position of checked block in chat each cycle. Default: true");
    debugUpdate = config.get("Debug", "Update", true, "Prints changes in stepHeight value in chat. Default: true");
    debugMatch = config.get("Debug", "Matches", false, "Prints message on match/inverted match to console. Default: false");

    config.save();
  }

  public static boolean isIgnoreList()
  {
    return ignoreList.getBoolean(false);
  }

  public static boolean isInvertList()
  {
    return invertList.getBoolean(false);
  }

  public static int getChecksPerTick()
  {
    return checksPerTick.getInt();
  }

  public static List getBlockIds()
  {
    return blockIDs;
  }

  public static boolean isDebugOutput()
  {
    return debugOutput.getBoolean(false);
  }

  public static boolean isDebugBlockID()
  {
    return debugBlockID.getBoolean(true);
  }

  public static boolean isDebugPosition()
  {
    return debugPosition.getBoolean(true);
  }

  public static boolean isDebugUpdate()
  {
    return debugUpdate.getBoolean(true);
  }

  public static boolean isDebugMatch()
  {
    return debugMatch.getBoolean(false);
  }
}
