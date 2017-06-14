package io.github.skepter.skeptermod;

/*
 * Class to test events and stuff
 */
public class TestHandler {
	
//	@SubscribeEvent
//	public void render(RenderWorldLastEvent event) {
//		for(TileEntity e : Minecraft.getMinecraft().world.loadedTileEntityList) {
//			if(e instanceof TileEntityBeacon) {
//				TileEntityBeacon beacon = (TileEntityBeacon) e;
//					
//				if (Minecraft.getMinecraft().world.getTotalWorldTime() % 80L == 1L) {
//					try {
//						Field beaconSegments = beacon.getClass().getDeclaredField("beamSegments");
//						beaconSegments.setAccessible(true);
//						
//						List<BeamSegment> beams = Lists.<BeamSegment>newArrayList();
//						for(int i = 0; i <= 100; i++) {
//							beams.add(new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.RED)));
//							beams.add(new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.ORANGE)));
//							beams.add(new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.YELLOW)));
//							beams.add(new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.LIME)));
//							beams.add(new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.LIGHT_BLUE)));
//							beams.add(new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.MAGENTA)));
//							beams.add(new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.PURPLE)));
//						}
//						beaconSegments.set(beacon, beams);
//					} catch (NoSuchFieldException e1) {
//						e1.printStackTrace();
//					} catch (SecurityException e1) {
//						e1.printStackTrace();
//					} catch (IllegalArgumentException e1) {
//						e1.printStackTrace();
//					} catch (IllegalAccessException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		}
//	}
	
	
	
//	@SubscribeEvent
//	public void beacon(TickEvent event) {
//		if(Minecraft.getMinecraft() != null) {
//			if(Minecraft.getMinecraft().world != null) {
//				for(TileEntity e : Minecraft.getMinecraft().world.loadedTileEntityList) {
//					if(e instanceof TileEntityBeacon) {
//						TileEntityBeacon beacon = (TileEntityBeacon) e;
//						try {
//							Field beaconSegments = beacon.getClass().getDeclaredField("beamSegments");
//							beaconSegments.setAccessible(true);
//							
//							List<BeamSegment> beams = Lists.<BeamSegment>newArrayList();
//							BeamSegment beamSegment = new BeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.RED));
//							beams.add(beamSegment);
//							beaconSegments.set(beacon, beams);
//						} catch (NoSuchFieldException e1) {
//							e1.printStackTrace();
//						} catch (SecurityException e1) {
//							e1.printStackTrace();
//						} catch (IllegalArgumentException e1) {
//							e1.printStackTrace();
//						} catch (IllegalAccessException e1) {
//							e1.printStackTrace();
//						}
//						
//						TileEntityBeaconRenderer.renderBeamSegment(100, 100, 100, 1, 1, Minecraft.getMinecraft().world.getTotalWorldTime(), 2, 1, EntitySheep.getDyeRgb(EnumDyeColor.RED), 0.2D, 0.25D);
//						
//						
//						
//					}
//				}
//			}
//			
//		}
//		
//	}
	
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
//	
//	@SubscribeEvent
//	public void onChat(LivingJumpEvent event) {
//		System.out.println("LivingJumpEvent event");
//	}
	
}
