package net.foof.divination.util

import io.netty.buffer.Unpooled
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.foof.divination.Mod
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.Vec3d

// MOST OF THIS CODE IS FROM BOSSES OF MASS DESTRUCTION
class NetworkUtils {

    companion object {
        private val spawnEntityPacketId = Mod.identifier("spawn_entity")
    }

    private fun packSpawnClientEntity(packet: EntitySpawnS2CPacket): PacketByteBuf {
        val packetData = PacketByteBuf(Unpooled.buffer())
        packet.write(packetData)
        return packetData
    }

    fun createClientEntityPacket(entity: Entity): Packet<*> {
        return ServerPlayNetworking.createS2CPacket(spawnEntityPacketId, packSpawnClientEntity(
            EntitySpawnS2CPacket(entity)
        ))
    }

}