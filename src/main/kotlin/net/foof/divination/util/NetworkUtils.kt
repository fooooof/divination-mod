package net.foof.divination.util

import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.foof.divination.Mod
import net.minecraft.entity.Entity
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket

// https://github.com/miyo6032/bosses-of-mass-destruction

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