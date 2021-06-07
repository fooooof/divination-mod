package net.foof.divination.register

//import net.foof.divination.entity.projectile.SparkProjectile
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.foof.divination.MOD_ID
import net.foof.divination.Mod
import net.foof.divination.entity.projectile.SparkProjectile
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.Identifier
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import java.util.function.Predicate

// MOST OF THIS CODE IS FROM BOSSES OF MASS DESTRUCTION

object Entities {

    class ExemptEntities(private val exemptEntities: List<EntityType<*>>): Predicate<EntityHitResult> {
        override fun test(t: EntityHitResult): Boolean =
            !exemptEntities.contains(t.entity.type)
    }

    val SPARK: EntityType<SparkProjectile> = Registry.register(
        Registry.ENTITY_TYPE,
        Mod.identifier("spark"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::SparkProjectile)
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build()
    )


//    fun clientInit() {
//        val sparkTexture = Mod.identifier("textures/entity/spark.png")
//        val sparkRenderLayer = RenderLayer.getEntityCutoutNoCull(sparkTexture)
//        EntityRendererRegistry.INSTANCE.register(SPARK) { entityRenderDispatcher, _ ->
//            render.SimpleEntityRenderer(
//                entityRenderDispatcher,
//                render.CompositeRenderer(
//                    render.BillboardRenderer(entityRenderDispatcher, sparkRenderLayer) { 0.5f },
//                    render.ConditionalRenderer(
//                        WeakHashPredicate<SparkProjectile> { render.FrameLimiter(20f, pauseSecondTimer)::canDoFrame },
//                        LerpedPosRenderer {
//                            ParticleFactories.soulFlame().build(it.add(RandomUtils.randVec().multiply(0.25)))
//                        })
//                ),
//                { sparkTexture },
//                render.FullRenderLight()
//            )
//        }
//    }
}
object render {
    class SimpleEntityRenderer<T : Entity>(
        renderManager: EntityRenderDispatcher,
        private val renderer: IRenderer<T>,
        private val textureProvider: ITextureProvider<T>,
        private val brightness: IRenderLight<T>? = null,
    ) : EntityRenderer<T>(renderManager) {
        override fun render(
            entity: T,
            yaw: Float,
            tickDelta: Float,
            matrices: MatrixStack,
            vertexConsumers: VertexConsumerProvider,
            light: Int,
        ) {
            renderer.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
            super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
        }

        override fun getTexture(entity: T): Identifier = textureProvider.getTexture(entity)

        override fun getBlockLight(entity: T, blockPos: BlockPos): Int =
            brightness?.getBlockLight(entity, blockPos) ?: super.getBlockLight(entity, blockPos)
    }

    interface IRenderer<T : Entity> {
        fun render(
            entity: T,
            yaw: Float,
            partialTicks: Float,
            matrices: MatrixStack,
            vertexConsumers: VertexConsumerProvider,
            light: Int,
        )
    }

    interface IRenderLight<T : Entity> {
        fun getBlockLight(entity: T, blockPos: BlockPos): Int
    }

    fun interface ITextureProvider <T> {
        fun getTexture(entity: T): Identifier
    }

//    class BillboardRenderer<T : Entity>(
//        private val dispatcher: EntityRenderDispatcher,
//        private val renderLayer: RenderLayer,
//        private val scale: () -> Float,
//    ) : IRenderer<T> {
//        override fun render(
//            entity: T,
//            yaw: Float,
//            partialTicks: Float,
//            matrices: MatrixStack,
//            vertexConsumers: VertexConsumerProvider,
//            light: Int,
//        ) {
//            val scale = scale()
//            matrices.push()
//            matrices.scale(scale, scale, scale)
//            VanillaCopies.renderBillboard(matrices, vertexConsumers, light, dispatcher, renderLayer)
//            matrices.pop()
//        }
//    }


    class CompositeRenderer<T : Entity>(vararg renderers: IRenderer<T>) : IRenderer<T> {
        var rendererList = renderers.toList()

        override fun render(
            entity: T,
            yaw: Float,
            partialTicks: Float,
            matrices: MatrixStack,
            vertexConsumers: VertexConsumerProvider,
            light: Int,
        ) {
            rendererList.forEach { it.render(entity, yaw, partialTicks, matrices, vertexConsumers, light) }
        }
    }

    class ConditionalRenderer<T : Entity>(private val predicate: Predicate<T>, private val renderer: IRenderer<T>) : IRenderer<T> {
        override fun render(
            entity: T,
            yaw: Float,
            partialTicks: Float,
            matrices: MatrixStack,
            vertexConsumers: VertexConsumerProvider,
            light: Int,
        ) {
            if (predicate.test(entity)) {
                renderer.render(entity, yaw, partialTicks, matrices, vertexConsumers, light)
            }
        }
    }

    class FullRenderLight<T : Entity> : IRenderLight<T> {
        override fun getBlockLight(entity: T, blockPos: BlockPos): Int = 15
    }

//    class FrameLimiter(framesPerUnit: Float, private val timer: IAnimationTimer) {
//        private val minimumFrameDelta = 1 / framesPerUnit
//        var previousTime = 0.0
//
//        fun canDoFrame(): Boolean {
//            val currentTick = timer.getCurrentTick()
//            val frameDelta = currentTick - previousTime
//            if (frameDelta >= minimumFrameDelta) {
//                previousTime = currentTick
//                return true
//            }
//            return false
//        }
//    }
}