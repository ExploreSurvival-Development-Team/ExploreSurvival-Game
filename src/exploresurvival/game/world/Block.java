package exploresurvival.game.world;

import exploresurvival.game.render.Model;
import exploresurvival.game.util.AABB;
import exploresurvival.game.util.BlockPos;

public class Block {
    private Model model;
    private String name;
    public final byte blockID;
    public static final Block[] blockList=new Block[256];

    public Block(byte id,String name,Model model) {
        this.name =name;
        this.model =model;
        blockID=id;
        blockList[id&0xFF]=this;
    }
    public AABB getBlockAABB(BlockPos pos) {
    	return new AABB(pos.x,pos.y,pos.z,pos.x+1,pos.y+1,pos.z+1);
    }
    public Model getModel() {
    	return model;
    }
    public String getName() {
    	return name;
    }
}
