package exploresurvival.game.render;

import java.util.Arrays;

public class ModelSurface {

	private int face;
	private int texture;
	private byte[] x,y,z;

	public ModelSurface(byte[] points, int textrue, int face) {
		this.setFace(face);
		this.texture=textrue;
		x=getPoint(points,0);
		y=getPoint(points,1);
		z=getPoint(points,2);
		Arrays.sort(x);
		Arrays.sort(y);
		Arrays.sort(z);
		
	}
	private byte[] getPoint(byte[] points,int axis) {
		byte[] result=new byte[points.length/3];
		for(int i=0;i<points.length/3;i++) {
			result[i]=points[i*3+axis];
		}
		return result;
	}

	public void setTextrue(int texture) {
		this.texture=texture;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	public int getTexture() {
		// TODO Auto-generated method stub
		return texture;
	}
}
