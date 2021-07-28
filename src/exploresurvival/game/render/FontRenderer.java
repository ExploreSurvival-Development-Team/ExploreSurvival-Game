package exploresurvival.game.render;

import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

public final class FontRenderer
{
    private final int[] widthmap;
    private int fontTexture;

    public FontRenderer(final String var2, final RenderEngine var3) {
        this.widthmap = new int[256];
        this.fontTexture = 0;
        BufferedImage var4;
        try {
            var4 = ImageIO.read(Objects.requireNonNull(RenderEngine.class.getResourceAsStream("/default.png")));
        }
        catch (IOException var5) {
            throw new RuntimeException(var5);
        }
        final int var6 = var4.getWidth();
        int var7 = var4.getHeight();
        final int[] var8 = new int[var6 * var7];
        var4.getRGB(0, 0, var6, var7, var8, 0, var6);
        for (int var9 = 0; var9 < 128; ++var9) {
            var7 = var9 % 16;
            final int var10 = var9 / 16;
            int var11 = 0;
            for (boolean var12 = false; var11 < 8 && !var12; ++var11) {
                final int var13 = (var7 << 3) + var11;
                var12 = true;
                for (int var14 = 0; var14 < 8 && var12; ++var14) {
                    final int var15 = ((var10 << 3) + var14) * var6;
                    if ((var8[var13 + var15] & 0xFF) > 128) {
                        var12 = false;
                    }
                }
            }
            if (var9 == 32) {
                var11 = 4;
            }
            this.widthmap[var9] = var11;
        }
        this.fontTexture = var3.getTexture(var2);
    }

    public final void render(final String var1, final int var2, final int var3, final int var4) {
        this.render(var1, var2 + 1, var3 + 1, var4, true);
        this.renderNoShadow(var1, var2, var3, var4);
    }

    public final void renderNoShadow(final String var1, final int var2, final int var3, final int var4) {
        this.render(var1, var2, var3, var4, false);
    }

    private void render(final String var1, final int var2, final int var3, int var4, final boolean var5) {
        if (var1 != null) {
            final char[] var6 = var1.toCharArray();
            if (var5) {
                var4 = (var4 & 0xFCFCFC) >> 2;
            }
            GL11.glBindTexture(3553, this.fontTexture);
            final ShapeRenderer var7 = ShapeRenderer.instance;
            ShapeRenderer.instance.begin();
            var7.color(var4);
            int var8 = 0;
            for (int var9 = 0; var9 < var6.length; ++var9) {
                if (var6[var9] == '&' && var6.length > var9 + 1) {
                    if ((var4 = "0123456789abcdef".indexOf(var6[var9 + 1])) < 0) {
                        var4 = 15;
                    }
                    int var10 = (var4 & 0x8) << 3;
                    int var11 = (var4 & 0x1) * 191 + var10;
                    int var12 = ((var4 & 0x2) >> 1) * 191 + var10;
                    var4 = ((var4 & 0x4) >> 2) * 191 + var10;
                    var4 = (var4 << 16 | var12 << 8 | var11);
                    var9 += 2;
                    if (var5) {
                        var4 = (var4 & 0xFCFCFC) >> 2;
                    }
                    var7.color(var4);
                }
                var4 = var6[var9] % 16 << 3;
                int var10 = var6[var9] / 16 << 3;
                final float var13 = 7.99f;
                var7.vertexUV((float)(var2 + var8), var3 + var13, 0.0f, var4 / 128.0f, (var10 + var13) / 128.0f);
                var7.vertexUV(var2 + var8 + var13, var3 + var13, 0.0f, (var4 + var13) / 128.0f, (var10 + var13) / 128.0f);
                var7.vertexUV(var2 + var8 + var13, (float)var3, 0.0f, (var4 + var13) / 128.0f, var10 / 128.0f);
                var7.vertexUV((float)(var2 + var8), (float)var3, 0.0f, var4 / 128.0f, var10 / 128.0f);
                var8 += this.widthmap[var6[var9]];
            }
            var7.end();
        }
    }

    public final int getWidth(final String var1) {
        if (var1 == null) {
            return 0;
        }
        final char[] var2 = var1.toCharArray();
        int var3 = 0;
        for (int var4 = 0; var4 < var2.length; ++var4) {
            if (var2[var4] == '&') {
                ++var4;
            }
            else {
                var3 += this.widthmap[var2[var4]];
            }
        }
        return var3;
    }

    public static String stripColor(final String var0) {
        final char[] var = var0.toCharArray();
        String var2 = "";
        for (int var3 = 0; var3 < var.length; ++var3) {
            if (var[var3] == '&') {
                ++var3;
            }
            else {
                var2 = var2 + var[var3];
            }
        }
        return var2;
    }
}