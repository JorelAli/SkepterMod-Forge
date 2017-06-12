package io.github.skepter.skeptermod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ModConfigGUI extends GuiConfig {

	public ModConfigGUI(GuiScreen parent) {
		super(parent,
			getConfigElements(),
			//new ConfigElement(Main.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
			Main.MODID, 
			false, 
			false, 
			"SkepterMod Configuration - " + GuiConfig.getAbridgedConfigPath(Main.configFile.toString()));
	}

	/** Compiles a list of config elements */
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
      
        //Add categories to config GUI
        list.add(categoryElement(Main.CATEGORY_RAINBOW, "Rainbow Chat", "Allows you to talk in chat in rainbow colors"));
        list.add(categoryElement(Main.CATEGORY_COMMAND, "Commands", "Enable/Disable commands"));
        list.add(categoryElement(Main.CATEGORY_CHALLENGE, "Challenge", "Challenge options"));
        list.add(categoryElement(Main.CATEGORY_OTHER, "Other", "Enable/Disable other stuff"));
      
        return list;
    }
  
    /** Creates a button linking to another screen where all options of the category are available */
    private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
                new ConfigElement(Main.configFile.getCategory(category)).getChildElements());
    }
	
	
	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		Main.syncConfig();
	}

}
