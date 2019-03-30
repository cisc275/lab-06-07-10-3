import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Model: Contains all the state and logic
 * Does not contain anything about images or graphics, must ask view for that
 *
 * has methods to
 *  detect collision with boundaries
 * decide next direction
 * provide direction
 * provide location
 **/
public class Model{
	private int x = 0;
    private int y = 0;
    private final int xIncr = 8;
    private final int yIncr = 2;

    private Direction direct;
	private int width;
    private int height;
    private int imageWidth;
    private int imageHeight;
    
    boolean initial = true;
    Direction[] hitNorth = {Direction.SOUTHWEST, Direction.SOUTHEAST, Direction.SOUTH};
    Direction[] hitSouth = {Direction.NORTHWEST, Direction.NORTHEAST, Direction.NORTH};
    Direction[] hitEast = {Direction.NORTHWEST, Direction.SOUTHWEST, Direction.WEST};
    Direction[] hitWest = {Direction.NORTHEAST, Direction.SOUTHEAST, Direction.EAST};
    Random r = new Random();
    private boolean startstop = true;
    
    private String currentBehavior = "Forward";
    
    private int jumpCounter = 0;
    private int fireCounter = 0; 
    
    
    
    public Model(int width, int height, int imageWidth, int imageHeight) {
    	
    	
		this.width = width;
		this.height = height;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}
    
    public void setCurrentBehavior(String currentBehavior) {
    	this.currentBehavior = currentBehavior;
    }
    
    public String getCurrentbehavior() {
    	return currentBehavior;
    }
    
    public boolean getStartstop() {
		return startstop;
	}


	public void setStartstop() {
		startstop = !startstop;
	}


	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public Direction getDirect() {
		return direct;
	}
	
	public void updateLocationAndDirection() {
		
		if(currentBehavior.equals("Forward") || currentBehavior.equals("Jump")) {
			
			if(initial) {
	    		x+=xIncr;
	    		y+=yIncr;
	    		direct = Direction.SOUTHEAST;
	    		initial = false;
	    		return;
	    	}
		
			if(x < (width-imageWidth) && (x > 0) && (y < (height - imageWidth)) &&  (y > 0)) {
	
				switch(direct) {
				case NORTHWEST: 
					x-=xIncr;
					y-=yIncr;
					break;
				case SOUTHWEST:
					x-=xIncr;
					y+=yIncr;
					break;
				case NORTHEAST:
					x+=xIncr;
					y-=yIncr;
					break;
				case SOUTHEAST:
					x+=xIncr;
					y+=yIncr;
					break;
				case SOUTH:
					y+=(yIncr+2);
					break;
				case NORTH:
					y-=(yIncr+2);
					break;
				case WEST:
					x-=xIncr;
					break;
				case EAST:
					x+=xIncr;
					break;
					
				}
			}else if(x >= (width-imageWidth)) {
	    		direct = hitEast[r.nextInt(hitEast.length)];
	    		switch(direct) {
		    		case NORTHWEST: 
						x-=xIncr;
						y-=yIncr;
						break;
					case SOUTHWEST:
						x-=xIncr;
						y+=yIncr;
						break;
					case WEST:
						x-=xIncr;
						break;
	    		}
	    	}else if(x <= 0 ) {
	    		direct = hitWest[r.nextInt(hitWest.length)];
	    		switch(direct) {
					case NORTHEAST:
						x+=xIncr;
						y-=yIncr;
						break;
					case SOUTHEAST:
						x+=xIncr;
						y+=yIncr;
						break;
					case EAST:
						x+=xIncr;
						break;
	    		}
	    	}else if(y <= 0) {
	    		direct = hitNorth[r.nextInt(hitNorth.length)];
	    		switch(direct) {
					case SOUTHWEST:
						x-=xIncr;
						y+=yIncr;
						break;
					case SOUTHEAST:
						x+=xIncr;
						y+=yIncr;
						break;
					case SOUTH:
						y+=(yIncr+2);
						break;
	    		}
	    	}else if(y >= (height - imageWidth)) {
	    		direct = hitSouth[r.nextInt(hitSouth.length)];
	    		switch(direct) {
	    		case NORTHWEST: 
					x-=xIncr;
					y-=yIncr;
					break;
				case NORTHEAST:
					x+=xIncr;
					y-=yIncr;
					break;
				case NORTH:
					y-=(yIncr+2);
					break;
	    		}
	    	}
			if(currentBehavior.equals("Jump")) {
				jumpCounter++;
				if(jumpCounter == 8) {
					currentBehavior = "Forward";
					jumpCounter = 0;
				}
			}
		}
		
		if(currentBehavior.equals("Fire")) {
			fireCounter++;
			if(fireCounter == 5) {
				currentBehavior = "Forward";
				fireCounter = 0;
			}
		}
	}

	
}