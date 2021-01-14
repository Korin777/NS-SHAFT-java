package finalproject;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.KeyListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MyFrame extends JFrame implements KeyListener{

	
	private boolean start = false, stop = false, isTwoPlayer = false;
	JButton startButton, start2PButton,leaveButton,pauseButton,resumeButton;
	
	public static void main(String[] args) throws InterruptedException {
		
		MyFrame game = new MyFrame();
		int speedUp = 1, count = 0;
		
		JLabel healthBar_1p = new JLabel();
		JLabel healthBar_2p = new JLabel();
		JLabel score = new JLabel();
		Random r1 = new Random(); 
		healthBar_1p.setOpaque(true);
		healthBar_1p.setBackground(Color.BLUE);
		healthBar_2p.setOpaque(true);
		healthBar_2p.setBackground(Color.GREEN);
		
		JLabel hpJLabel = new JLabel("1P");
		hpJLabel.setBounds(620,461,100,100);
		hpJLabel.setFont(new java.awt.Font("Dialog", 1, 18));
		game.add(hpJLabel);
		
		JLabel hpJLabel2 = new JLabel("2P");
		hpJLabel2.setBounds(620,511,100,100);
		hpJLabel2.setFont(new java.awt.Font("Dialog", 1, 18));
		game.add(hpJLabel2);
		
		
		
		score.setBounds(645, 50, 120, 40);
		game.add(healthBar_1p);
		game.add(healthBar_2p);
		game.add(score);
		score.setText("LV = " + speedUp);
		score.setFont(new java.awt.Font("Dialog", 1, 30));
		

		ImageIcon icon = new ImageIcon("./src/wall.png");
		Image transformImage = icon.getImage();
		icon = new ImageIcon(transformImage.getScaledInstance(10, 700, java.awt.Image.SCALE_SMOOTH));	
		JLabel wallJLabel = new JLabel();	
		wallJLabel.setIcon(icon);
		wallJLabel.setSize(10,700);
		wallJLabel.setLocation(580,0);
		game.add(wallJLabel);	
		JLabel wallJLabel2 = new JLabel();	
		wallJLabel2.setIcon(icon);
		wallJLabel2.setSize(10,700);
		wallJLabel2.setLocation(580,700);
		game.add(wallJLabel2);
		Blocker block;
		game.addKeyListener(game);
		game.setVisible(true);
		while(true) {
			healthBar_1p.setBounds(600, 500, 100, 20);
			healthBar_2p.setBounds(600, 550, 100, 20);
			hpJLabel.setVisible(false);
			hpJLabel2.setVisible(false);
			score.setVisible(false);
			healthBar_1p.setVisible(false);
			healthBar_2p.setVisible(false);
			game.pauseButton.setVisible(false);
			game.resumeButton.setVisible(false);
			for(int i = 0; i < 7; i++) {
				game.blockList.get(i).setVisible(false);
			}
			game.startButton.setVisible(true);
			game.start2PButton.setVisible(true);
			game.leaveButton.setVisible(true);
			speedUp = 0;
			count = 0;
			game.player_1p.health = 1000;
			game.player_1p.setLocation(100,100);
			game.player_1p.setVisible(false);
			game.command_1p = "NONE";
			game.player_2p.health = 1000;
			game.player_2p.setLocation(500,100);
			game.player_2p.setVisible(false);
			game.command_2p = "NONE";
		

			while(!game.start) {System.out.println("");
			}


			for(int i = 0; i < 7; i++) {
				game.blockList.get(i).setVisible(true);
			}
			game.player_1p.setVisible(true);
			game.setFocusable(true);
			score.setVisible(true);
			healthBar_1p.setVisible(true);
			game.pauseButton.setVisible(true);
			game.resumeButton.setVisible(true);
			hpJLabel.setVisible(true);
			game.startButton.setVisible(false);
			game.start2PButton.setVisible(false);
			game.leaveButton.setVisible(false);
			if(game.isTwoPlayer) {
				game.player_2p.setVisible(true);
				healthBar_2p.setVisible(true);
				hpJLabel2.setVisible(true);
			}
			
		

			while(true) {
				while(game.stop) {
					System.out.println("");
				}
				game.requestFocus();
				Thread.sleep(50);
				if(count == 500) {
					count = 0;
					speedUp++;
				}
				if (wallJLabel.getLocation().y < -680)
					wallJLabel.setLocation(580,700);
				if (wallJLabel2.getLocation().y < -680)
					wallJLabel2.setLocation(580,700);
				wallJLabel.setLocation(wallJLabel.getLocation().x,wallJLabel.getLocation().y - speedUp);
				wallJLabel2.setLocation(wallJLabel2.getLocation().x,wallJLabel2.getLocation().y - speedUp);
				game.player_1p.resetPlayerState();
				game.player_2p.resetPlayerState();
				boolean onleftblock_1p,onrightblock_1p,onleftblcok_2p,onrightblock_2p;
				onleftblock_1p = onrightblock_1p = onleftblcok_2p = onrightblock_2p = false;
				int blocktype_1p,blocktype_2p;
				for(int i = 0; i < game.blockList.size(); i++) {
					block = game.blockList.get(i);
					blocktype_1p = game.player_1p.setPlayerState(block, ((speedUp + 10) / 2) + 1, speedUp, game.command_1p, game.command_2p, game.player_2p);
					blocktype_2p = game.player_2p.setPlayerState(block, ((speedUp + 10) / 2) + 1, speedUp, game.command_1p, game.command_2p, game.player_1p);
					if(blocktype_1p == 3)
						onrightblock_1p = true;
					if(blocktype_1p == 4)
						onleftblock_1p = true;
					if(blocktype_2p == 3)
						onrightblock_2p = true;
					if(blocktype_2p == 4)
						onleftblcok_2p = true;
					block.setLocation(block.getLocation().x, block.getLocation().y - speedUp);
				}
				
				//輸送帶速度加道player上
				int err_1p = 0,err_2p = 0;
				if(onrightblock_1p)
					err_1p = ((speedUp + 10) / 2) - 1;
				if(onleftblock_1p)
					err_1p = -((speedUp + 10) / 2) + 1;
				if(onrightblock_2p)
					err_2p = ((speedUp + 10) / 2) - 1;
				if(onleftblcok_2p)
					err_2p = -((speedUp + 10) / 2) + 1;
				if(game.player_1p.speed==10 || game.player_1p.speed==-10 || game.player_1p.speed ==0)
					game.player_1p.speed += err_1p;
				if(game.player_2p.speed==10 || game.player_2p.speed==-10 || game.player_2p.speed == 0)
					game.player_2p.speed += err_2p;
				if(err_1p == 0 && game.player_1p.speed!=10 && game.player_1p.speed!=-10)
					game.player_1p.speed = 0;
				if(err_2p == 0 && game.player_2p.speed==10 && game.player_2p.speed==-10)
					game.player_2p.speed = 0;
				
				//1P 2P 碰撞檢測
				boolean left_1P,right_1P,left_2P,right_2P,collide;
				left_1P = left_2P = right_1P = right_2P = false;
				int push1p = game.player_1p.speed,push2p = game.player_2p.speed;
				collide = false;
				if((game.player_1p.getLocation().y <= game.player_2p.getLocation().y + game.player_2p.getHeight() &&
						game.player_1p.getLocation().y >= game.player_2p.getLocation().y) ||
						(game.player_2p.getLocation().y <= game.player_1p.getLocation().y + game.player_1p.getHeight() &&
								game.player_2p.getLocation().y >= game.player_1p.getLocation().y)) {
					int tmp_speed1p = game.player_1p.speed,tmp_speed2p = game.player_2p.speed;
					if(game.player_1p.getLocation().x - (game.player_2p.getLocation().x + game.player_2p.getWidth()) <= 10 && 
							game.player_1p.getLocation().x - (game.player_2p.getLocation().x + game.player_2p.getWidth()) >= 0) {//2p 1p
						if(game.player_2p.speed>=0 && game.player_1p.speed<=0) {
							if(game.player_2p.speed == -game.player_1p.speed && Math.abs(game.player_2p.speed)>0 && Math.abs(game.player_1p.speed)>0) {//兩個互擋 
								collide = true;
								game.player_1p.setLocation((game.player_1p.getLocation().x+game.player_2p.getLocation().x+game.player_2p.getWidth())/2,game.player_1p.getLocation().y);
								game.player_2p.setLocation((game.player_1p.getLocation().x+game.player_2p.getLocation().x+game.player_2p.getWidth())/2-game.player_2p.getWidth(),game.player_2p.getLocation().y);
							}
							else if(Math.abs(game.player_2p.speed)>Math.abs(game.player_1p.speed)) {
								if(game.command_1p.equals("NONE"));
									right_1P = true;
								push1p = (game.player_2p.speed+game.player_1p.speed) - (game.player_1p.getLocation().x-(game.player_2p.getLocation().x + game.player_2p.getWidth()));
								push2p = game.player_2p.speed+game.player_1p.speed;
							}
							else if(Math.abs(game.player_1p.speed)>Math.abs(game.player_2p.speed)) {
								if(game.command_2p.equals("NONE"));
									left_2P = true;
								push2p = (game.player_1p.getLocation().x - (game.player_2p.getLocation().x + game.player_2p.getWidth())) + (game.player_2p.speed+game.player_1p.speed);//(Math.abs(game.player_1p.speed) - Math.abs(game.player_2p.speed));
								push1p = game.player_2p.speed+game.player_1p.speed;
							}
						}
						if(game.player_1p.speed<0 && game.player_2p.speed<0) {
							if(Math.abs(game.player_1p.speed)>Math.abs(game.player_2p.speed)) {
								if(game.command_2p.equals("NONE"))
									left_2P = true;
								push2p = (game.player_2p.getLocation().x - (game.player_1p.getLocation().x + game.player_1p.getWidth())) + game.player_1p.speed;
								push1p = game.player_1p.speed;
							}
						}
						if(game.player_1p.speed>0 && game.player_2p.speed>0) {
							if(Math.abs(game.player_2p.speed)>Math.abs(game.player_1p.speed)) {
								if(game.command_1p.equals("NONE"))
									right_1P = true;
								push1p = game.player_2p.speed -(game.player_2p.getLocation().x - (game.player_1p.getLocation().x + game.player_1p.getWidth()));
								push2p = game.player_2p.speed;
							}
						}
						if(game.player_1p.getLocation().x+push1p>550 && game.player_2p.getLocation().x+push2p>520) {
							game.player_2p.setLocation(520,game.player_2p.getLocation().y);
							game.player_1p.setLocation(550,game.player_1p.getLocation().y);
							push1p = push2p = 0;
						}
						if(game.player_1p.getLocation().x+push1p<30 && game.player_2p.getLocation().x+push2p<0) {
							game.player_2p.setLocation(0,game.player_2p.getLocation().y);
							game.player_1p.setLocation(30,game.player_1p.getLocation().y);
							push1p = push2p = 0;
						}
					}
					if(game.player_2p.getLocation().x - (game.player_1p.getLocation().x + game.player_1p.getWidth()) <=10 && 
							game.player_2p.getLocation().x - (game.player_1p.getLocation().x + game.player_1p.getWidth()) >= -10) {//1p 2p
						if(game.player_1p.speed>=0 && game.player_2p.speed<=0) {
							if(game.player_2p.speed == -game.player_1p.speed && Math.abs(game.player_2p.speed)>0 && Math.abs(game.player_1p.speed)>0) {//兩個互擋
								collide = true;
								game.player_1p.setLocation((game.player_2p.getLocation().x+game.player_1p.getLocation().x+game.player_1p.getWidth())/2-game.player_2p.getWidth(),game.player_1p.getLocation().y);
								game.player_2p.setLocation((game.player_2p.getLocation().x+game.player_1p.getLocation().x+game.player_1p.getWidth())/2,game.player_2p.getLocation().y);
							}
							if(Math.abs(game.player_1p.speed)>Math.abs(game.player_2p.speed)) {
								if(game.command_2p.equals("NONE"))
									right_2P = true;
								push2p = (game.player_2p.speed+game.player_1p.speed) - (game.player_2p.getLocation().x - (game.player_1p.getLocation().x + game.player_1p.getWidth()));
								push1p = game.player_2p.speed+game.player_1p.speed;
							}
							if(Math.abs(game.player_2p.speed)>Math.abs(game.player_1p.speed)) {
								if(game.command_1p.equals("NONE"));
									left_1P = true;	
								push1p = (game.player_2p.getLocation().x - (game.player_1p.getLocation().x + game.player_1p.getWidth())) + (game.player_2p.speed+game.player_1p.speed);
								push2p = game.player_2p.speed+game.player_1p.speed;
							}
						}
						if(game.player_1p.speed<0 && game.player_2p.speed<0) {
							if(Math.abs(game.player_2p.speed)>Math.abs(game.player_1p.speed)) {
								if(game.command_1p.equals("NONE"))
									left_1P = true;
								push1p = (game.player_2p.getLocation().x - (game.player_1p.getLocation().x + game.player_1p.getWidth())) + game.player_2p.speed;
								push2p = game.player_2p.speed;
							}
						}
						if(game.player_1p.speed>0 && game.player_2p.speed>0) {
							if(Math.abs(game.player_1p.speed)>Math.abs(game.player_2p.speed)) {
								if(game.command_2p.equals("NONE"))
									right_2P = true;
								push2p = game.player_1p.speed -(game.player_2p.getLocation().x - (game.player_1p.getLocation().x + game.player_1p.getWidth()));
								push1p = game.player_1p.speed;
							}
						}
						if(game.player_2p.getLocation().x+push2p>550 && game.player_1p.getLocation().x+push1p>520) {
							game.player_1p.setLocation(520,game.player_1p.getLocation().y);
							game.player_2p.setLocation(550,game.player_2p.getLocation().y);
							push1p = push2p = 0;
						}
						if(game.player_2p.getLocation().x+push2p<30 && game.player_1p.getLocation().x+push1p<0) {
							game.player_1p.setLocation(0,game.player_1p.getLocation().y);
							game.player_2p.setLocation(30,game.player_2p.getLocation().y);
							push1p = push2p = 0;
						}
					}
				}

				boolean _1pOn2p = false,_2pOn1p = false;
				if((game.player_1p.getLocation().x>game.player_2p.getLocation().x&&game.player_1p.getLocation().x<game.player_2p.getLocation().x+game.player_2p.getWidth()) || 
						(game.player_2p.getLocation().x>game.player_1p.getLocation().x&&game.player_2p.getLocation().x<game.player_1p.getLocation().x+game.player_1p.getWidth()) ||
						(game.player_1p.getLocation().x==game.player_2p.getLocation().x&&game.player_1p.getLocation().x+game.player_1p.getWidth()==game.player_2p.getLocation().x+game.player_2p.getWidth())) {
					if(game.player_1p.getLocation().y-game.player_2p.getLocation().y-game.player_2p.getHeight()<=10 &&
							game.player_1p.getLocation().y-game.player_2p.getLocation().y-game.player_2p.getHeight()>=-10) {
						_2pOn1p = true;
					}
				}//2p在1p上面
				if((game.player_2p.getLocation().x>game.player_1p.getLocation().x&&game.player_2p.getLocation().x<game.player_1p.getLocation().x+game.player_1p.getWidth()) || 
						(game.player_1p.getLocation().x>game.player_2p.getLocation().x&&game.player_1p.getLocation().x<game.player_2p.getLocation().x+game.player_2p.getWidth()) ||
						(game.player_2p.getLocation().x==game.player_1p.getLocation().x&&game.player_2p.getLocation().x+game.player_2p.getWidth()==game.player_1p.getLocation().x+game.player_1p.getWidth())) {
					if(game.player_2p.getLocation().y-game.player_1p.getLocation().y-game.player_1p.getHeight()<=10 &&
							game.player_2p.getLocation().y-game.player_1p.getLocation().y-game.player_1p.getHeight()>=-10) {
						_1pOn2p = true;
					}
				}//1p在2p上面
				
				//1p 2p 移動
				if(!game.player_1p.getOnBlock()) {
					game.player_1p.setLocation((game.player_1p.getLocation().x - (game.player_1p.getLocation().x % 10)), game.player_1p.getLocation().y+10);
					//
				}
				if(game.command_1p.equals("RIGHT") && !game.player_1p.getBesideBlock().equals("ON_LEFT") && game.player_1p.getLocation().x + game.player_1p.getWidth() <= 570 && !collide || onrightblock_1p) {
					if(game.player_1p.getLocation().x+push1p>550)
						game.player_1p.setLocation(550,game.player_1p.getLocation().y);
					else
						game.player_1p.setLocation(game.player_1p.getLocation().x + push1p, game.player_1p.getLocation().y);
				}
				else if(game.command_1p.equals("LEFT") && !game.player_1p.getBesideBlock().equals("ON_RIGHT") && game.player_1p.getLocation().x > 0 && !collide || onleftblock_1p) {
					if(game.player_1p.getLocation().x+push1p<0)
						game.player_1p.setLocation(0,game.player_1p.getLocation().y);
					else
						game.player_1p.setLocation(game.player_1p.getLocation().x + push1p, game.player_1p.getLocation().y);
				}
				if(!game.player_2p.getOnBlock()) {
					game.player_2p.setLocation((game.player_2p.getLocation().x - (game.player_2p.getLocation().x % 10)), game.player_2p.getLocation().y+10);
					//
				}
				if(game.command_2p.equals("RIGHT") && !game.player_2p.getBesideBlock().equals("ON_LEFT") && game.player_2p.getLocation().x + game.player_2p.getWidth() <= 570 && !collide || onrightblock_2p) {
					if(game.player_2p.getLocation().x+push2p>550)
						game.player_2p.setLocation(550,game.player_2p.getLocation().y);
					else
						game.player_2p.setLocation(game.player_2p.getLocation().x + push2p, game.player_2p.getLocation().y);
				}
				else if(game.command_2p.equals("LEFT") && !game.player_2p.getBesideBlock().equals("ON_RIGHT") && game.player_2p.getLocation().x > 0 && !collide || onleftblcok_2p) {
					if(game.player_2p.getLocation().x+push2p<0)
						game.player_2p.setLocation(0,game.player_2p.getLocation().y);
					else
						game.player_2p.setLocation(game.player_2p.getLocation().x + push2p, game.player_2p.getLocation().y);
				}

				//1p推2p或2p推1p
				if(left_1P && !onleftblock_1p && !onrightblock_1p) {
					game.player_1p.setLocation(game.player_1p.getLocation().x + push1p, game.player_1p.getLocation().y);
				}
				if(right_1P && !onleftblock_1p && !onrightblock_1p) {
					game.player_1p.setLocation(game.player_1p.getLocation().x + push1p, game.player_1p.getLocation().y);
				}
				if(left_2P && !onleftblcok_2p && ! onrightblock_2p) {
					game.player_2p.setLocation(game.player_2p.getLocation().x + push2p, game.player_2p.getLocation().y);
				}
				if(right_2P && !onleftblcok_2p && ! onrightblock_2p) {
					game.player_2p.setLocation(game.player_2p.getLocation().x + push2p, game.player_2p.getLocation().y);
				}

				//1p在2p上或2p在1p上
				if(_2pOn1p) {
					game.player_2p.setLocation(game.player_2p.getLocation().x,game.player_1p.getLocation().y - game.player_1p.getHeight());;
				}
				if(_1pOn2p) {
					game.player_1p.setLocation(game.player_1p.getLocation().x,game.player_2p.getLocation().y - game.player_2p.getHeight());;
				}

				for(int i = 0; i < game.blockList.size(); i++) {
					block = game.blockList.get(i);
					block.setLocation(block.getLocation().x, block.getLocation().y-1);
					Block_Animation(block);//block動畫
					if(block.getLocation().y<=0) {
						block.setLocation(block.getLocation().x, 700);
						block.changeType(r1.nextInt(39) * 10, r1.nextInt(10));
						block.setVisible(true);
					}
				}
				healthBar_1p.setBounds(650, 500, game.player_1p.getHealth()/10, 20);
				healthBar_2p.setBounds(650, 550, game.player_2p.getHealth()/10, 20);
				if((game.player_1p.getHealth() <= 0 || game.player_1p.getLocation().y < 0 || game.player_1p.getLocation().y > 700) && !game.isTwoPlayer) {
				 	JOptionPane.showMessageDialog(game,"Game over, your LV is "+ (speedUp -  1));
				 	game.isTwoPlayer = false;
				 	Robot rb=null;
					try {
						rb = new Robot();
					} catch (AWTException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rb.keyPress(KeyEvent.VK_SHIFT);
					rb.keyRelease(KeyEvent.VK_SHIFT);
				 	break;
				}
				else if((game.player_1p.getHealth() <= 0 || game.player_1p.getLocation().y < 0 || game.player_1p.getLocation().y > 700) && game.isTwoPlayer) {
					JOptionPane.showMessageDialog(game,"2P Win!!!");
				 	game.isTwoPlayer = false;
				 	Robot rb=null;
					try {
						rb = new Robot();
					} catch (AWTException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rb.keyPress(KeyEvent.VK_SHIFT);
					rb.keyRelease(KeyEvent.VK_SHIFT);
				 	break;
				}
				else if((game.player_2p.getHealth() <= 0 || game.player_2p.getLocation().y < 0 || game.player_2p.getLocation().y > 700) && game.isTwoPlayer) {
					JOptionPane.showMessageDialog(game,"1P Win!!!");
				 	game.isTwoPlayer = false;
				 	Robot rb=null;
					try {
						rb = new Robot();
					} catch (AWTException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rb.keyPress(KeyEvent.VK_SHIFT);
					rb.keyRelease(KeyEvent.VK_SHIFT);
				 	break;
				}
				else if(speedUp == 11 && !game.isTwoPlayer) {
					JOptionPane.showMessageDialog(game,"Congratulations!!! You passed LV 10");
					game.isTwoPlayer = false;
					Robot rb=null;
					try {
						rb = new Robot();
					} catch (AWTException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rb.keyPress(KeyEvent.VK_SHIFT);
					rb.keyRelease(KeyEvent.VK_SHIFT);
					break;
				}
				else if(speedUp == 11 && game.isTwoPlayer) {
					JOptionPane.showMessageDialog(game,"Tie!!!");
					game.isTwoPlayer = false;
					Robot rb=null;
					try {
						rb = new Robot();
					} catch (AWTException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rb.keyPress(KeyEvent.VK_SHIFT);
					rb.keyRelease(KeyEvent.VK_SHIFT);
					break;
				}
				count++;
				score.setText("LV = " + speedUp);
			}
			//System.exit(0);
			game.start = false;
		}
	}
	
	public MyFrame() {
		super("NS_SHAFT");
		Random r1 = new Random();
		setSize(800, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);


		startButton = new JButton("Start 1P");
		add(startButton);
		startButton.setLocation(135,100);
		startButton.setSize(300,100);
		startButton.setForeground(Color.darkGray);
		startButton.setFont(new Font("Arial", Font.PLAIN, 40));
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				start = true;
				Robot rb=null;
				try {
					rb = new Robot();
				} catch (AWTException e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				rb.keyPress(KeyEvent.VK_SHIFT);
				rb.keyRelease(KeyEvent.VK_SHIFT);
			}
		});
		start2PButton = new JButton("Start 2P");
		add(start2PButton);
		start2PButton.setLocation(135,250);
		start2PButton.setSize(300,100);
		start2PButton.setForeground(Color.darkGray);
		start2PButton.setFont(new Font("Arial", Font.PLAIN, 40));
		start2PButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				start = true;
				isTwoPlayer = true;
				Robot rb=null;
				try {
					rb = new Robot();
				} catch (AWTException e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				rb.keyPress(KeyEvent.VK_SHIFT);
				rb.keyRelease(KeyEvent.VK_SHIFT);
			}
		});
		leaveButton = new JButton("Exit");
		add(leaveButton);
		leaveButton.setLocation(135,400);
		leaveButton.setSize(300,100);
		leaveButton.setFont(new Font("Arial", Font.PLAIN, 40));
		leaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		pauseButton = new JButton("pause");
		pauseButton.setSize(100,30);
		pauseButton.setLocation(640,230);
		pauseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stop = true;
			}
		});
		add(pauseButton);
		resumeButton = new JButton("resume");
		resumeButton.setSize(100,30);
		resumeButton.setLocation(640,330);
		resumeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stop = false;
				
			}
		});
		add(resumeButton);
		pauseButton.setVisible(false);
		resumeButton.setVisible(false);
		
		
		for(int i = 0; i < 7; i++) {
			block = new Blocker(r1.nextInt(39) * 10,0 + 100 * i,200,50,r1.nextInt(10));
			blockList.add(block);
			add(block);
		}
		for(int i = 0; i < 7; i++) {
			blockList.get(i).setVisible(false);
		}

		player_1p.setLocation(100,100);
		player_1p.setSize(30,30);
		player_1p.setOpaque(true);
		player_1p.setBackground(Color.BLUE);
		add(player_1p);
		player_1p.setVisible(false);
		
		player_2p.setLocation(500,100);
		player_2p.setSize(30,30);
		player_2p.setOpaque(true);
		player_2p.setBackground(Color.GREEN);
		add(player_2p);
		player_2p.setVisible(false);
		
	}
	
	protected Player player_1p = new Player();
	protected Player player_2p = new Player();
	protected Blocker block;
	protected String command_1p = "NONE";
	protected String command_2p = "NONE";
	protected ArrayList<Blocker> blockList = new ArrayList();
	
	static public void Block_Animation(Blocker block) {
		if(block.get_Blocktype() == 3) {//輸送帶向右
			try {
				block.fake = ImageIO.read(new File("./src/conveyor_right98x64.png"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			ImageIcon imageIcon = new ImageIcon(block.fake.getSubimage(0, 16*block.animationframe, 98, 16));
			Image transform = imageIcon.getImage();
			imageIcon = new ImageIcon(transform.getScaledInstance(block.getWidth(), block.getHeight(), java.awt.Image.SCALE_SMOOTH));	
			block.setIcon(imageIcon);
			block.animationframe++;
			if(block.animationframe>3)
				block.animationframe = 0;
		}
		if(block.get_Blocktype() == 4) {//輸送帶向左
			try {
				block.fake = ImageIO.read(new File("./src/conveyor_left98x64.png"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			ImageIcon imageIcon = new ImageIcon(block.fake.getSubimage(0, 16*block.animationframe, 96, 16));
			Image transform = imageIcon.getImage();
			imageIcon = new ImageIcon(transform.getScaledInstance(block.getWidth(), block.getHeight(), java.awt.Image.SCALE_SMOOTH));	
			block.setIcon(imageIcon);
			block.animationframe++;
			if(block.animationframe>3)
				block.animationframe = 0;
		}
		if(block.get_Blocktype() == 2 && block.fakeanimation) {//假的反轉
			try {
				block.fake = ImageIO.read(new File("./src/fake97x217.png"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			ImageIcon imageIcon = new ImageIcon(block.fake.getSubimage(0, 16*block.animationframe, 97, 36));
			Image transform = imageIcon.getImage();
			imageIcon = new ImageIcon(transform.getScaledInstance(block.getWidth(), block.getHeight(), java.awt.Image.SCALE_SMOOTH));	
			block.setIcon(imageIcon);
			block.animationframe++;
			if(block.animationframe>4) {
				block.animationframe = 0;
				imageIcon = new ImageIcon("./src/2.png");
				transform = imageIcon.getImage();
				imageIcon = new ImageIcon(transform.getScaledInstance(block.getWidth(), block.getHeight(), java.awt.Image.SCALE_SMOOTH));	
				block.setIcon(imageIcon);
				block.fakeanimation = false;
			}
		}
	}
	
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_A) {
			command_1p = "LEFT";
			player_1p.speed = -10;
		}
		else if(e.getKeyCode()==KeyEvent.VK_D) {
			command_1p = "RIGHT";
			player_1p.speed = 10;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			command_2p = "LEFT";
			player_2p.speed = -10;
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			command_2p = "RIGHT";
			player_2p.speed = 10;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_A && command_1p.equals("LEFT")) {
			command_1p = "NONE";
			player_1p.speed = 0;
		}
		else if(e.getKeyCode()==KeyEvent.VK_D && command_1p.equals("RIGHT")) {
			command_1p = "NONE";
			player_1p.speed = 0;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT && command_2p.equals("LEFT")) {
			command_2p = "NONE";
			player_2p.speed = 0;
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT && command_2p.equals("RIGHT")) {
			command_2p = "NONE";
			player_2p.speed = 0;
		}
	}

}
