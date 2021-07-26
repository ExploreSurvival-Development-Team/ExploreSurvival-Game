package ExploreSurvival.Game.render;

import java.nio.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

public final class ShapeRenderer
{
    private final FloatBuffer buffer;
    private final float[] data;
    private int vertices;
    private float u;
    private float v;
    private float r;
    private float g;
    private float b;
    private boolean color;
    private boolean texture;
    private int vertexLength;
    private int length;
    private boolean noColor;
    public static ShapeRenderer instance;

    static {
        ShapeRenderer.instance = new ShapeRenderer();
    }

    public ShapeRenderer() {
        this.buffer = BufferUtils.createFloatBuffer(524288);
        this.data = new float[524288];
        this.vertices = 0;
        this.color = false;
        this.texture = false;
        this.vertexLength = 3;
        this.length = 0;
        this.noColor = false;
    }

    public final void end() {
        if (this.vertices > 0) {
            this.buffer.clear();
            this.buffer.put(this.data, 0, this.length);
            this.buffer.flip();
            if (this.texture && this.color) {
                GL11.glInterleavedArrays(10794, 0, this.buffer);
            }
            else if (this.texture) {
                GL11.glInterleavedArrays(10791, 0, this.buffer);
            }
            else if (this.color) {
                GL11.glInterleavedArrays(10788, 0, this.buffer);
            }
            else {
                GL11.glInterleavedArrays(10785, 0, this.buffer);
            }
            GL11.glEnableClientState(32884);
            if (this.texture) {
                GL11.glEnableClientState(32888);
            }
            if (this.color) {
                GL11.glEnableClientState(32886);
            }
            GL11.glDrawArrays(7, 0, this.vertices);
            GL11.glDisableClientState(32884);
            if (this.texture) {
                GL11.glDisableClientState(32888);
            }
            if (this.color) {
                GL11.glDisableClientState(32886);
            }
        }
        this.clear();
    }

    private void clear() {
        this.vertices = 0;
        this.buffer.clear();
        this.length = 0;
    }

    public final void begin() {
        this.clear();
        this.color = false;
        this.texture = false;
        this.noColor = false;
    }

    public final void color(final float var1, final float var2, final float var3) {
        if (!this.noColor) {
            if (!this.color) {
                this.vertexLength += 3;
            }
            this.color = true;
            this.r = var1;
            this.g = var2;
            this.b = var3;
        }
    }

    public final void vertexUV(final float var1, final float var2, final float var3, final float var4, final float var5) {
        if (!this.texture) {
            this.vertexLength += 2;
        }
        this.texture = true;
        this.u = var4;
        this.v = var5;
        this.vertex(var1, var2, var3);
    }

    public final void vertex(final float var1, final float var2, final float var3) {
        if (this.texture) {
            this.data[this.length++] = this.u;
            this.data[this.length++] = this.v;
        }
        if (this.color) {
            this.data[this.length++] = this.r;
            this.data[this.length++] = this.g;
            this.data[this.length++] = this.b;
        }
        this.data[this.length++] = var1;
        this.data[this.length++] = var2;
        this.data[this.length++] = var3;
        ++this.vertices;
        if (this.vertices % 4 == 0 && this.length >= 524288 - (this.vertexLength << 2)) {
            this.end();
        }
    }

    public final void color(int var1) {
        int var2 = var1 >> 16 & 0xFF;
        int var3 = var1 >> 8 & 0xFF;
        var1 &= 0xFF;
        final int var4 = var2;
        final int var5 = var3;
        var3 = var1;
        var2 = var5;
        var1 = var4;
        final byte var6 = (byte)var1;
        final byte var7 = (byte)var2;
        final byte var8 = (byte)var3;
        final byte var9 = var7;
        final byte var10 = var6;
        if (!this.noColor) {
            if (!this.color) {
                this.vertexLength += 3;
            }
            this.color = true;
            this.r = (var10 & 0xFF) / 255.0f;
            this.g = (var9 & 0xFF) / 255.0f;
            this.b = (var8 & 0xFF) / 255.0f;
        }
    }

    public final void noColor() {
        this.noColor = true;
    }

    public final void normal(final float var1, final float var2, final float var3) {
        GL11.glNormal3f(var1, var2, var3);
    }
    public void fillTexture(int tex, float[] imageWidthWH, float[] xyz0, float[] xyz1, int u0, int v0, int u1, int v1) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        this.begin();
        this.vertexUV(xyz0[0], xyz1[1], 0.0f, (float) u0 / imageWidthWH[0], (float) v1 / imageWidthWH[1]);
        this.vertexUV(xyz1[0], xyz1[1], 0.0f, (float) u1 / imageWidthWH[0], (float) v1 / imageWidthWH[1]);
        this.vertexUV(xyz1[0], xyz0[1], 0.0f, (float) u1 / imageWidthWH[0], (float) v0 / imageWidthWH[1]);
        this.vertexUV(xyz0[0], xyz0[1], 0.0f, (float) u0 / imageWidthWH[0], (float) v0 / imageWidthWH[1]);
        this.end();
    }
}
