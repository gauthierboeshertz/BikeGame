/*
 *	Author:      Auguste Lefevre
 *	Date:        24 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.bikegame;

import java.awt.Color;
import java.util.ArrayList;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Finish;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;


public class BikeGame extends ActorGame {
	
	// Declares and initializes a list of vectors used for the creation of Terrain
	private ArrayList<Vector> listVector = new ArrayList<Vector>();

	// Declares the different object of the Game that we need to have access (to use their methods)
	private Bike bike;
	private Finish finish;
	
	// Declares the message "messageEnd" and creates a method which is called in the method begin to initialize this message
	TextGraphics messageEnd;
	private void textFinish(String texte) {
		messageEnd = new TextGraphics(texte, 0.2f, Color.RED, Color.WHITE, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		messageEnd.setParent(getCanvas());
		messageEnd.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}
	
	// Declare the message "messageReset" and creates a method which is called in the method begin to initialize this message
	TextGraphics messageReset;
	private void textReset(String texte) {
		messageReset = new TextGraphics(texte, 0.05f, Color.WHITE, Color.WHITE, 0.02f, true, true, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		messageReset.setParent(getCanvas());
		messageReset.setRelativeTransform(Transform.I.translated(0.0f, -1.2f));
	}
	
	// Method that adds a vector to the list listVector to create the Terrain
	private void addVector(float a, float b) {
		Vector vector = new Vector(a, b);
		listVector.add(vector);
	}
	
	/*
	 * Redefinition of the method begin of the class ActorGame.
	 * We add the different object of the game, the Vector use for the Terrain
	 * and the different texts.
	 */
	
	public boolean begin(Window window, FileSystem fileSystem) {
		// Calls the function begin of the super class ActorGame
		super.begin(window, fileSystem);
			
		// Add the vectors to listVector to create Terrain
		this.addVector(-1000.0f, -1000.0f);
		this.addVector(-1000.0f, 0.0f);
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
		
		// Creates the Terrain
		Terrain terrain = new Terrain(this, true, listVector);
		this.addActor(terrain);
		
		// Initialize bike
		bike = new Bike(this, false, new Vector(5.0f, 3.0f));
		this.addActor(bike);
		// Set the ViewCandidate to the bike (We want the camera to be centered on the bike)
		setViewCandidate(bike);
		
		// Create 3 crates 
		Crate crate1 = new Crate(this, false, new Vector(-1.0f, 5.0f), "box.4.png", 1.0f, 1.0f);
		Crate crate2 = new Crate(this, false, new Vector(0.2f, 7.0f), "box.4.png", 1.0f, 1.0f);
		Crate crate3 = new Crate(this, false, new Vector(2.0f, 6.0f), "box.4.png", 1.0f, 1.0f);
		this.addActor(crate1);
		this.addActor(crate2);
		this.addActor(crate3);
		
		// Initialize the finish line
		finish = new Finish(this, true, new Vector(-10.0f, 0.0f), "flag.red.png");
		this.addActor(finish);
		
		// Initialize the different text used in the game
		textFinish("Default");
		textReset("PRESS_R_TO_RESET");
		
		// catch the error throws and write a message in the console with more information about the error
		}catch(NullPointerException e ) {
			System.err.println(" [ " + e.getMessage() + " ] ");
		}
		catch(IllegalArgumentException e) {
			System.err.println(" [ " + e.getMessage() + " ] ");
		}

		return true;
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
			// If the bike's hit box is hit by something not "allowed"
			if(bike.getHit()) {
				// We destroy the bike (cf. overrided destroy method of bike)
				bike.destroy();
				// If the bike hasn't crossed the line we display 2 messages (end and reset message)
				if (!finish.getBikeFinish()) {
					messageEnd.setText("GAME OVER ");
					messageEnd.draw(getCanvas());
					messageReset.draw(getCanvas());
				}
			}
			
			// If the bike crosses the finish line we draw a text and stop the bike
			if(finish.getBikeFinish()) {
				messageEnd.setText("YOU WIN");
				messageEnd.draw(getCanvas());
				bike.stop();
			}
			
			// If you press SPACE, the character changes the way it is looking
			// (and we relax the wheel to have more control on the bike)
			if (getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
				bike.setLook(!bike.getLook());
				bike.changeWayOfLook();
				bike.relaxMotor();
			}
					
			// If keyevent UP and DOWN are not down, we relax the motor	
			if ((!(getKeyboard().get(KeyEvent.VK_UP).isDown()))
					&& (!(getKeyboard().get(KeyEvent.VK_DOWN).isDown()))) {
				if(!finish.getBikeFinish()) {
					bike.relaxMotor();
				}
			}
			
			// If the keyevent DOWN is down, stop the bike (cf. method stop of Bike)
			if (getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
				bike.stop();
			}
					
			// If the keyevent UP is pressed down and the finish line has not been crossed: 
			// give speed to the bike (cf. method accel of Bike)	
			if (getKeyboard().get(KeyEvent.VK_UP).isDown()) {
				if (!finish.getBikeFinish()) {
					bike.accel();
				}
			}
			
			
			// Apply a angular force on the bike if you press down LEFT
			if (getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
				bike.getEntity().applyAngularForce(10.0f);
			}
			
			// Apply a angular force on the bike if you press down RIGHT
			if (getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
				bike.getEntity().applyAngularForce(-10.0f);
			}
			
			// If you press R, restart the game (from the start, cf. methods deleteActor of ActorGame)
			if (getKeyboard().get(KeyEvent.VK_R).isPressed()) {
				this.deleteAllActor();
				this.begin(((Window)this.getCanvas()), this.getFileSystem());
			}

	}
}