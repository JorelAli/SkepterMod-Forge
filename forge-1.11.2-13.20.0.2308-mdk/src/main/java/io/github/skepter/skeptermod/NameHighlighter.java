package io.github.skepter.skeptermod;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NameHighlighter {
	
	Queue<String> sentMsgs = new LinkedList<String>();
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		if(Main.nameHighlightEnabled) {
			for(String str : sentMsgs) {
				if(event.getMessage().getUnformattedText().contains(str)) {
					sentMsgs.remove();
					return;
				}
			}
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
			}  else if(event.getMessage().getUnformattedText().contains("skepter")) {
				Style style = new Style();
				style.setBold(true);
				style.setColor(TextFormatting.GOLD);
				event.setMessage(new TextComponentString(event.getMessage().getFormattedText().replaceAll("skepter", new TextComponentString("skepter").setStyle(style).getFormattedText())));
			} else if(event.getMessage().getUnformattedText().contains("skep")) {
				Style style = new Style();
				style.setBold(true);
				style.setColor(TextFormatting.GOLD);
				event.setMessage(new TextComponentString(event.getMessage().getFormattedText().replaceAll("skep", new TextComponentString("skep").setStyle(style).getFormattedText())));
			}
		}
	}
	
	@SubscribeEvent
	public void onSendChat(ClientChatEvent event) {
		if(Main.nameHighlightEnabled) {
			if(sentMsgs.size() == 5) {
				sentMsgs.remove();
			}
			sentMsgs.add(event.getOriginalMessage());
		}
	}
}
