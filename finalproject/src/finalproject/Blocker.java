package finalproject;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Blocker extends JLabel{
	
	protected int blocktype;
	public BufferedImage fake;
	public Blocker() {};
	boolean fakeanimation = false;
	int speed = 10;
	
	public int animationframe = 0;
	
	public Blocker(int x,int y,int width, int height,int Blocktype) {
		this.setBounds(x,y,width,height);
		this.blocktype = Blocktype;
		if(blocktype > 5) {
			blocktype = 0;
		}
		ImageIcon icon;
		switch (Blocktype) {
		case 0:
			icon = new ImageIcon("./src/0.png");
			break;
		case 1:
			icon = new ImageIcon("./src/1.png");
			break;
		case 2:
			icon = new ImageIcon("./src/2.png");
			break;
		case 3:
			//icon = new ImageIcon("./src/3.png");
			try {
				fake = ImageIO.read(new File("./src/conveyor_right98x64.png"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			icon = new ImageIcon(fake.getSubimage(0, 0, 98, 16));
			break;
		case 4:
			//icon = new ImageIcon("./src/4.png");
			try {
				fake = ImageIO.read(new File("./src/conveyor_left98x64.png"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			icon = new ImageIcon(fake.getSubimage(0, 0, 96, 16));
			break;
		case 5:
			icon = new ImageIcon("");
			this.setOpaque(true);
			this.setBackground(Color.red);
			break;
		default:
			icon = new ImageIcon("./src/0.png");
			break;
		}
		Image transformImage = icon.getImage();
		icon = new ImageIcon(transformImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));	
		this.setIcon(icon);
	}
	 
	
	public void changeType(int x, int Blocktype) {
		this.setBounds(x,this.getLocation().y,this.getWidth(),this.getHeight());
		this.blocktype = Blocktype;
		if(blocktype > 5) {
			blocktype = 0;
		}
		this.setOpaque(false);
		ImageIcon icon;
		switch (Blocktype) {
		case 0:
			icon = new ImageIcon("./src/0.png");
			break;
		case 1:
			icon = new ImageIcon("./src/1.png");
			break;
		case 2:
			icon = new ImageIcon("./src/2.png");
			break;
		case 3:
			icon = new ImageIcon("./src/3.png");
			break;
		case 4:
			icon = new ImageIcon("./src/4.png");
			break;
		case 5:
			icon = new ImageIcon("");
			this.setOpaque(true);
			this.setBackground(Color.red);
			break;
		default:
			icon = new ImageIcon("./src/0.png");
			break;
		}
		Image transformImage = icon.getImage();
		icon = new ImageIcon(transformImage.getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH));	
		this.setIcon(icon);
	}
	
	
	public int get_Blocktype() {
		return blocktype;
	}
	
	
	
} 
