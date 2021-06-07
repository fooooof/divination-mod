package net.foof.divination.item

import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.world.World


class BaseWand(settings: Settings?) : Item(settings) {
    override fun appendTooltip(
        itemStack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text?>,
        tooltipContext: TooltipContext?
    ) {

        // default white text
        tooltip.add(TranslatableText("item.divination.base_wand.tooltip"))

        // formatted red text
        tooltip.add(TranslatableText("item.divination.base_wand.tooltip_2").formatted(Formatting.RED))
    }
}