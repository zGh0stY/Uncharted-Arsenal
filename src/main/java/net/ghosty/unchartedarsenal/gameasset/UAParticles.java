package net.ghosty.unchartedarsenal.gameasset;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UAParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, UnchartedArsenal.MOD_ID);

    public static final RegistryObject<SimpleParticleType> FIRE_GEYSER = PARTICLES.register("fire_geyser", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FIRE_GEYSER_START = PARTICLES.register("fire_geyser_start", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FIRE_GEYSER_END = PARTICLES.register("fire_geyser_end", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FALLING_FLAME = PARTICLES.register("falling_flame", () -> new SimpleParticleType(true));
}
