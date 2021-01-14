package finalproject;
import java.util.Random;

import javax.swing.*;

public class Player extends JLabel{
	
	protected int health;
	public int speed = 0;
	public boolean ontransformblock = false;
	
	public Player() {
		health = 1000;
	}
	
	public void resetPlayerState() {
		onBlock = false;
		besideBlock = "NONE";
	}
	
	public int setPlayerState(Blocker blocker, int err , int speedup, String command1p, String command2p, Player player) {
		if((blocker.getLocation().y - err <= this.getLocation().y + this.getHeight() &&
			blocker.getLocation().y + err >= this.getLocation().y + this.getHeight())&&
			blocker.getLocation().x < this.getLocation().x + this.getWidth() &&
			blocker.getLocation().x + blocker.getWidth() > this.getLocation().x) {
			onBlock = true;
			this.setLocation(this.getLocation().x, blocker.getLocation().y - speedup - this.getHeight());
			switch(blocker.get_Blocktype()) {
			case 0:
				if(health < 1000)
					health++;
				break;
			case 1:
				health -= 5;
				break;
			case 2:
				health -= 100;
				onBlock = false;
//				blocker.setVisible(false);
				blocker.fakeanimation = true;
				break;
			case 3:
				//this.setLocation(this.getLocation().x + err, blocker.getLocation().y - speedup- this.getHeight());
				break;
			case 4:
				//this.setLocation(this.getLocation().x - err, blocker.getLocation().y - speedup - this.getHeight());
				break;
			case 5:
				Random r1 = new Random();
				int go_through;
				if(40-5*err>=20) {
					go_through=r1.nextInt(45-2*err);
				}
				else {
					go_through=r1.nextInt(20);
				}
				if(go_through==0) {
					onBlock=false;
					blocker.setVisible(false);
				}
				break;
			}
			return blocker.get_Blocktype();
		}
		if(blocker.getLocation().y <= this.getLocation().y + this.getHeight() &&
				blocker.getLocation().y + blocker.getHeight() >= this.getLocation().y &&
				blocker.getLocation().x == this.getLocation().x + this.getWidth()) {
				besideBlock = "ON_LEFT";
		}
		else if(blocker.getLocation().y <= this.getLocation().y + this.getHeight() &&
				blocker.getLocation().y + blocker.getHeight() >= this.getLocation().y &&
				blocker.getLocation().x + blocker.getWidth() == this.getLocation().x) {
				besideBlock = "ON_RIGHT";
		}
		return 0;
	}
	
	public boolean getOnBlock() {
		return onBlock;
	}
	
	public String getBesideBlock() {
		return besideBlock;
	}
	public int getHealth() {
		return health;
	}
	
	private boolean onBlock = false;
	private String besideBlock = "NONE";
}
