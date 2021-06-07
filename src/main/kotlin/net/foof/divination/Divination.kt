package net.foof.divination

import net.foof.divination.register.Items
import net.foof.divination.util.NetworkUtils
import net.minecraft.util.Identifier

const val MOD_ID = "divination"

object Mod {
    val networkUtils = NetworkUtils()
    fun identifier(path: String) = Identifier(MOD_ID, path)
}
@Suppress("unused")
fun init() {
    Items.register()
}
