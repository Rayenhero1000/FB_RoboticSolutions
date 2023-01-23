package net.demoman.roboticsolutions.core.particle;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, RoboticSolutions.MODID);
    public static final RegistryObject<BasicParticleType> WHITE_SMOKE_PARTICLE =
            PARTICLE_TYPES.register("white_smoke", () -> new BasicParticleType(true));
    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

}
