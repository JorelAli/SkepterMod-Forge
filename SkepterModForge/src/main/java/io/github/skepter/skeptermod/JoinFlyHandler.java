package io.github.skepter.skeptermod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

@Deprecated
public class JoinFlyHandler {
	
	@SubscribeEvent
	public void onLeave(ClientDisconnectionFromServerEvent event) {
		Main.isInWorld = false;
	}
	
	@SubscribeEvent
	public void onJoin(ClientConnectedToServerEvent event) {
		Main.isInWorld = true;
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		if(player == null) {
			return;
		} else {
			if(player.onGround) {
				player.sendChatMessage("/fly");
			}
		}
	}
	
}
