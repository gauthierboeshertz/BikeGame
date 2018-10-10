/*
 *	Author:      Auguste Lefevre
 *	Date:        27 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.bikegame;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Ball;
import ch.epfl.cs107.play.game.actor.general.Block;
import ch.epfl.cs107.play.game.actor.general.CheckPoint;
import ch.epfl.cs107.play.game.actor.general.FinishTrigger;
import ch.epfl.cs107.play.game.actor.general.Flamme;
import ch.epfl.cs107.play.game.actor.general.IceBlock;
import ch.epfl.cs107.play.game.actor.general.Jump;
import ch.epfl.cs107.play.game.actor.general.Pendulum;
import ch.epfl.cs107.play.game.actor.general.PendulumBall;
import ch.epfl.cs107.play.game.actor.general.Sign;
import ch.epfl.cs107.play.game.actor.general.Spikes;
import ch.epfl.cs107.play.game.actor.general.Star;
import ch.epfl.cs107.play.game.actor.general.Start;
import ch.epfl.cs107.play.game.actor.general.Swing;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.general.TreeSnow;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGame2 extends ActorGame {
	
	private double timer = 0.0;
	private String time = timer + "SEC";
	
	
	// Declare and initialize a list of vector used for the creation of Terrain
	private ArrayList<Vector> listVector = new ArrayList<Vector>();
	
	// Declare a Bike and his position
	private BikeExtended bike;
	private Vector bikePosition = new Vector(-180.0f, 0.0f);
	
	private int countScore = 0;
	
	private FinishTrigger finish;
	private boolean finish1time;
	
	private Flamme flame1;
	
	// Declare a checkpoint and a timer link to this checkpoint
	private CheckPoint check1;
	private double timerCheck = 0.0;
	
	// Declare 2 spikes and the timer link with their
	private Spikes spikes1;
	private double timerSpikes1 = 0.0;
	private Spikes spikes2;
	private double timerSpikes2 = 0.0;
	
	// Declare 2 star and the boolean wish give us information about the fact that the star have been collect or not 
	private Star star1;
	private boolean collect1 = false;
	private Star star2;
	private boolean collect2 = false;
	
	// Declare and initialize a string wish will represent the score of the player
	private String s = countScore + " PTS ";


	// Method witch add a vector to the list listVector
	private void addVector(float a, float b) {
		Vector vector = new Vector(a, b);
		listVector.add(vector);
	}
	
	// Declare the message "messageScore" and create a method witch is call in the begin to initialize this message
	TextGraphics messageScore;
	private void textScore(String texte) {
		messageScore = new TextGraphics(texte, 0.05f, Color.WHITE, Color.WHITE, 0.02f, true, false,
				new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		messageScore.setParent(getCanvas());
		messageScore.setRelativeTransform(Transform.I.translated(0.7f, -0.5f));
	}
	
	// Declare the message "messageTime" and create a method witch is call in the begin to initialize this message
	TextGraphics messageTime;
	private void textTime(String texte) {
		messageTime = new TextGraphics(texte, 0.05f, Color.WHITE, Color.WHITE, 0.02f, true, false,
				new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		messageTime.setParent(getCanvas());
		messageTime.setRelativeTransform(Transform.I.translated(-0.7f, -0.5f));
	}
	
	// Declare the message "messageEnd" and create a method witch is call in the begin to initialize this message
	TextGraphics messageEnd;
	private void textFinish(String texte) {
		messageEnd = new TextGraphics(texte, 0.2f, Color.RED, Color.WHITE, 0.02f, true, false,
				new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		messageEnd.setParent(getCanvas());
		messageEnd.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}
	
	// Declare the message "messageRestart" and create a method witch is call in the begin to initialize this message
	TextGraphics messageRestart;
	private void textRestart(String texte) {
		messageRestart = new TextGraphics(texte, 0.05f, Color.WHITE, Color.WHITE, 0.02f, true,
				true, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		messageRestart.setParent(getCanvas());
		messageRestart.setRelativeTransform(Transform.I.translated(0.0f, -1.2f));
	}
	
	// Declare the message "messageCheckpoint" and create a method witch is call in the begin to initialize this message
	TextGraphics messageCheckpoint;
	private void textCheckPoint(String texte) {
		messageCheckpoint = new TextGraphics(texte, 0.1f, Color.YELLOW, Color.WHITE, 0.02f, true,
				true, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		messageCheckpoint.setParent(getCanvas());
		messageCheckpoint.setRelativeTransform(Transform.I.translated(0.0f, -0.6f));
	}
	
	// Declare the message "messageSpikes" and create a method witch is call in the begin to initialize this message
	TextGraphics messageSpikes;
	private void textSpikes(String texte) {
		messageSpikes = new TextGraphics(texte, 0.1f, Color.RED, Color.WHITE, 0.02f, true,
				true, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		messageSpikes.setParent(getCanvas());
		messageSpikes.setRelativeTransform(Transform.I.translated(0.0f, -0.6f));
	}
	
	
	
	
	public boolean begin(Window window, FileSystem fileSystem) {
		// Call the function begin of the super class ActorGame
		super.begin(window, fileSystem);
		
		// Set finish1time to true
		finish1time = true;
	
		// Add vector the listVector to create Terrain
		this.addVector(-1000.0f, -1000.0f);
		this.addVector(-1000.0f, 0.0f);
		this.addVector(-60.0f, 0.0f);
		this.addVector(-60.f, -7.f);
		this.addVector(-45.f, -7.f);
		this.addVector(-45.f, 0.f);
		this.addVector(0.0f, 0.0f);
		this.addVector(3.0f, 1.0f);
		this.addVector(8.0f, 1.0f);
		this.addVector(15.0f, 3.0f);
		this.addVector(16.0f, 3.0f);
		this.addVector(25.0f, 0.0f);
		this.addVector(35.0f, -5.0f);
		this.addVector(50.0f, -5.0f);
		this.addVector(55.0f, -4.0f);
		this.addVector(65.0f, 0.0f);
		this.addVector(6500.0f, -1000.0f);
		
		
		try {
			// Create Terrain
			Terrain terrain = new Terrain(this, true, listVector);
			this.addActor(terrain);
			
						
			// Initialize  bike
			bike = new BikeExtended(this, false, bikePosition);
			this.addActor(bike);
			setViewCandidate(bike);
			
			// Create Start flag
			Start start = new Start(this, true, new Vector (-180.0f, 0.0f), "flag.green.png");
			this.addActor(start);
		
			
			// Initialize check1
			check1 = new CheckPoint(this, true, new Vector(-75.0f, 0.0f), "flag.yellow.png");
			this.addActor(check1);
			
			// Initialize 3 Stars
			star1 = new Star(this, true, new Vector(-97.0f, 6.0f));
			this.addActor(star1);
			star2 = new Star(this, true, new Vector(-52.5f, 3.0f));
			this.addActor(star2);
			
			
			// Create 2 jumps
			Jump jump1 = new Jump(this, true, new Vector(-108.0f, 0.0f), 8.0f, 4.0f);
			this.addActor(jump1);
			Jump jump2 = new Jump(this, true, new Vector(-63.0f, 0.0f), 3.0f, 2.0f);
			this.addActor(jump2);
			
			// Create Sign and block (use as post)
			Sign gliss = new Sign(this, true, new Vector(-152.0f, 1.0f) , "gliss.png");
			this.addActor(gliss);
			Block post = new Block(this, true, new Vector(-151.2f, 0.0f), "metal.7.png", 0.3f, 1.0f, true, 1.0f, -1.0f);
			this.addActor(post);
			Sign gliss2 = new Sign(this, true, new Vector(-23.0f, 1.0f) , "gliss.png");
			this.addActor(gliss2);
			Block post2 = new Block(this, true, new Vector(-22.2f, 0.0f), "metal.7.png", 0.3f, 1.0f, true, 1.0f, -1.0f);
			this.addActor(post2);
			
			// Create 2 IceBlock
			IceBlock ice = new IceBlock(this, true, new Vector(-148.0f, -7.0f), 30.0f, 7.0f);
			this.addActor(ice);
			IceBlock ice2 = new IceBlock(this, true, new Vector(-20.0f, -7.0f), 17.0f, 7.0f);
			this.addActor(ice2);
			
			
			// Create 5 Trees
			TreeSnow tree1 = new TreeSnow(this, true, new Vector(-144.0f, 0.0f));
			this.addActor(tree1);
			TreeSnow tree2 = new TreeSnow(this, true, new Vector(-135.0f, 0.0f));
			this.addActor(tree2);
			TreeSnow tree3 = new TreeSnow(this, true, new Vector(-124.0f, 0.0f));
			this.addActor(tree3);
			TreeSnow tree4 = new TreeSnow(this, true, new Vector(-17.0f, 0.0f));
			this.addActor(tree4);
			TreeSnow tree5 = new TreeSnow(this, true, new Vector(-7.0f, 0.0f));
			this.addActor(tree5);
			
			// Create 3 crate 
			Crate crate1 = new Crate(this, false, new Vector(-154.0f, 0.0f), "box.4.png", 1.0f, 1.0f);
			Crate crate2 = new Crate(this, false, new Vector(-156.0f, 0.0f), "box.4.png", 1.0f, 1.0f);
			Crate crate3 = new Crate(this, false, new Vector(-155.0f, 0.0f), "box.4.png", 1.0f, 1.0f);
			this.addActor(crate1);
			this.addActor(crate2);
			this.addActor(crate3);
			
			// Create a finish flag
			finish = new FinishTrigger(this, true, new Vector(50.0f, -5.0f), "flag.red.png");
			this.addActor(finish);
			
			// Create a PendulumBlock
			Pendulum pendul1 = new Pendulum(this, true, new Vector(-52.5f, 6.0f), new Vector(-52.5f, 6.0f), new Vector(-52.5f, 0.0f), 6.0f);
			this.addActor(pendul1);
			
			// Create Flame
			flame1 = new Flamme(this, true, new Vector(-60.0f, -7.0f), 15.0f, 2.0f);
			this.addActor(flame1);
			
			// Create 2 PendulumBall
			PendulumBall pendulBall1 = new PendulumBall(this, true, new Vector(15.0f, 10.0f),  new Vector(15.0f, 10.0f), new Vector(21.0f, 10.0f) ,6.0f);
			this.addActor(pendulBall1);
			PendulumBall pendulBall2 = new PendulumBall(this, true, new Vector(3.0f, 7.5f),  new Vector(3.0f, 7.5f), new Vector(-3.0f, 7.5f) ,6.0f);
			this.addActor(pendulBall2);
			
			// Create 2 spikes (that immobilize the bike)
			spikes1 = new Spikes(this, true, new Vector(-45.0f, 0.0f));
			this.addActor(spikes1);
			spikes2 = new Spikes(this, true, new Vector(-97.0f, 0.0f));
			this.addActor(spikes2);
			
			//Create a swing and a ball to activate the swing a first time
			Swing swing1 = new Swing(this, true, new Vector(-28.0f, 0.0f), new Vector(-28.0f, 0.0f), new Vector (-31.0f, 1.0f));
			this.addActor(swing1);
			Ball ball1 = new Ball(this, false, new Vector(-29.0f, 3.0f), "", -1.0f);
			this.addActor(ball1);
			
			// Create a Moon
			Block moon = new Block(this, true,new Vector(-177.0f, 5.0f), "moon.full.png", 2.0f, 2.0f, true, 1.0f, 0.0f);
			this.addActor(moon);
			
			// Create a cloud
			Block cloud = new Block(this, true, new Vector(-176.0f, 4.5f), "smoke.white.1.png", 4.0f, 2.0f, true, 1.0f, 1.0f);
			this.addActor(cloud);
			
			// Create Rock
			Block rock = new Block(this, true, new Vector(-170.0f, 0.0f), "rock.png", 5.0f, 5.0f, true, 1.0f, -1.0f);
			this.addActor(rock);
			Block rock2 = new Block(this, true, new Vector(-85.0f, 0.0f), "rock.png", 5.0f, 5.0f, true, 1.0f, -1.0f);
			this.addActor(rock2);
			
		// catch the error throws and write a message in the console with more information about the error
		} catch(NullPointerException e ) {
			System.err.println(" [ " + e.getMessage() + " ] ");
		}
		catch(IllegalArgumentException e) {
			System.err.println(" [ " + e.getMessage() + " ] ");
		}
		
		// Initialize the different text (to reduce the loading when we display a message)
		textFinish("Default");
		textRestart("Default");
		textScore(s);
		textTime(time);
		textCheckPoint("CHECKPOINT");
		textSpikes("WAIT 5 SEC");
			
		return true;
		
	}
	
	
	
	
	/*
	 * Update method 
	 */
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		// If your hitbox touch something not allowed
		if(bike.getHit()) {
			// Destroy the bike
			bike.destroy();
			// If the bike don't cross the finish line and the check point
			if (!finish.getBikeTouch() && !check1.getBikeTouch()) {
				messageEnd.setText("GAME OVER ");
				messageEnd.draw(getCanvas());
				messageRestart.setText("PRESS R TO RESET");
				messageRestart.draw(getCanvas());
			}
			// If the bike don't cross the finish line but cross the check point
			if (!finish.getBikeTouch() && check1.getBikeTouch()) {
				messageEnd.setText("GAME OVER ");
				messageEnd.draw(getCanvas());
				messageRestart.setText("PRESS C TO RESTART FROM CHECKPOINT");
				messageRestart.draw(getCanvas());
			}
		}
		
		// If you cross the finish line
		if(finish.getBikeTouch()) {
			// draw a text and stop the bike
			messageEnd.setText("YOU WIN");
			messageEnd.draw(getCanvas());
			// Put the motorSpeed to ZERO to stop the bike even if you press any Key control
			bike.stop();
			//Destroy the emitter 
			bike.destroyEmitter();
			// Do the difference of the timer to 100, multiply by 10 and add it to the total score
			if (this.timer < 100 && finish1time ) {
				this.countScore += (100 - Math.round(timer)) * 10;
				s = countScore + " PTS ";
				messageScore.setText(s);
				messageScore.setRelativeTransform(Transform.I.translated(0.0f, -1.3f));
				messageScore.setFontSize(0.1f);
				finish1time = false;
			} else {
				messageScore.setRelativeTransform(Transform.I.translated(0.0f, -1.3f));
				messageScore.setFontSize(0.1f);
				finish1time = false;
			}
		}
				
		if (getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
			bike.setLook(!bike.getLook());
			bike.changeWayOfLook();
			bike.relaxMotor();
		}
		
	
		if ((!(getKeyboard().get(KeyEvent.VK_UP).isDown()))
				&& (!(getKeyboard().get(KeyEvent.VK_DOWN).isDown()))) {
			if(!finish.getBikeTouch()) {
				bike.relaxMotor();
			}
		}
				
		if (getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
			bike.stop();
		}
				
				
		if (getKeyboard().get(KeyEvent.VK_UP).isDown()) {
			if (!finish.getBikeTouch()) {
				bike.accel();
			}
		}
		
		if (getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
			bike.getEntity().applyAngularForce(10.0f);
		}
		
		if (getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
			bike.getEntity().applyAngularForce(-10.0f);
		}
		
		// If you press R, restart the game (from the start)
		if (getKeyboard().get(KeyEvent.VK_R).isPressed()) {
			// Delete all Actor of the actual Game
			this.deleteAllActor();
			// Define the bike position to his initial position
			bikePosition = new Vector(-180.0f, 0.0f);
			// Set armUp to false
			bike.setArmup(false);
			// Restart the counter of Star collected and actualize the String s
			this.countScore = 0;
			this.s = countScore + "PTS";
			// Restart the timer of the checkpoint
			timerCheck = 0.0;
			// Restart the timer of spike 1 and 2 to 0
			this.timerSpikes1 = 0.0;
			this.timerSpikes2 = 0.0;
			// Restart the timer of time
			this.timer = 0.0;
			// Restart the game
			this.begin(((Window)this.getCanvas()), this.getFileSystem());
		}
		
		// If you have crossed the checkpoint and not finish the game 
		if(check1.getBikeTouch() && !finish.getBikeTouch()) {
			
			// If cross the CheckPoint check1, assign a position (in case of restart) to the bike
			bikePosition = check1.getPosition();
			
			// If you press C
			if (getKeyboard().get(KeyEvent.VK_C).isPressed()) {
				// Delete all Actor of the game
				this.deleteAllActor();
				// Actualize the position of the bike to the position of the checkpoint
				this.bikePosition = check1.getPosition();
				//Set armUp to false
				bike.setArmup(false);
				// If you collect the second star, substract 100 points to your score
				if (collect2) {
					this.countScore -= 100;
					s = countScore + " PTS ";
					collect2 = false;
				}
				
				// Restart the timer of spikes 1 and 2 to 0
				this.timerSpikes1 = 0.0;
				this.timerSpikes2 = 0.0;
				// Set finish1time to true
				// Restart the game (with the settings set before
				this.begin(((Window)this.getCanvas()), this.getFileSystem());
				// Destroy the Star 1 because it can't be accessible again if you cross the checkpoint 
				this.star1.destroy();
			}
			
			// Add 0.0001 s to timer check
			timerCheck += deltaTime;
			// If timer check is < 3.0 s, put the arm up and display a message 
			 if (timerCheck < 3.0) {
				 bike.setArmup(true);
				 bike.armUp();
				 messageCheckpoint.draw(getCanvas());
			 } else {
				 bike.setArmup(false);
				 bike.armUp();
			 }
		}
		
		// If you touch the star1 (collect it)
		if(star1.getBikeTouch()) {
			// add 100 points to you score
			countScore += 100;
			// Set the TouchBike setting to false (to not do this loop again) 
			star1.setBikeTouch(false);
			// Actualize String s and the text 
			s = countScore + " PTS";
			messageScore.setText(s);
			// Set collect1 to true
			collect1 = true;
			// Destroy the star
			star1.destroy();
		}
		
		// If you touch the star2 (collect it)
		if(star2.getBikeTouch()) {
			// add 100 points to you score
			countScore += 100;
			// Set the TouchBike setting to false (to not do this loop again) 
			star2.setBikeTouch(false);
			// Actualize String s and the text 
			s = countScore + " PTS";
			messageScore.setText(s);
			// Set collect2 to true
			collect2 = true;
			// Destroy the star
			star2.destroy();
		}
		
		//	If you touch fire, you die.
		if(flame1.getBikeTouch()){
			bike.setHit(true);
		}
		
		// If you touch the spikes 1, immobilize during 5sec
		if (spikes1.getBikeTouch()) {
			if(timerSpikes1 < 5.0) {
				timerSpikes1 += deltaTime;
				bike.stop();
				messageSpikes.draw(getCanvas());
			} else {
				spikes1.setBikeTouch(false);
			}
			
		}
		
		// If you touch the spikes 2, immobilize during 5sec
		if (spikes2.getBikeTouch()) {
			if(timerSpikes2 < 5.0) {
				timerSpikes2 += deltaTime;
				bike.stop();
				messageSpikes.draw(getCanvas());
			} else {
				spikes2.setBikeTouch(false);
			}	
		}
		
		// Until you don't finish the level, timer is enabled
		if (!finish.getBikeTouch()) {
			timer += deltaTime;
			time = Math.round(timer) + "SEC";
			messageTime.setText(time);
		} 
	
		// Draw the text link to the score of the player
		messageScore.draw(getCanvas());
		// Draw the timer
		messageTime.draw(getCanvas());

	}

}
