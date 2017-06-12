package io.github.skepter.skeptermod;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NameHighlighter {
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		//Make name bold and gold so it stands out if someone says my name
		if(event.getMessage().getUnformattedText().contains("Skepter")) {
			Style style = new Style();
			style.setBold(true);
			style.setColor(TextFormatting.GOLD);
			event.setMessage(new TextComponentString(event.getMessage().getFormattedText().replaceAll("Skepter", new TextComponentString("Skepter").setStyle(style).getFormattedText())));
		} else if(event.getMessage().getUnformattedText().contains("Skep")) {
			Style style = new Style();
			style.setBold(true);
			style.setColor(TextFormatting.GOLD);
			event.setMessage(new TextComponentString(event.getMessage().getFormattedText().replaceAll("Skep", new TextComponentString("Skep").setStyle(style).getFormattedText())));
		}
	}
	
}
