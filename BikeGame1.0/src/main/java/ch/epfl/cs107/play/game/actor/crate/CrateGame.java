/*
 *	Author:      Auguste Lefevre
 *	Date:        23 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.actor.crate.Crate;

/*
 * The Crate object. It's a "GameEntity" with
 * with some particular added methods and variables
 * that differentiates the crate from any GameEntity
 * 
 */
public class CrateGame extends ActorGame {
	
	
	// Redefine the method begin to creates 3 Crates
	public boolean begin(Window window, FileSystem fileSystem) {
		
		// Apply the method begin of ActorGame by using
		super.begin(window, fileSystem);
		
		//Create 3 crate by using the constructor of Crate
		Crate crate1 = new Crate(this, false, new Vector(0.0f, 5.0f), "box.4.png", 1.0f, 1.0f);
		Crate crate2 = new Crate(this, false, new Vector(0.2f, 7.0f), "box.4.png", 1.0f, 1.0f);
		Crate crate3 = new Crate(this, false, new Vector(2.0f, 6.0f), "box.4.png", 1.0f, 1.0f);
		
		
		// Add the three crate (Actor) to the listActor of ActorGame by using the method addActor
		addActor(crate1);
		addActor(crate2);
		addActor(crate3);
		return true;
	}
	
}
