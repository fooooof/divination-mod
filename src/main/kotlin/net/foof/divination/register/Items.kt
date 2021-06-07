package net.foof.divination.register

import net.foof.divination.MOD_ID
import net.foof.divination.item.WandOfSparking
import net.foof.divination.register.Groups.DIVINATION_TOOLS
import net.foof.divination.register.Groups.DIVINATION_MATERIALS
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

object Items {
    val BASE_WAND = Item(Item.Settings().group(DIVINATION_TOOLS).rarity(Rarity.UNCOMMON))
    val WAND_OF_SPARKING = WandOfSparking(Item.Settings().group(DIVINATION_TOOLS).rarity(Rarity.UNCOMMON))
    val FREURHIL_INGOT = Item(Item.Settings().group(DIVINATION_MATERIALS).rarity(Rarity.UNCOMMON))
    val FREURHIL_NUGGET = Item(Item.Settings().group(DIVINATION_MATERIALS).rarity(Rarity.UNCOMMON))
    val RAW_FREURHIL = Item(Item.Settings().group(DIVINATION_MATERIALS).rarity(Rarity.UNCOMMON))

    fun register() {
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "wand_of_sparking"), WAND_OF_SPARKING)
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "base_wand"), BASE_WAND)
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "freurhil_ingot"), FREURHIL_INGOT)
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "freurhil_nugget"), FREURHIL_NUGGET)
        Registry.register(Registry.ITEM, Identifier(MOD_ID, "raw_freurhil"), RAW_FREURHIL)
    }
}