package exploresurvival.game.world;

import exploresurvival.game.util.BlockPos;
import exploresurvival.game.world.render.Model;

public class Block {
    public Model model;
    public BlockPos pos;
    public String name;
    public AABB blockAABB;

    public Block(String name, BlockPos pos, Model model, AABB aabb) {
        this.name =name;
        this.pos = pos;
        this.model =model;
        this.blockAABB =aabb;
    }
}
