package io.github.skepter.skeptermod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TopHandler {
	
	@SubscribeEvent
	public void onChat(ClientChatEvent event) {
		if(Main.topCommandEnabled) {
			if(event.getOriginalMessage().startsWith("/top")) {
				event.setCanceled(true);
				Minecraft.getMinecraft().player.sendChatMessage("/tppos ~ 256 ~");
			}
		}
	}
}
