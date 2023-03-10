package net.demoman.roboticsolutions;

import net.demoman.roboticsolutions.core.BlockRegistration;
import net.demoman.roboticsolutions.core.ItemRegistration;
import net.demoman.roboticsolutions.core.events.ClientEvents;
import net.demoman.roboticsolutions.core.events.ModEvents;
import net.demoman.roboticsolutions.core.fluid.ModFluids;
import net.demoman.roboticsolutions.core.particle.ModParticles;
import net.demoman.roboticsolutions.core.world.biome.ModBiomes;
import net.demoman.roboticsolutions.core.world.gen.ModBiomeGeneration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RoboticSolutions.MODID)
public class RoboticSolutions
{
    public static final String MODID = "roboticsolutions";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public RoboticSolutions() {
        // Register the setup method for modloading
        // Use an IEventBus variable to simplify code/make it easier to read
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockRegistration.init(eventBus);
        ItemRegistration.init(eventBus);
        ModEvents.setup(eventBus);
        ModFluids.register(eventBus);
        ModParticles.register(eventBus);
        ModBiomes.init(eventBus);
        


        eventBus.addListener(ModEvents::init);
        eventBus.addListener(this::setup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(ClientEvents::init));
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModBiomeGeneration.generateBiomes();
        });
    }
}
