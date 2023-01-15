package net.demoman.roboticsolutions;

import net.demoman.roboticsolutions.events.ClientEvents;
import net.demoman.roboticsolutions.events.ModEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RoboticSolutions.MODID)
public class RoboticSolutions
{
    public static final String MODID = "roboticsolutions";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public RoboticSolutions() {
        // Register the setup method for modloading
        //Use an IEventBus variable to simplify code/make it easier to read
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModRegistration.init(eventBus);
        ModEvents.setup(eventBus);

        eventBus.addListener(ModEvents::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(ClientEvents::init));
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
