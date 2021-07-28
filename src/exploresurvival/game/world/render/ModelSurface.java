package exploresurvival.game.world.render;

public class ModelSurface {

	private int face;
	private int texture;
	private int[] bounds;

	public ModelSurface(int[] bounds, int textrue, int face) {
		this.setFace(face);
		this.texture=textrue;
		this.bounds=bounds;
		
	}

	public void setTextrue(int textrue) {
		// TODO Auto-generated method stub
		
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
