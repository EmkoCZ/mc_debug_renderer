package com.mattworzala.debug.client.shape;

import com.mattworzala.debug.client.render.DebugRenderContext;
import com.mattworzala.debug.client.render.RenderLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public record TextShape(
        @NotNull Vec3d position,
        @NotNull String content,
        @NotNull float size,
        int argb
) implements Shape {

    public TextShape {

    }

    public TextShape(PacketByteBuf buffer) {
        this(
                new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                buffer.readString(),
                buffer.readFloat(),
                buffer.readInt()
        );
    }

    public int r() {
        return (argb >> 16) & 0xFF;
    }

    public int g() {
        return (argb >> 8) & 0xFF;
    }

    public int b() {
        return argb & 0xFF;
    }

    public int a() {
        return (argb >> 24) & 0xFF;
    }

    @Override
    public void render(@NotNull DebugRenderContext context) {
        DebugRenderer.drawString(null, null, content, position.x, position.y, position.z, argb, size, true, 0f, false);
    }
}
