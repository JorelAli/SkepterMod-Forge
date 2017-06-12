package io.github.skepter.skeptermod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION, guiFactory = "io.github.skepter.skeptermod.ModGuiFactory")
public class Main {
	
	public static final String MODID = "skeptermod";
	public static final String VERSION = "1.0";
	
	//Config
	public static Configuration configFile;
	public static boolean rainbowchatEnabled = true;
	public static String colors = "";
	public static boolean loopColor = true;
	public static boolean tpCommandEnabled = true;
	public static boolean topCommandEnabled = true;
	public static boolean compassEnabled = true;
	public static boolean nameHighlightEnabled = true;
	public static boolean nearCommandEnabled = true;
	public static boolean challengeEnabled = true;
	public static int challengeDelay = 5000;
	public static boolean challengeDisplay = true;
	public static boolean challengeLowercase = true;
	public static boolean challengeRandomDelay = true;
	public static boolean challengeSmartDelay = true;
	
	//Config categories
	public static final String CATEGORY_RAINBOW = "rainbowchat";
	public static final String CATEGORY_COMMAND = "commands";
	public static final String CATEGORY_CHALLENGE = "challenge";
	public static final String CATEGORY_OTHER = "other";
	
	public static boolean isInWorld = false;

	//public static KeyBinding key = new KeyBinding("Send anvil packet", Keyboard.KEY_K, "Skeptermod");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ChatHandler());
		MinecraftForge.EVENT_BUS.register(new TPHandler());
		MinecraftForge.EVENT_BUS.register(new TopHandler());
		MinecraftForge.EVENT_BUS.register(new CompassHandler());
		MinecraftForge.EVENT_BUS.register(new NameHighlighter());
		MinecraftForge.EVENT_BUS.register(new NearHandler());
		MinecraftForge.EVENT_BUS.register(new ChallengeHandler());
		//MinecraftForge.EVENT_BUS.register(new AnvilModHandler());
		//MinecraftForge.EVENT_BUS.register(new JoinFlyHandler());
		//ClientRegistry.registerKeyBinding(key);
	}
	
	public static void syncConfig() {
		rainbowchatEnabled = configFile.getBoolean("Rainbow chat enabled", CATEGORY_RAINBOW, rainbowchatEnabled, "Whether the rainbow chat is enabled or not");
		colors = configFile.getString("Rainbow chat color",CATEGORY_RAINBOW, "&4 &c &6 &e &a &2 &b &3 &5 &d", "The order of colors to use", new String[] {"&4 &c &6 &e &a &2 &b &3 &5 &d", "&c &e &a &b &d"});
		loopColor = configFile.getBoolean("Enable color looping", CATEGORY_RAINBOW, loopColor, "Whether the mod will loop the colors from the previous chat messages");
		
		tpCommandEnabled = configFile.getBoolean("/tp command enabled", CATEGORY_COMMAND, tpCommandEnabled, "Whether the /tp command is enabled");
		topCommandEnabled = configFile.getBoolean("/top command enabled", CATEGORY_COMMAND, topCommandEnabled, "Whether the /top command is enabled");
		nearCommandEnabled = configFile.getBoolean("/near command enabled", CATEGORY_COMMAND, nearCommandEnabled, "Whether the /near command is enabled");
		
		challengeEnabled = configFile.getBoolean("Challenge solver enabled", CATEGORY_CHALLENGE, challengeEnabled, "Whether the challenge completer is enabled");
		challengeDelay = configFile.getInt("Challenge delay", CATEGORY_CHALLENGE, challengeDelay,  0, Integer.MAX_VALUE, "The delay between the challenge being asked and answering the challenge");
		challengeDisplay = configFile.getBoolean("Challenge display enabled", CATEGORY_CHALLENGE, challengeDisplay, "Whether the challenge countdown is enabled");
		challengeLowercase = configFile.getBoolean("Lowercase answers", CATEGORY_CHALLENGE, challengeLowercase, "Whether the challenge should be answered in lowercase");
		challengeRandomDelay = configFile.getBoolean("Challenge delay randomiser", CATEGORY_CHALLENGE, challengeRandomDelay, "Adds a random time to the base challenge delay option");
		challengeSmartDelay = configFile.getBoolean("Challenge smart delay", CATEGORY_CHALLENGE, challengeSmartDelay, "Calculates the delay based on the complexity of the string. OVERRIDES RANDOM DELAY");
		
		compassEnabled = configFile.getBoolean("Teleporting compass enabled", CATEGORY_OTHER, compassEnabled, "Whether right clicking with a compass teleports you");
		nameHighlightEnabled = configFile.getBoolean("Name highlighter enabled", CATEGORY_OTHER, nameHighlightEnabled, "Whether it highlights your name");
		
		if (configFile.hasChanged())
			configFile.save();
	}
} 
