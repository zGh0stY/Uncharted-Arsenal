package net.ghosty.unchartedarsenal.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;

public class FallingFlameParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    FallingFlameParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteset, double dX, double dY, double dZ) {
        super(pLevel, pX, pY, pZ, dX, dY, dZ);

        this.gravity = 0.75F;
        this.friction = 0.999F;
        this.xd = dX;
        this.yd = dY;
        this.zd = dZ;
        this.lifetime = 30;
        this.quadSize = 1.0F;
        this.setSpriteFromAge(spriteset);
        sprites = spriteset;

        this.rCol = 1F;
        this.gCol = 1F;
        this.bCol = 1F;

        this.quadSize = 0.1F;
    }

    @Override
    public void tick() {
        super.tick();

        this.alpha = (-(1 / (float) lifetime) * age + 1);
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return LightTexture.FULL_BRIGHT;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new FallingFlameParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
