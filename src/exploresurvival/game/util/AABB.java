package exploresurvival.game.util;

public class AABB {
	   private double epsilon = 0.0F;
	   public double x0;
	   public double y0;
	   public double z0;
	   public double x1;
	   public double y1;
	   public double z1;

	   public AABB(double x0, double y0, double z0, double x1, double y1, double z1) {
	      this.x0 = x0;
	      this.y0 = y0;
	      this.z0 = z0;
	      this.x1 = x1;
	      this.y1 = y1;
	      this.z1 = z1;
	   }

	   public AABB expand(double xa, double ya, double za) {
	      double _x0 = this.x0;
	      double _y0 = this.y0;
	      double _z0 = this.z0;
	      double _x1 = this.x1;
	      double _y1 = this.y1;
	      double _z1 = this.z1;
	      if(xa < 0.0F) {
	         _x0 += xa;
	      }

	      if(xa > 0.0F) {
	         _x1 += xa;
	      }

	      if(ya < 0.0F) {
	         _y0 += ya;
	      }

	      if(ya > 0.0F) {
	         _y1 += ya;
	      }

	      if(za < 0.0F) {
	         _z0 += za;
	      }

	      if(za > 0.0F) {
	         _z1 += za;
	      }

	      return new AABB(_x0, _y0, _z0, _x1, _y1, _z1);
	   }

	   public AABB grow(double xa, double ya, double za) {
	      double _x0 = this.x0 - xa;
	      double _y0 = this.y0 - ya;
	      double _z0 = this.z0 - za;
	      double _x1 = this.x1 + xa;
	      double _y1 = this.y1 + ya;
	      double _z1 = this.z1 + za;
	      return new AABB(_x0, _y0, _z0, _x1, _y1, _z1);
	   }

	   public AABB cloneMove(double xa, double ya, double za) {
	      return new AABB(this.x0 + za, this.y0 + ya, this.z0 + za, this.x1 + xa, this.y1 + ya, this.z1 + za);
	   }

	   public double clipXCollide(AABB c, double xa) {
	      if(c.y1 > this.y0 && c.y0 < this.y1) {
	         if(c.z1 > this.z0 && c.z0 < this.z1) {
	            if(xa > 0.0F && c.x1 <= this.x0) {
	               double max = this.x0 - c.x1 - this.epsilon;
	               if(max < xa) {
	                  xa = max;
	               }
	            }

	            if(xa < 0.0F && c.x0 >= this.x1) {
	               double max = this.x1 - c.x0 + this.epsilon;
	               if(max > xa) {
	                  xa = max;
	               }
	            }

	            return xa;
	         } else {
	            return xa;
	         }
	      } else {
	         return xa;
	      }
	   }

	   public double clipYCollide(AABB c, double ya) {
	      if(c.x1 > this.x0 && c.x0 < this.x1) {
	         if(c.z1 > this.z0 && c.z0 < this.z1) {
	            if(ya > 0.0F && c.y1 <= this.y0) {
	               double max = this.y0 - c.y1 - this.epsilon;
	               if(max < ya) {
	                  ya = max;
	               }
	            }

	            if(ya < 0.0F && c.y0 >= this.y1) {
	               double max = this.y1 - c.y0 + this.epsilon;
	               if(max > ya) {
	                  ya = max;
	               }
	            }

	            return ya;
	         } else {
	            return ya;
	         }
	      } else {
	         return ya;
	      }
	   }

	   public double clipZCollide(AABB c, double za) {
	      if(c.x1 > this.x0 && c.x0 < this.x1) {
	         if(c.y1 > this.y0 && c.y0 < this.y1) {
	            if(za > 0.0F && c.z1 <= this.z0) {
	               double max = this.z0 - c.z1 - this.epsilon;
	               if(max < za) {
	                  za = max;
	               }
	            }

	            if(za < 0.0F && c.z0 >= this.z1) {
	               double max = this.z1 - c.z0 + this.epsilon;
	               if(max > za) {
	                  za = max;
	               }
	            }

	            return za;
	         } else {
	            return za;
	         }
	      } else {
	         return za;
	      }
	   }

	   public boolean intersects(AABB c) {
	      return c.x1 > this.x0 && c.x0 < this.x1?(c.y1 > this.y0 && c.y0 < this.y1?c.z1 > this.z0 && c.z0 < this.z1:false):false;
	   }

	   public void move(double xa, double ya, double za) {
	      this.x0 += xa;
	      this.y0 += ya;
	      this.z0 += za;
	      this.x1 += xa;
	      this.y1 += ya;
	      this.z1 += za;
	   }
	}
