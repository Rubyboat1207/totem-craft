package totemcraft.rubyboat.itemComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.Component;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.GlobalPos;

public record TeleportBackComponent(GlobalPos position) {
    public static final Codec<TeleportBackComponent> CODEC = RecordCodecBuilder.create((instance) -> instance
            .group(GlobalPos.CODEC.fieldOf("position")
                    .forGetter(TeleportBackComponent::position)).apply(instance, TeleportBackComponent::new));
    public static final PacketCodec<ByteBuf, TeleportBackComponent> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.codec(GlobalPos.CODEC), TeleportBackComponent::position, TeleportBackComponent::new);

}
