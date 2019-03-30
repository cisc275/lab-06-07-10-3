import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

/**
 * View: Contains everything about graphics and images
 * Know size of world, which images to load etc
 *
 * has methods to
 * provide boundaries
 * use proper images for direction
 * load images for all direction (an image should only be loaded once!!! why?)
 **/
public class View extends JPanel{
	final int frameCount_Move = 10;//frameCount for movement
	final int frameCount_Jump = 8;
	final int frameCount_Fire = 4;
    private int picNum_Move = 0;
    private int picNum_Jump = 0;
    private int picNum_Fire = 0;

    BufferedImage[] pics;
    
    
    HashMap<Direction,BufferedImage[]> picsMovFile = new HashMap<>();
    HashMap<Direction,BufferedImage[]> picsFirFile = new HashMap<>();
    HashMap<Direction,BufferedImage[]> picsJmpFile = new HashMap<>();
    
    private final int width = 500;
    private final int height = 300;
    private final int imageWidth = 165;
    private final int imageHeight = 165;
    
    private Direction direct = Direction.SOUTHEAST;
    private int x;			
    private int y;
    private String currentBehavior = "Forward";
    // a frame count to count how many frame needed for jump or fire in painComponent();
    public JButton b1;
    JFrame frame;
    
    public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	
	public void paintComponent(Graphics g) {
		
		if(currentBehavior.equals("Forward")) {
			picNum_Move = (picNum_Move + 1) % frameCount_Move;
	    	//System.out.println(direct + " " + "(" + x + ", " + y +")");
	    	g.drawImage(picsMovFile.get(direct)[picNum_Move], x, y, Color.GRAY, this);
		}else if(currentBehavior.equals("Jump")) {
			picNum_Jump = (picNum_Jump + 1) % frameCount_Jump;
			g.drawImage(picsJmpFile.get(direct)[picNum_Jump], x, y, Color.GRAY, this);
		}else if(currentBehavior.equals("Fire")) {
			picNum_Fire = (picNum_Fire + 1) % frameCount_Fire;
			g.drawImage(picsFirFile.get(direct)[picNum_Fire], x, y, Color.GRAY, this);
		}
    	
	}
	
	public void update(int x, int y, Direction d, String currentBehavior) {
		
		this.x = x;
		this.y = y;
		this.direct = d;
		this.currentBehavior = currentBehavior;
		
		frame.repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    
    public View(){
    	
    	b1 = new JButton("Start/Stop");  
    	b1.setMnemonic(KeyEvent.VK_D);
    	b1.setBackground(Color.GRAY);
    	b1.setOpaque(true);
    	this.add(b1);
    	
    	HashMap<Direction,BufferedImage> orcMoves = createMoveImage();
    	for(Direction key: orcMoves.keySet()) {
    		BufferedImage img = orcMoves.get(key); //read image
    		pics = new BufferedImage[frameCount_Move];
    		for(int i = 0; i < frameCount_Move; i++) {
    			pics[i] = img.getSubimage(imageWidth*i, 0, imageWidth, imageHeight);
    		}
    		picsMovFile.put(key, pics);	
    	}
    	
    	HashMap<Direction,BufferedImage> orcJump = createJumpImage();
    	for(Direction key: orcJump.keySet()) {
    		BufferedImage img = orcJump.get(key); //read image
    		pics = new BufferedImage[frameCount_Jump]; //frameCount is different
    		for(int i = 0; i < frameCount_Jump; i++) {
    			pics[i] = img.getSubimage(imageWidth*i, 0, imageWidth, imageHeight);
    		}
    		picsJmpFile.put(key, pics);
    	}
    	
    	HashMap<Direction,BufferedImage> orcFire = createFireImage();
    	for(Direction key: orcFire.keySet()) {
    		BufferedImage img = orcFire.get(key); //read image
    		pics = new BufferedImage[frameCount_Fire]; //frameCount is different
    		for(int i = 0; i < frameCount_Fire; i++) {
    			pics[i] = img.getSubimage(imageWidth*i, 0, imageWidth, imageHeight);
    		}
    		picsFirFile.put(key, pics);
    		
    	}
    	
    	
    	
    	
	
    	frame = new JFrame();
    	
    	//frame.setContentPane(this);
    	
    	frame.getContentPane().add(this);
    	frame.setBackground(Color.gray);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(width, height);
    	frame.setVisible(true);	
    }  
    
    
    private HashMap<Direction,BufferedImage> createMoveImage(){
    	BufferedImage bufferedImage;
    	HashMap<Direction,BufferedImage> orcMoves = new HashMap<>();
    	
    	try {
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_southeast.png"));
    		orcMoves.put(Direction.SOUTHEAST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_east.png"));
    		orcMoves.put(Direction.EAST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_north.png"));
    		orcMoves.put(Direction.NORTH, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_northeast.png"));
    		orcMoves.put(Direction.NORTHEAST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_northwest.png"));
    		orcMoves.put(Direction.NORTHWEST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_south.png"));
    		orcMoves.put(Direction.SOUTH, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_southwest.png"));
    		orcMoves.put(Direction.SOUTHWEST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_forward_west.png"));
    		orcMoves.put(Direction.WEST, bufferedImage);
    		
    		return orcMoves;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
 
    	return null;
    }
    
    private HashMap<Direction,BufferedImage> createFireImage(){
    	BufferedImage bufferedImage;
    	HashMap<Direction,BufferedImage> orcFire = new HashMap<>();
    	
    	try {
    		bufferedImage = ImageIO.read(new File("images/orc/orc_fire_southeast.png"));
    		orcFire.put(Direction.SOUTHEAST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_fire_east.png"));
    		orcFire.put(Direction.EAST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_fire_north.png"));
    		orcFire.put(Direction.NORTH, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_fire_northeast.png"));
    		orcFire.put(Direction.NORTHEAST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_fire_northwest.png"));
    		orcFire.put(Direction.NORTHWEST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_fire_south.png"));
    		orcFire.put(Direction.SOUTH, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_fire_southwest.png"));
    		orcFire.put(Direction.SOUTHWEST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_fire_west.png"));
    		orcFire.put(Direction.WEST, bufferedImage);
    		return orcFire;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
 
    	return null;
    }
    
    private HashMap<Direction,BufferedImage> createJumpImage(){
    	BufferedImage bufferedImage;
    	HashMap<Direction,BufferedImage> orcJump = new HashMap<>();
    	
    	try {
    		bufferedImage = ImageIO.read(new File("images/orc/orc_jump_southeast.png"));
    		orcJump.put(Direction.SOUTHEAST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_jump_east.png"));
    		orcJump.put(Direction.EAST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_jump_north.png"));
    		orcJump.put(Direction.NORTH, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_jump_northeast.png"));
    		orcJump.put(Direction.NORTHEAST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_jump_northwest.png"));
    		orcJump.put(Direction.NORTHWEST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_jump_south.png"));
    		orcJump.put(Direction.SOUTH, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_jump_southwest.png"));
    		orcJump.put(Direction.SOUTHWEST, bufferedImage);
    		
    		bufferedImage = ImageIO.read(new File("images/orc/orc_jump_west.png"));
    		orcJump.put(Direction.WEST, bufferedImage);
    		return orcJump;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
 
    	return null;
    }
    
    
}