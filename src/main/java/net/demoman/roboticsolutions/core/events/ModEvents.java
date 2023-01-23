package net.demoman.roboticsolutions.core.events;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.particle.custom.WhiteSmokeParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static net.demoman.roboticsolutions.core.particle.ModParticles.WHITE_SMOKE_PARTICLE;

@Mod.EventBusSubscriber(modid = RoboticSolutions.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    // Register stuff like entity attributes here

    public static void setup(IEventBus eventBus) {
    }

    public static void init(FMLCommonSetupEvent event) {

    }
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(WHITE_SMOKE_PARTICLE.get(), WhiteSmokeParticle.Factory::new);
    }

}
