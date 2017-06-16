package io.github.skepter.skeptermod;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ModConfigGUI extends GuiConfig {

	public ModConfigGUI(GuiScreen parent) {
		super(parent,
				new ConfigElement(Main.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				"SkepterMod", false, false, "SkepterMod Configuration - " + GuiConfig.getAbridgedConfigPath(Main.configFile.toString()));
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
