package io.github.skepter.skeptermod;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatHandler {

	private String[] colors;
	private int colorVar = 0;
	
	@SubscribeEvent
	public void onChat(ClientChatEvent event) {
		if(event.getOriginalMessage().startsWith("/")) { 
			return;
		} else if(Main.rainbowchatEnabled) {
			event.setMessage(addRainbow(event.getOriginalMessage()));
		}
	}
	
	private String addRainbow(String str) {
		//inserts chatcolors to simulate rainbow chat
		colors = Main.colors.split(" ");
		StringBuilder newString = new StringBuilder("");
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if(c == ' ') {
				newString.append(c);
				continue;
			}
			newString.append(colors[colorVar % colors.length]);
			colorVar++;
			newString.append(c);
		}
		if(!Main.loopColor) {
			colorVar = 0;
		}
		return newString.toString();
	}
	
}
