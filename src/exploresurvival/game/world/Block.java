package exploresurvival.game.world;

import exploresurvival.game.render.Model;
import exploresurvival.game.util.AABB;
import exploresurvival.game.util.BlockPos;

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
