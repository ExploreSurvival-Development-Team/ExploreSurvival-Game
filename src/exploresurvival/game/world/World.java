package exploresurvival.game.world;

import java.util.ArrayList;

public class World {
	private ArrayList<Chunk> loadedChunks;
	public Chunk getChunk(long x,long z) {
		for(Chunk c:loadedChunks) {
			if(c.x==x&&c.z==z) {
				return c;
			}
		}
		return loadChunk(x,z);
	}
	private Chunk loadChunk(long x, long z) {
		Chunk c=genChunk(x,z);
		loadedChunks.add(c);
		return c;
	}
	private Chunk genChunk(long x, long z) {
		byte[] chunk=new byte[Chunk.CHUNK_WIDTH*Chunk.CHUNK_WIDTH*Chunk.CHUNK_HEIGHT];
		for(int i=0;i<Chunk.CHUNK_WIDTH;i++) for(int j=0;j<Chunk.CHUNK_WIDTH;j++) for(int k=0;k<20;k++) {
			chunk[i*Chunk.CHUNK_WIDTH+j*Chunk.CHUNK_WIDTH+k]=1;
		}
		return new Chunk(x,z,chunk);
	}
	public int getBlock(long x,long y,long z) {
		return getChunk(x/Chunk.CHUNK_WIDTH,z/Chunk.CHUNK_WIDTH).getBlock((int)x%Chunk.CHUNK_WIDTH, (int)y, (int)z%Chunk.CHUNK_WIDTH);
	}
	public boolean setBlock(long x,long y,long z,byte id) {
		return getChunk(x/Chunk.CHUNK_WIDTH,z/Chunk.CHUNK_WIDTH).setBlock((int)x%Chunk.CHUNK_WIDTH, (int)y, (int)z%Chunk.CHUNK_WIDTH,id);
	}
}
