package net.foof.divination.item

import net.foof.divination.entity.projectile.SparkProjectile
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World


class WandOfSparking(settings: Settings?) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand) // creates a new ItemStack instance of the user's itemStack in-hand
        world.playSound(
            null,
            user.x,
            user.y,
            user.z,
            SoundEvents.ENTITY_BAT_TAKEOFF,
            SoundCategory.NEUTRAL,
            0.3f,
            1f
        ) // plays a globalSoundEvent
        world.playSound(
            null,
            user.x,
            user.y,
            user.z,
            SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL,
            SoundCategory.NEUTRAL,
            0.3f,
            1.4f
        ) // plays a globalSoundEvent
		user.itemCooldownManager.set(this, 5);
		if (!world.isClient) {
            val snowballEntity = SparkProjectile(world, user)
            snowballEntity.setItem(itemStack)
            snowballEntity.setProperties(user, user.pitch, user.yaw, 0.0f, 1.5f, 0f)
            world.spawnEntity(snowballEntity) // spawns entity
        }
        return TypedActionResult.success(itemStack, world.isClient())
    }
}