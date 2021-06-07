package net.foof.divination.entity.projectile

import net.foof.divination.Mod
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.network.Packet
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import java.util.function.Predicate

abstract class BaseThrownItemEntity : ThrownItemEntity {
    private val collisionPredicate: Predicate<EntityHitResult>

    constructor(
        entityType: EntityType<out ThrownItemEntity>,
        d: Double,
        e: Double,
        f: Double,
        world: World
    ) : super(entityType, d, e, f, world) {
        collisionPredicate = Predicate { true }
    }

    constructor(
        entityType: EntityType<out ThrownItemEntity>, world: World?
    ) : super(entityType, world) {
        collisionPredicate = Predicate { true }
    }

    constructor(
        entityType: EntityType<out ThrownItemEntity>,
        livingEntity: LivingEntity,
        world: World,
        collisionPredicate: Predicate<EntityHitResult> = Predicate { true }
    ) : super(
        entityType,
        livingEntity,
        world,
    ) {
        this.collisionPredicate = collisionPredicate
    }

    final override fun tick() {
        super.tick()
        if (world.isClient) {
            clientTick()
        }
    }

    override fun createSpawnPacket(): Packet<*> {
        return Mod.networkUtils.createClientEntityPacket(this)
    }

    override fun onCollision(hitResult: HitResult) {
        if(!world.isClient) {
            super.onCollision(hitResult)
        }
    }

    final override fun onEntityHit(entityHitResult: EntityHitResult) {
        if(collisionPredicate.test(entityHitResult)) {
            entityHit(entityHitResult)
        }
    }

    abstract fun entityHit(entityHitResult: EntityHitResult)

    open fun clientTick() {

    }

    open fun addParticle(
        world: World,
        lastX: Double,
        x: Double,
        lastZ: Double,
        z: Double,
        y: Double,
        effect: ParticleEffect
    ) {
        world.addParticle(
            effect,
            MathHelper.lerp(world.random.nextDouble(), lastX, x),
            y,
            MathHelper.lerp(world.random.nextDouble(), lastZ, z),
            0.0,
            0.0,
            0.0
        )
    }

    override fun getDefaultItem(): Item = Items.SNOWBALL
}