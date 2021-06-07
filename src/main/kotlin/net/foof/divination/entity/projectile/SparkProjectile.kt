package net.foof.divination.entity.projectile

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.foof.divination.register.Entities.SPARK
import net.foof.divination.register.Items.WAND_OF_SPARKING
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.StrayEntity
import net.minecraft.entity.passive.SnowGolemEntity
import net.minecraft.entity.projectile.thrown.ThrownEntity
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.Item
import net.minecraft.particle.ItemStackParticleEffect
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundEvents
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World


class SparkProjectile : ThrownItemEntity {

    constructor(entityType: EntityType<out ThrownItemEntity?>?, world: World?) : super(entityType, world)
    constructor(world: World?, owner: LivingEntity?) : super(SPARK, owner, world)
    constructor(world: World?, x: Double, y: Double, z: Double) : super(SPARK, x, y, z, world)

    override fun getDefaultItem(): Item = WAND_OF_SPARKING

    // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
    @get:Environment(EnvType.CLIENT)
    private val particleParameters: ParticleEffect
        get() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
            val itemStack = this.item
            return (if (itemStack.isEmpty) ParticleTypes.LAVA else ItemStackParticleEffect(
                ParticleTypes.ITEM,
                itemStack
            )) as ParticleEffect
        }

    @Environment(EnvType.CLIENT)
    override fun handleStatus(status: Byte) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
        if (status.toInt() == 3) {
            val particleEffect = particleParameters
            for (i in 0..7) {
                world.addParticle(particleEffect, this.x, this.y, this.z, 0.0, 0.0, 0.0)
            }
        }
    }

    override fun onEntityHit(entityHitResult: EntityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult)
        val entity: Entity = entityHitResult.entity // sets a new Entity instance as the EntityHitResult (victim)
        val i = if (entity is SnowGolemEntity || entity is StrayEntity) 8 else 4 // sets damage to 8 if the Entity instance is an instance of SnowGolemEntity or StrayEntity
        entity.damage(DamageSource.thrownProjectile(this, this.owner), i.toFloat()) // deals damage
        if (entity is LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            entity.setOnFireFor(2) // sets on fire
            // entity.addStatusEffect(StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 2, 3)) // applies a status effect
            entity.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 0.3f, 1.8f) // plays a sound for the entity hit only
            groundParticles() // particle effects
        }
    }

    override fun onCollision(hitResult: HitResult) { // called on collision with a block
        super.onCollision(hitResult)
        groundParticles()

        if (!world.isClient) { // checks if the world is client
            world.sendEntityStatus(this, 3.toByte()) // particle?
            this.remove() // kills the projectile
        }
    }
    // runs each tick
    override fun tick() {
        super.tick()
        tickParticles()
    }
    // particle creation functions
    private fun tickParticles() {
        val world: World? = MinecraftClient.getInstance().world
        val entity = this as ThrownEntity
            world!!.addParticle(ParticleTypes.FLAME, true, entity.x, entity.y, entity.z, 0.0, 0.1, 0.0)
            world!!.addParticle(ParticleTypes.LAVA, true, entity.x, entity.y, entity.z, 0.0, 0.0, 0.0)
    }
    private fun groundParticles() {
        val world: World? = MinecraftClient.getInstance().world
        val entity = this as ThrownEntity
        world!!.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, entity.x, entity.y, entity.z, 0.0, 0.0, 0.0)
        world!!.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, entity.x, entity.y, entity.z, 0.0, 0.0, 0.0)
        world!!.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, entity.x, entity.y, entity.z, 0.0, 0.0, 0.0)

        for (i in 1..10) {
            world!!.addParticle(ParticleTypes.LAVA, true, entity.x, entity.y, entity.z, 0.0, 0.0, 0.0)
        }
        entity.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 0.3f, 1.8f) // plays a sound for the ground contact

    }

}