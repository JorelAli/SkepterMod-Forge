package io.github.skepter.skeptermod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CompassHandler {
	
	@SubscribeEvent
	public void compassClick(PlayerInteractEvent event) {
		if(Main.compassEnabled) {
			//If they're holding a compass
			if(ItemStack.areItemsEqual(event.getItemStack(), new ItemStack(Item.getItemById(345)))) {		
				//Get the target block (at a maximums of 1024 blocks away)
				BlockPos targetBlockDistance = Minecraft.getMinecraft().player.rayTrace(Main.compassDistance, 1.0F).getBlockPos();
				if(Minecraft.getMinecraft().objectMouseOver == null) {
					return;
				}
				//Get the target block (about 5 blocks away) 
				//This will be the block that the player is currently "highlighting"
				//This may be "air"
				BlockPos targetBlockClose = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
				if(targetBlockClose == null || targetBlockDistance == null) {
					return;
				}
				
				//Since we can't detect left/right click, use a distance check
				//to determine whether we're going through a block, or teleporting
				//to a block
				if(arePositionsSimilar(targetBlockClose, targetBlockDistance)) {
					//Do a /thru command (equivalent)

					//System.out.println("Blocks are similar (we're looking directly at a block)");
					int x = 0;
					int y = -10;
					int z = 0;
					//System.out.println("We are looking " + Minecraft.getMinecraft().player.getHorizontalFacing().name());
					switch(Minecraft.getMinecraft().player.getHorizontalFacing()) {
					//Switch user's facing direction to somewhat determine which direction
					//to /thru through
						case EAST: {
							for(int i = 1; i <= Main.compassThruDistance; i++) {
								if(isAir(targetBlockClose.east(i))) {
									x = targetBlockClose.east(i).getX();
									y = targetBlockClose.east(i).getY();
									z = targetBlockClose.east(i).getZ();
									break;
								}
							}
							break;
						}
						case NORTH: {
							for(int i = 1; i <= Main.compassThruDistance; i++) {
								if(isAir(targetBlockClose.north(i))) {
									x = targetBlockClose.north(i).getX();
									y = targetBlockClose.north(i).getY();
									z = targetBlockClose.north(i).getZ();
									break;
								}
							}
							break;
						}
						case SOUTH: {
							for(int i = 1; i <= Main.compassThruDistance; i++) {
								if(isAir(targetBlockClose.south(i))) {
									x = targetBlockClose.south(i).getX();
									y = targetBlockClose.south(i).getY();
									z = targetBlockClose.south(i).getZ();
									break;
								}
							}
							break;
						}
						case WEST: {
							for(int i = 1; i <= Main.compassThruDistance; i++) {
								if(isAir(targetBlockClose.west(i))) {
									x = targetBlockClose.west(i).getX();
									y = targetBlockClose.west(i).getY();
									z = targetBlockClose.west(i).getZ();
									break;
								}
							}
							break;
						}
						default:
							break;
					}
					
					//Down
					if(Minecraft.getMinecraft().player.rotationPitch > 60) {
						for(int i = 1; i <= Main.compassThruDistance; i++) {
							if(isAir(targetBlockClose.down(i))) {
								x = targetBlockClose.down(i).getX();
								y = targetBlockClose.down(i).getY() - 1;
								z = targetBlockClose.down(i).getZ();
								break;
							}
						}
					}
					
					//Up
					if(Minecraft.getMinecraft().player.rotationPitch < -60) {
						for(int i = 1; i <= Main.compassThruDistance; i++) {
							if(isAir(targetBlockClose.up(i))) {
								x = targetBlockClose.up(i).getX();
								y = targetBlockClose.up(i).getY();
								z = targetBlockClose.up(i).getZ();
								break;
							}
						}
					}
					//Prevent teleporting through bedrock
					if(y <= 0) {
						return;
					}
					//Could not find coordinates
					if(x == 0 && y == -10 && z == 0) {
						return;
					} else {
						//Teleport to location through blocks
						teleportSafely(x, y, z);
					}
				} else {
					//Teleport to location
					int x = targetBlockDistance.getX();
					int y = targetBlockDistance.getY(); // + 1
					int z = targetBlockDistance.getZ();
					teleportSafely(x, y, z);
				}
			}
		}
	}
	
	private void teleportSafely(int x, int y, int z) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		BlockPos pos = new BlockPos(x,y,z);

		//Check if air above block
		if(isAir(pos.up()) && isAir(pos.up(2))) {
			//System.out.println("Teleporting normally (block above is air)");
			teleportCheckingY(x, y, z);
			return;
		} else {
			//Finds the closest block to the player and teleports there IF there is air above it
			BlockPos[] poss = new BlockPos[] {new BlockPos(x+1,y,z), new BlockPos(x-1,y,z), new BlockPos(x,y,z+1), new BlockPos(x,y,z-1)};
			
			double minDistance = Main.compassDistance * 2;
			BlockPos minBlockPos = null;
			for(BlockPos p : poss) {
				double distance = p.distanceSq(player.posX, player.posY, player.posZ);
				if(distance < minDistance) {
					minBlockPos = p;
					minDistance = distance;
				}
			}
			if(minBlockPos == null) {
				//System.out.println("Couldn't find minBlockPosition. Smushing face into target block");
				teleportCheckingY(x, y, z);
				return;
			}
			if(isAir(minBlockPos.up())) {
				//System.out.println("Teleporting to block closest to player");
				teleportCheckingY(minBlockPos.getX(), minBlockPos.getY(), minBlockPos.getZ());
				return;
			} else {
				//Teleports to a block near the target block, IF there is air above it
				for(BlockPos p : poss) {
					if(isAir(p.up())) {
						//System.out.println("Teleporting to suitable block at target");
						teleportCheckingY(p.getX(), p.getY(), p.getZ());
						return;
					}
				}
				//System.out.println("Smushing face into target block");
				teleportCheckingY(x, y, z);
				return;
			}
		}
	}
	
	//Checks the Y direction for air before teleporting
	private void teleportCheckingY(int x, int y, int z) {
		BlockPos pos = new BlockPos(x,y,z);
		if(isAir(pos)) {
			Minecraft.getMinecraft().player.sendChatMessage("/tppos " + x + " " + y + " " + z);
		} else {
			if(isAir(pos.up()) && isAir(pos.up(2))) {
				Minecraft.getMinecraft().player.sendChatMessage("/tppos " + x + " " + (y + 1) + " " + z);
			} else {
				Minecraft.getMinecraft().player.sendChatMessage("/tppos " + x + " " + y + " " + z);
			}
		}
		
	}
	
	//Checks if two BlockPos' are similar
	private boolean arePositionsSimilar(BlockPos pos1, BlockPos pos2) {
		return (pos1.getX() == pos2.getX()) && (pos1.getY() == pos2.getY()) && (pos1.getZ() == pos2.getZ());
	}

	//Checks if a block is air
	private boolean isAir(BlockPos bp) {
		return Minecraft.getMinecraft().world.isAirBlock(bp);
	}
}