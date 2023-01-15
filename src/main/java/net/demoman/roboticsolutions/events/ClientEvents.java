package net.demoman.roboticsolutions.events;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = RoboticSolutions.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }
}
