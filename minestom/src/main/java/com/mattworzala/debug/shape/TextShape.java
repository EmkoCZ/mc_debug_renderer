package com.mattworzala.debug.shape;


import com.mattworzala.debug.Layer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

public record TextShape(
        @NotNull Point position,
        @NotNull String content,
        @NotNull float size,
        int argb
) implements Shape {

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
    public int id() {
        return 4;
    }

    @Override
    public void write(@NotNull NetworkBuffer buffer) {
        buffer.write(NetworkBuffer.DOUBLE, position.x());
        buffer.write(NetworkBuffer.DOUBLE, position.y());
        buffer.write(NetworkBuffer.DOUBLE, position.z());
        buffer.write(NetworkBuffer.STRING, content);
        buffer.write(NetworkBuffer.FLOAT, size);
        buffer.write(NetworkBuffer.INT, argb);
    }

    public static class Builder {

        private Point position;
        private String content;
        private float size;
        private int argb;

        public @NotNull TextShape.Builder position(@NotNull Point position) {
            this.position = position;
            return this;
        }

        public @NotNull TextShape.Builder content(@NotNull String content) {
            this.content = content;
            return this;
        }

        public @NotNull TextShape.Builder size(float size) {
            this.size = size;
            return this;
        }

        public @NotNull TextShape.Builder color(int color) {
            this.argb = color;
            return this;
        }

        public @NotNull Shape build() {
            Check.notNull(position, "position");
            Check.notNull(content, "content");
            Check.notNull(size, "size");
            return new TextShape(position, content, size, argb);
        }

    }
}
