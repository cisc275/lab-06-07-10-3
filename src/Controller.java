import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

/**
 * Do not modify this file without permission from your TA.
 **/
public class Controller implements ActionListener, KeyListener{

	private Model model;
	private View view;
	Action drawAction;
	final int drawDelay = 30;
	
	public Controller(){
		
		view = new View();
		model = new Model(view.getWidth(), view.getHeight(), view.getImageWidth(), view.getImageHeight());
		
		view.b1.addActionListener(this);
		view.b1.addKeyListener(this);
	}
	
        //run the simulation
	public void start(){
		
		drawAction = new AbstractAction(){
    		public void actionPerformed(ActionEvent e){
    			if(model.getStartstop()) {
    				model.updateLocationAndDirection();
    				view.update(model.getX(), model.getY(), model.getDirect(), model.getCurrentbehavior());
    			}			
    		}
    	};
    	
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				Timer t = new Timer(drawDelay, drawAction);
				t.start();
			}
		});
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		model.setStartstop();
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_J) {
			model.setCurrentBehavior("Jump");
		}else if(keyCode == KeyEvent.VK_F){
			model.setCurrentBehavior("Fire");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
