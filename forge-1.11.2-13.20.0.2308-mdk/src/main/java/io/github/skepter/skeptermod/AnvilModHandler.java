package io.github.skepter.skeptermod;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Deprecated
public class AnvilModHandler {

	@SubscribeEvent
	public void onKey(AnvilUpdateEvent event) {
		System.out.println("Key was pressed!");
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString("hey"));

		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF("6hello");
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		buffer.writeBytes(output.toByteArray());

		CPacketCustomPayload packet = new CPacketCustomPayload("MC|ItemName", buffer);
		Minecraft.getMinecraft().getConnection().sendPacket(packet);
		
		CPacketCustomPayload packet1 = new CPacketCustomPayload("SM|Test", buffer);
		Minecraft.getMinecraft().getConnection().sendPacket(packet1);
	}

}
