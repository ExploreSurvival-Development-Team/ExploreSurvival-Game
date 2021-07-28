package exploresurvival.game.util;

public class BlockPos {
	public final long x,y,z;
	public byte facing;
	public boolean[] enableFacingList;
	public static final byte FACING_NORTH=1;
	public static final byte FACING_SOUTH=2;
	public static final byte FACING_WEST=3;
	public static final byte FACING_EAST=4;
	public static final byte FACING_UP=5;
	public static final byte FACING_DOWN=6;
	public BlockPos(long x,long y,long z,byte facing,boolean[] enableFacing) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.enableFacingList=enableFacing;
		setFacing(facing);
	}
	public void setFacing(byte facing) {
		if(enableFacingList[facing-1]) {
			this.facing=facing;
		} else if((facing==1||facing==3||facing==5)&&enableFacingList[facing+1]) {
			this.facing=(byte) (facing+1);
		} else if((facing==2|facing==4||facing==6)&&enableFacingList[facing-1]) {
			this.facing=(byte) (facing-1);
		} else {
			this.facing=1;
		}
	}
}
