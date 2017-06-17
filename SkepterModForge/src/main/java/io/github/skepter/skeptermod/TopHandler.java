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
				//Simple trick to teleport them as high as possible
				//If server has teleporting checks, it doesn't teleport them to x 256 z, it
				//teleports them to the highest block
				Minecraft.getMinecraft().player.sendChatMessage("/tppos ~ 256 ~");
			}
		}
	}
}
