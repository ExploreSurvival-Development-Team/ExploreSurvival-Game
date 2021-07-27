package exploresurvival.game.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

public class RenderEngine {
	private Hashtable<String,Integer> textures=new Hashtable<String,Integer>();
	private ByteBuffer imageData = ByteBuffer.allocateDirect(0xFFFFFF);
	private IntBuffer singleIntBuffer = ByteBuffer.allocateDirect(Integer.BYTES).asIntBuffer();
	protected float imgZ;
	public void setupTexture(BufferedImage image,int id) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		int[] intbuffer=new int[image.getWidth()*image.getHeight()];
		byte[] bytebuffer=new byte[image.getWidth()*image.getHeight()*4];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), intbuffer, 0, image.getWidth());
		for(int i=0;i<intbuffer.length;i++) {
			/*bytebuffer[i*4+1]=(byte) (intbuffer[i]>> 24 & 255);
			bytebuffer[i*4+2]=(byte) (intbuffer[i]>> 16 & 255);
			bytebuffer[i*4+3]=(byte) (intbuffer[i]>> 8  & 255);
			bytebuffer[i*4  ]=(byte) (intbuffer[i]      & 255);*/
			int var8 = intbuffer[i] >> 24 & 255;
        	int var9 = intbuffer[i] >> 16 & 255;
        	int var10 = intbuffer[i] >> 8 & 255;
        	int var11 = intbuffer[i] & 255;
        	bytebuffer[i * 4 + 0] = (byte)var9;
        	bytebuffer[i * 4 + 1] = (byte)var10;
        	bytebuffer[i * 4 + 2] = (byte)var11;
            bytebuffer[i * 4 + 3] = (byte)var8;
		}
		imageData.clear();
		imageData.put(bytebuffer);
		imageData.position(0).limit(bytebuffer.length);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
	}
	public int allocateAndSetupTexture(BufferedImage image) {
		singleIntBuffer.clear();
		GL11.glGenTextures(singleIntBuffer);
		int id=singleIntBuffer.get(0);
		setupTexture(image,id);
		return id;
	}
	public int getTexture(String file) {
		if(textures.containsKey(file)) {
			return textures.get(file);
		}
		InputStream stream=RenderEngine.class.getResourceAsStream(file);
		int id;
		try {
			id = allocateAndSetupTexture(ImageIO.read(stream));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		textures.put(file, id);
		return id;
	}
	public void drawImage(int var1, int var2, int var3, int var4, int var5, int var6) {
	      ShapeRenderer var9 = ShapeRenderer.instance;
	      ShapeRenderer.instance.begin();
	      var9.vertexUV((float)var1, (float)(var2 + var6), this.imgZ, (float)var3 * 0.00390625F, (float)(var4 + var6) * 0.00390625F);
	      var9.vertexUV((float)(var1 + var5), (float)(var2 + var6), this.imgZ, (float)(var3 + var5) * 0.00390625F, (float)(var4 + var6) * 0.00390625F);
	      var9.vertexUV((float)(var1 + var5), (float)var2, this.imgZ, (float)(var3 + var5) * 0.00390625F, (float)var4 * 0.00390625F);
	      var9.vertexUV((float)var1, (float)var2, this.imgZ, (float)var3 * 0.00390625F, (float)var4 * 0.00390625F);
	      var9.end();
	}
}
