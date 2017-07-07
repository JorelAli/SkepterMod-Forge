package io.github.skepter.skeptermod;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChallengeHandler extends Gui {

	private long countdown = 600L;
	private Timer timer;
	
	@SubscribeEvent
	public void render(RenderGameOverlayEvent.Post event) {
		if(Main.challengeDisplay) {
			if(!Minecraft.getMinecraft().gameSettings.showDebugInfo) {
				if(event.getType() != ElementType.EXPERIENCE) return;
				
				ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
				int width = scaled.getScaledWidth();
				//int height = scaled.getScaledHeight();
				
				String time = new SimpleDateFormat("mm:ss").format(new Date(countdown * 1000));
				String formattedTime = time.split(":")[0] + " minutes, " + time.split(":")[1] + " seconds";
				String output = "Time until next challenge: " + formattedTime;
				drawCenteredString(Minecraft.getMinecraft().fontRendererObj, output, width / 2, 2, Integer.parseInt("FFFFFF", 16));
				
				//drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Hai", width / 2, (height / 2) - 4, Integer.parseInt("FFAA00", 16));
			}
			
		}
	}
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		///tellraw @a {"text":"[Owner] PinchBot: The first player to type ABSHE wins $100!"}
		if (Main.challengeEnabled) {

			String message = event.getMessage().getUnformattedText();
			boolean challengeSaidInChat = false;
			if (message.startsWith("[Owner] PinchBot:") && message.contains("type")) {
				countdown = 600;
				final String answer = message.replace("[Owner] PinchBot: The first player to type ", "") .replace(" wins $100!", "");
				if(Main.challengeLowercase) {
					submitAnswer(answer.toLowerCase());
				} else {
					submitAnswer(answer);
				}
				challengeSaidInChat = true;
			} else if (message.startsWith("[Owner] PinchBot:") && message.contains("solve")) {
				countdown = 600;
				///tellraw @a {"text":"[Owner] PinchBot: The first player to solve 5*2 wins $100!"}
				String input = message.replace("[Owner] PinchBot: The first player to solve ", "")
						.replace(" wins $100!", "");

				double d = calculate(input);
				if ((int) d == d) {
					if(Main.challengeDerp) {
						String derp = String.valueOf(ThreadLocalRandom.current().nextLong(1000, 999999));
						String derp2 = String.valueOf(ThreadLocalRandom.current().nextLong(1000, 999999));
						submitAnswer(derp + String.valueOf((int) d) + derp2);
					} else {
						submitAnswer(String.valueOf((int) d));
					}
				} else {
					DecimalFormat twoDecimals = new DecimalFormat("#.00");
					DecimalFormat oneDecimal = new DecimalFormat("#.0");
					if(twoDecimals.format(d).endsWith("0")) {
						if(oneDecimal.format(d).startsWith(".")) {
							submitAnswer("0" + oneDecimal.format(d)); 
						} else {
							submitAnswer(oneDecimal.format(d));
						}
					} else {
						if(twoDecimals.format(d).startsWith(".")) {
							submitAnswer("0" + twoDecimals.format(d)); 
						} else {
							submitAnswer(twoDecimals.format(d));
						}
					}
				}
				challengeSaidInChat = true;
			}
			
			if(challengeSaidInChat) {
				if(timer != null) {
					try {
						timer.cancel();
					} catch(IllegalStateException e) {}
				}
				timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						countdown--;
					}
				}, 0L, 1000L);
			}
		}
	}

	private void submitAnswer(final String answer) {
		final EntityPlayerSP p = Minecraft.getMinecraft().player;
		long time = (long) Main.challengeDelay;
		if(Main.challengeSmartDelay) {
			//String length is always 8.
			int numDigits = answer.replaceAll("\\D", "").length();
			int numLetters = answer.replaceAll("\\d", "").length();
			if(numDigits >= 7) {
				time += 0;
			} else if(numDigits >= 5) {
				time += 500;
			}  else if(numDigits >= 2) {
				time += 1000;
			} 
			
			if(numLetters >= 7) {
				time += 0;
			} else if(numLetters >= 5) {
				time += 500;
			}  else if(numLetters >= 2) {
				time += 1000;
			} 
		} else if(Main.challengeRandomDelay) {
			time += ThreadLocalRandom.current().nextLong(0, 1000);
		}
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				p.sendChatMessage(answer);
			}
		}, time);
	}

	private double calculate(String input) {
		if (input.contains("+")) {
			double num1 = Double.parseDouble(input.split("\\+")[0]);
			double num2 = Double.parseDouble(input.split("\\+")[1]);
			return num1 + num2;
		}
		if (input.contains("-")) {
			double num1 = Double.parseDouble(input.split("-")[0]);
			double num2 = Double.parseDouble(input.split("-")[1]);
			return num1 - num2;
		}
		if (input.contains("*")) {
			double num1 = Double.parseDouble(input.split("\\*")[0]);
			double num2 = Double.parseDouble(input.split("\\*")[1]);
			return num1 * num2;
		}
		if (input.contains("/")) {
			double num1 = Double.parseDouble(input.split("/")[0]);
			double num2 = Double.parseDouble(input.split("/")[1]);
			return num1 / num2;
		}
		return 0.0D;
	}
	
}
