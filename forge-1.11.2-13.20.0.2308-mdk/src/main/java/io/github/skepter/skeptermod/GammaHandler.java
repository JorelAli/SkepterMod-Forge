package io.github.skepter.skeptermod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class GammaHandler {

	boolean gammaOn = false;

	public void adjustBrightness() {
		if (gammaOn) {
			Minecraft.getMinecraft().gameSettings.setOptionFloatValue(Options.GAMMA, 1);
			gammaOn = false;
		} else {
			Minecraft.getMinecraft().gameSettings.setOptionFloatValue(Options.GAMMA, 1000);
			gammaOn = true;
		}
		Minecraft.getMinecraft().gameSettings.saveOptions();
		Minecraft.getMinecraft().gameSettings.loadOptions();
	}

	@SubscribeEvent
	public void onKey(KeyInputEvent event) {
		if (Main.gammaKey.isPressed()) {
			adjustBrightness();
		}
	}
}
