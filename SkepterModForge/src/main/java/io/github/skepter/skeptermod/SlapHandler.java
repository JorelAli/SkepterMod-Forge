package io.github.skepter.skeptermod;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SlapHandler {

	private long countdown = 10L;
	private boolean canSlap = true;

	@SubscribeEvent
	public void onGameTick(TickEvent event) {
		if (Main.slapModeEnabled) {
			if (Minecraft.getMinecraft().world != null) {
				int range = 5;
				EntityPlayerSP p = Minecraft.getMinecraft().player;
				AxisAlignedBB boundingBox = new AxisAlignedBB(p.posX - range, p.posY - range, p.posZ - range,
						p.posX + range, p.posY + range, p.posZ + range);
				List<EntityPlayer> entities = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityPlayer.class,
						boundingBox);
				for (EntityPlayer e : entities) {
					if (e.getName().equals(Main.slapPlayer) && canSlap) {
						Minecraft.getMinecraft().player.sendChatMessage("/slap " + Main.slapPlayer);
						canSlap = false;
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								canSlap = true;
							}
						}, countdown * 1000);
						break;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onChat(ClientChatEvent event) {
		if (Main.slapModeEnabled) {
			if (event.getOriginalMessage().startsWith("/sslap ")) {
				Minecraft.getMinecraft().player.sendChatMessage("/tp " + event.getOriginalMessage().split(" ")[1]);
				event.setCanceled(true);
			}
		}
	}

}
