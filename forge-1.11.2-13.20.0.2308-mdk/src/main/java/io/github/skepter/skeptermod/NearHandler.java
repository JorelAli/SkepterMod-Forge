package io.github.skepter.skeptermod;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NearHandler {
	
	private final int dist = 200;
	
	@SubscribeEvent
	public void onNearCommand(ClientChatEvent event) {
		if(Main.nearCommandEnabled) {
			if(event.getOriginalMessage().equalsIgnoreCase("/near")) {
				event.setCanceled(true);
				
				Style gold = new Style();
				gold.setColor(TextFormatting.GOLD);
				
				Style white = new Style();
				white.setColor(TextFormatting.WHITE);
				
				Style red = new Style();
				red.setColor(TextFormatting.DARK_RED);
				
				
				EntityPlayerSP p = Minecraft.getMinecraft().player;
				AxisAlignedBB boundingBox = new AxisAlignedBB(p.posX - dist, p.posY - dist, p.posZ - dist, p.posX + dist, p.posY + dist, p.posZ + dist);
				List<EntityPlayer> players = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityPlayer.class, boundingBox);
				if(players.size() == 1) {
					ITextComponent string = new TextComponentString("Players nearby: ").setStyle(gold).appendSibling(new TextComponentString("none").setStyle(white));
					p.sendMessage(string);
					return;
				} else {
					ITextComponent string = new TextComponentString("Players nearby: ").setStyle(gold);
					players.remove(p);
					for (int j = 0; j < players.size(); j++) {
						EntityPlayer player = players.get(j);
						int i = (int) player.getDistance(p.posX, p.posY, p.posZ);

						if (j != 0 || j != players.size() - 1) {
							ITextComponent comma = new TextComponentString(", ").setStyle(white);
							string.appendSibling(comma);
						}

						ITextComponent name = new TextComponentString(player.getName() + "(").setStyle(white);
						ITextComponent dist = new TextComponentString(String.valueOf(i) + "m").setStyle(red);
						ITextComponent end = new TextComponentString(")").setStyle(white);
						string.appendSibling(name);
						string.appendSibling(dist);
						string.appendSibling(end);

					}
					p.sendMessage(string);
				}
			}
		}
	}
}
