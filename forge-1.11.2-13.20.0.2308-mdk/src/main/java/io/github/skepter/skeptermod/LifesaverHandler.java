package io.github.skepter.skeptermod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LifesaverHandler {
	
	@SubscribeEvent
	public void onChat(LivingFallEvent event) {
		if(event.getDistance() > 3.0F) {
			Minecraft.getMinecraft().player.sendChatMessage("/fly");
		}
	}
	
}
