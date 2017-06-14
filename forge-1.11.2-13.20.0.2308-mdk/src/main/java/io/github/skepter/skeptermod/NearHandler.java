package io.github.skepter.skeptermod;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEvoker;
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
				
				//Styles
				Style gold = new Style();
				gold.setColor(TextFormatting.GOLD);
				
				Style white = new Style();
				white.setColor(TextFormatting.WHITE);
				
				Style red = new Style();
				red.setColor(TextFormatting.DARK_RED);
				
				EntityPlayerSP p = Minecraft.getMinecraft().player;
				//area of around 200 blocks around the player
				AxisAlignedBB boundingBox = new AxisAlignedBB(p.posX - dist, p.posY - dist, p.posZ - dist, p.posX + dist, p.posY + dist, p.posZ + dist);
				List<EntityPlayer> players = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityPlayer.class, boundingBox);
				if(players.size() == 1) {
					//The list only contains itself
					ITextComponent string = new TextComponentString("Players nearby: ").setStyle(gold).appendSibling(new TextComponentString("none").setStyle(white));
					p.sendMessage(string);
					return;
				} else {
					ITextComponent string = new TextComponentString("Players nearby: ").setStyle(gold);
					//Remove themselves from the list (prevents weird commas coming up)
					players.remove(p);
					for (int j = 0; j < players.size(); j++) {
						EntityPlayer player = players.get(j);
						
						//Find distances between the player and nearby players
						int i = (int) player.getDistance(p.posX, p.posY, p.posZ);

						if (j != 0 || j != players.size() - 1) {
							ITextComponent comma = new TextComponentString(", ").setStyle(white);
							string.appendSibling(comma);
						}

						//Essentials color formatting
						ITextComponent name = new TextComponentString(player.getName() + "(").setStyle(white);
						ITextComponent dist = new TextComponentString(String.valueOf(i) + "m").setStyle(red);
						ITextComponent end = new TextComponentString(")").setStyle(white);
						string.appendSibling(name);
						string.appendSibling(dist);
						string.appendSibling(end);

					}
					p.sendMessage(string);

					p.sendMessage(new TextComponentString(ChatFormatting.GOLD + "Total of " + ChatFormatting.DARK_RED + String.valueOf(players.size()) + " " + ChatFormatting.GOLD + "players"));
				}
			} else if(event.getOriginalMessage().startsWith("/nearentities") || event.getOriginalMessage().startsWith("/nearbyentities")) {
				//Prints out nearby entities
				event.setCanceled(true);
				
				//Styles
				Style gold = new Style();
				gold.setColor(TextFormatting.GOLD);
				
				Style white = new Style();
				white.setColor(TextFormatting.WHITE);
				
				Style red = new Style();
				red.setColor(TextFormatting.DARK_RED);
				
				String rangeStr = event.getOriginalMessage().replaceAll("\\D", "");
				int range = 200;
				if(rangeStr.length() != 0) {
					range = Integer.parseInt(rangeStr);
				}
				EntityPlayerSP p = Minecraft.getMinecraft().player;
				//area of around 200 blocks around the player
				AxisAlignedBB boundingBox = new AxisAlignedBB(p.posX - range, p.posY - range, p.posZ - range, p.posX + range, p.posY + range, p.posZ + range);
				List<Entity> entities = Minecraft.getMinecraft().world.getEntitiesWithinAABBExcludingEntity(p, boundingBox);
				for(Entity e : entities) {
					p.sendMessage(new TextComponentString(e.getName() + " (" + ((int) e.posX) + ", " + ((int) e.posY) + ", " + ((int) e.posZ) + ")"));
				}
				
				p.sendMessage(new TextComponentString(ChatFormatting.GOLD + "Total of " + ChatFormatting.DARK_RED + String.valueOf(entities.size()) + " " + ChatFormatting.GOLD + "entities"));

			} else if(event.getOriginalMessage().startsWith("/evoker")) {
				event.setCanceled(true);
				int range = 200;
				EntityPlayerSP p = Minecraft.getMinecraft().player;
				AxisAlignedBB boundingBox = new AxisAlignedBB(p.posX - range, p.posY - range, p.posZ - range, p.posX + range, p.posY + range, p.posZ + range);
				List<EntityEvoker> entities = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityEvoker.class, boundingBox);
				if(entities.isEmpty()) {
					p.sendMessage(new TextComponentString("No evokers nearby :("));
				}
				for(Entity e : entities) {
					p.sendMessage(new TextComponentString(e.getName() + " (" + ((int) e.posX) + ", " + ((int) e.posY) + ", " + ((int) e.posZ) + ")"));
				}
			}
		}
	}
}
