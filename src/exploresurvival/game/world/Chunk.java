package exploresurvival.game.world;

import java.util.Random;

import exploresurvival.game.util.BlockPos;

public class Chunk {
	public static final int CHUNK_WIDTH=32;
	public static final int CHUNK_HEIGHT=256;
	private byte[][][] blocks=new byte[CHUNK_WIDTH][CHUNK_WIDTH][CHUNK_HEIGHT];
	public final long x,z;
	public final World worldObj;
	public Chunk(long x,long z,byte[] blocks, World worldObj) {
		this.x=x;
		this.z=z;
		for(int i=0;i<CHUNK_WIDTH;i++) for(int j=0;j<CHUNK_WIDTH;j++) for(int k=0;k<CHUNK_HEIGHT;k++) {
			this.blocks[i][j][k]=blocks[i*CHUNK_WIDTH+j*CHUNK_WIDTH+k];
		}
		this.worldObj = worldObj;
	}
	public int getBlock(int x,int y,int z) {
		if(x<0||y<0||z<0||x>=CHUNK_WIDTH||y<=CHUNK_HEIGHT||z<=CHUNK_WIDTH) return 0;
		return blocks[x][z][y]&0xFF;
	}
	public boolean setBlock(int x,int y,int z,byte id) {
		if(x<0||y<0||z<0||x>=CHUNK_WIDTH||y<=CHUNK_HEIGHT||z<=CHUNK_WIDTH) return false;
		blocks[x][z][y]=id;
		return true;
	}
	public void tick() {
		Random rand=worldObj.getWorldRandom();
		for(int i=0;i<3;i++) {
			int x=rand.nextInt(CHUNK_WIDTH);
			int y=rand.nextInt(CHUNK_HEIGHT);
			int z=rand.nextInt(CHUNK_WIDTH);
			Block block=Block.blockList[getBlock(x,y,z)];
			if(block!=null) {
				block.onRandomTick(new BlockPos(this.x*CHUNK_WIDTH+x,y,this.z*CHUNK_WIDTH+z));
			}
		}
	}
}
