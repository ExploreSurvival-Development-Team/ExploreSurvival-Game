package exploresurvival.game.render;
import java.util.ArrayList;
import java.util.Iterator;

public class Model {
    public ArrayList<ModelSurface> faces;

    public Model(ModelSurface[] faces) {
        this.faces = new ArrayList<ModelSurface>();
        for (int i = 0; i < faces.length; i++) {
            this.faces.add(faces[i]);
        }
    }// define model

    public Model(int textrue) {
    	this.faces = new ArrayList<ModelSurface>();
    	byte[] points = new byte[]{0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0}; // left (3)
        this.faces.add(new ModelSurface(points, textrue, 3));
        points = new byte[]{0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1}; // right (4)
        this.faces.add(new ModelSurface(points, textrue, 4));
        points = new byte[]{0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}; // front (1)
        this.faces.add(new ModelSurface(points, textrue, 1));
        points = new byte[]{1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1};// back(2)
        this.faces.add(new ModelSurface(points, textrue, 2));
        points = new byte[]{0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0};// top(5)
        this.faces.add(new ModelSurface(points, textrue, 2));
        points = new byte[]{0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0};// down (6)
        this.faces.add(new ModelSurface(points, textrue, 6));
    }

    public ModelSurface getFace(int face) {
        ModelSurface result = null;
        for(ModelSurface test:faces)
            if (test.getFace()==face){
                result = test;
            }
        return result;
    }
    public void setTextrue(int textrue,int face){
        this.getFace(face).setTextrue(textrue);
    }
    public int getTextrue(int face){
        return this.getFace(face).getTexture();
    }
}
