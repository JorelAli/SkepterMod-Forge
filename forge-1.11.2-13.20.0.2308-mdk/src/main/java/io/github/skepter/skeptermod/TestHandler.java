package io.github.skepter.skeptermod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
 * Class to test events and stuff
 */
public class TestHandler {
	
//	@SubscribeEvent
//	public void onChat(ClientChatEvent event) {
//		if(event.getOriginalMessage().contains("test")) {
//			System.out.println("Test successful");
//		}
//	}
//	
//	@SubscribeEvent
//	public void onChat(PlayerFlyableFallEvent event) {
//		System.out.println("Flying falling event");
//	}
//	
//	@SubscribeEvent
//	public void onChat(ArrowNockEvent event) {
//		System.out.println("Drawing bow...");
//	}
//	
//	@SubscribeEvent
//	public void onChat(ArrowLooseEvent event) {
//		System.out.println("Fired arrow!");
//		new TileEntityBeacon.BeamSegment(null);
//	}
	
	@SubscribeEvent
	public void onChat(LivingFallEvent event) {
		if(event.getDistance() > 3.0F) {
			Minecraft.getMinecraft().player.sendChatMessage("/fly");
		}
//		System.out.println(event.getDistance());
//		System.out.println("LivingFallEvent event");
	}
//	
//	@SubscribeEvent
//	public void onChat(LivingJumpEvent event) {
//		System.out.println("LivingJumpEvent event");
//	}
	
}
