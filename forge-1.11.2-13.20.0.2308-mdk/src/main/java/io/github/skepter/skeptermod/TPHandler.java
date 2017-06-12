package io.github.skepter.skeptermod;

import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TPHandler {

	boolean readyToTeleport = false;
	boolean acceptingCoordinates = false;

	/*
	 * 0 = the end 1 = main world 2 = spawn 3 = nether
	 */
	int world = -1;
	int x = 1000000;
	int y = -1;
	int z = 1000000;

	boolean similarWorld = false;

	@SubscribeEvent
	public void onChat(ClientChatEvent event) {
		if(Main.tpCommandEnabled) {
			if (event.getOriginalMessage().startsWith("/tp ")) {
				event.setCanceled(true);
				final String[] args = event.getOriginalMessage().split(" ");
				if (args.length == 1) {
					Minecraft.getMinecraft().player.sendMessage(new TextComponentString("[SkepterMod] You did not enter a player name!"));
					return; 
				} else {
					readyToTeleport = true;
					acceptingCoordinates = true;
					doCommand("/coords " + args[1]);

					return;
				}
			}
		}
	}
	
	private boolean isCoordinates(String str) {
		return (str.startsWith("Current World: ") || str.startsWith("X: ") || str.startsWith("Y: ")
				|| str.startsWith("Z: ") || str.startsWith("Yaw: ") || str.startsWith("Pitch: ")
				|| str.startsWith("Distance: "));
	}

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		if(Main.tpCommandEnabled && isCoordinates(event.getMessage().getUnformattedText())) {
			String message = event.getMessage().getUnformattedText();
			if(readyToTeleport || acceptingCoordinates) {
				event.setCanceled(true);
				if (message.startsWith("Current World: ")) {
					if (message.contains("end")) {
						world = 0;
					} else if (message.contains("new")) {
						world = 1;
					} else if (message.contains("creative")) {
						world = 2;
					} else if (message.contains("nether")) {
						world = 3;
					}
				}
				if(message.startsWith("X: ")) {
					x = Integer.parseInt(message.replaceAll("\\D", ""));
					if (message.charAt(3) == '-') {
						x = -x;
					}
				} else if(message.startsWith("Z: ")) {
					z = Integer.parseInt(message.replaceAll("\\D", ""));
					if (message.charAt(3) == '-') {
						z = -z;
					}
				} else if(message.startsWith("Y: ")) {
					y = Integer.parseInt(message.replaceAll("\\D", ""));
				} else if(message.startsWith("Distance: ")) {
					similarWorld = true;
				}
				if((y != -1) && (x != 1000000) && (z != 1000000) && readyToTeleport) {
					readyToTeleport = false;
					new Timer().schedule(new TimerTask() {

						@Override
						public void run() {
							doTeleport();

						}

					}, 500);
					return;
				}
			}
		}
	}

	public void doTeleport() {
		if (similarWorld) {
			world = 4;
			similarWorld = false;
		}
		acceptingCoordinates = false;
		performCommand(world, x, y, z);

		world = -1;
		x = 1000000;
		y = -1;
		z = 1000000;
	}

	public void performCommand(int world, int x, int y, int z) {
		switch (world) {
		case 0:
			doCommandInstant("/mvtp world_new_the_end");
			break;
		case 1:
			doCommandInstant("/mvtp world_new");
			break;
		case 2:
			doCommandInstant("/mvtp world_creative");
			break;
		case 3:
			doCommandInstant("/home n");
			break;
		case -1:
		case 4:
			break;
		}

		doCommand("/tppos " + x + " " + y + " " + z);
	}
	
	public void doCommandInstant(final String command) {
		Minecraft.getMinecraft().player.sendChatMessage(command);
	}
	
	public void doCommand(final String command) {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				Minecraft.getMinecraft().player.sendChatMessage(command);
			}

		}, 500);
	}

}