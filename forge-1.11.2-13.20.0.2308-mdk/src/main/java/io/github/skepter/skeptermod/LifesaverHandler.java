package io.github.skepter.skeptermod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LifesaverHandler {
	
	@SubscribeEvent
	public void onChat(LivingFallEvent event) {
		if(Main.lifeSaverEnabled) {
			if(Main.lifeSaverFatalOnly) {
				if(isFatal(event.getDistance())) {
					Minecraft.getMinecraft().player.sendChatMessage("/fly");
				}
			} else {
				Minecraft.getMinecraft().player.sendChatMessage("/fly");
			}
		}
	}
	
	private boolean isFatal(float distance) {
		if(distance <= 3.0F) {
			return false;
		} else {
			float health = Minecraft.getMinecraft().player.getHealth();
			float damage = distance - 3;
			return damage > health;
		}
	}
	
}
