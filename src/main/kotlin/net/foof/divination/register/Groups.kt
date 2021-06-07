package net.foof.divination.register

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.foof.divination.MOD_ID
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

object Groups {
    val DIVINATION_TOOLS: ItemGroup = FabricItemGroupBuilder
        .create(Identifier(MOD_ID, "tools"))
        .icon { ItemStack(Items.BASE_WAND) }
        .build()
    val DIVINATION_MATERIALS: ItemGroup = FabricItemGroupBuilder
        .create(Identifier(MOD_ID, "materials"))
        .icon { ItemStack(Items.FREURHIL_INGOT) }
        .build()
}