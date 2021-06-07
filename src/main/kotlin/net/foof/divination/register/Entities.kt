package net.foof.divination.register

import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.foof.divination.Mod
import net.foof.divination.entity.projectile.SparkProjectile
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.registry.Registry

object Entities {

    val SPARK: EntityType<SparkProjectile> = Registry.register(
        Registry.ENTITY_TYPE,
        Mod.identifier("spark"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::SparkProjectile)
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build()
    )
}