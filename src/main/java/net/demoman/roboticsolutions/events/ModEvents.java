package net.demoman.roboticsolutions.events;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = RoboticSolutions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    //Register stuff like entity attributes here

    public static void setup(IEventBus eventBus) {

    }

    public static void init(FMLCommonSetupEvent event) {

    }
}
