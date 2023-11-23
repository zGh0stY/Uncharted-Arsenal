package net.ghosty.unchartedarsenal.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.particle.EpicFightParticles;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class FireGeyserStartParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected FireGeyserStartParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteset, double dX, double dY, double dZ) {
        super(pLevel, pX, pY, pZ, dX, dY, dZ);

        this.friction = 0;
        this.xd = dX;
        this.yd = dY;
        this.zd = dZ;
        this.lifetime = 5;
        this.quadSize = 4.0F;
        this.setSpriteFromAge(spriteset);
        sprites = spriteset;

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        Vec3 vec3 = pRenderInfo.getPosition();
        float f = (float)(Mth.lerp((double)pPartialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp((double)pPartialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp((double)pPartialTicks, this.zo, this.z) - vec3.z());
        Quaternionf quaternionf;
        if (this.roll == 0.0F) {
            quaternionf = pRenderInfo.rotation();
        } else {
            quaternionf = new Quaternionf(pRenderInfo.rotation());
            quaternionf.rotateZ(Mth.lerp(pPartialTicks, this.oRoll, this.roll));
        }
        quaternionf.z = 0;
        quaternionf.x = 0;

        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f3 = this.getQuadSize(pPartialTicks);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quaternionf);
            vector3f.mul(f3);
            vector3f.add(f, f1, f2);
        }

        float f6 = this.getU0();
        float f7 = this.getU1();
        float f4 = this.getV0();
        float f5 = this.getV1();
        int j = this.getLightColor(pPartialTicks);
        pBuffer.vertex((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        pBuffer.vertex((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).uv(f7, f4).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        pBuffer.vertex((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).uv(f6, f4).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        pBuffer.vertex((double)avector3f[3].x(), (double)avector3f[3].y(), (double)avector3f[3].z()).uv(f6, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (age++ > lifetime) {
            this.remove();
            this.level.addParticle(UAParticles.FIRE_GEYSER.get(), this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
        }
        else {
            this.setSpriteFromAge(sprites);
        }

        if (age == 1) {
            int n = 5; // set the number of particles to emit
            double r = 0.6; // set the radius of the disk to 1
            double t = 0.01; // set the thickness of the disk to 0.1

            for (int i = 0; i < n; i++) {
                double theta = 2 * Math.PI * new Random().nextDouble(); // generate a random azimuthal angle
                double phi = (new Random().nextDouble() - 0.5) * Math.PI * t / r; // generate a random angle within the disk thickness

                // calculate the emission direction in Cartesian coordinates using the polar coordinates
                double x = r * Math.cos(phi) * Math.cos(theta);
                double y = r * Math.cos(phi) * Math.sin(theta);
                double z = 0;

                // create a Vector3f object to represent the emission direction
                Vec3f direction = new Vec3f((float)x, (float)y, (float)z);

                // rotate the direction vector to align with the forward vector
                OpenMatrix4f rotation = new OpenMatrix4f().rotate((float) Math.toRadians(80), new Vec3f(1, 0, 0));
                OpenMatrix4f.transform3v(rotation, direction, direction);

                // emit the particle in the calculated direction, with some random velocity added
                this.level.addParticle(UAParticles.FALLING_FLAME.get(),
                        (this.x),
                        (this.y),
                        (this.z),
                        (float)(direction.x),
                        (float)(direction.y),
                        (float)(direction.z));
            }
        }
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
            return new FireGeyserStartParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
