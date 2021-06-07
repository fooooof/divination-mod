package net.foof.divination.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.world.World

class MagmaStone(settings: Settings?) : Item(settings) {
    override fun appendTooltip(
        itemStack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text?>,
        tooltipContext: TooltipContext?
    ) {

        tooltip.add(TranslatableText("item.divination.magma_stone.tooltip").formatted(Formatting.GRAY))

        tooltip.add(TranslatableText("item.divination.magma_stone.tooltip_2").formatted(Formatting.YELLOW))
    }
}