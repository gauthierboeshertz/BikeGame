/*
 *	Author:      Auguste Lefevre
 *	Date:        22 nov. 2017
 */

package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.Vector;


/*
 * The super class of every object of a game.
 * Every object will derive of this class.
 * 
 */
public abstract class GameEntity {
	
	// Declare the two (unavoidable) variables of a GameEntity
	private Entity body;
	private ActorGame game;
	
	// Constructor of a GameEntity with initial position and his properties
	public GameEntity(ActorGame game, boolean fixed, Vector position) {
		if (game == null) {
			throw new NullPointerException ("The game entered is null");
		}
		this.game = game;
		if (position == null) {
			throw new NullPointerException ("The position entered is null");
		}
		body = game.entityConstructor(fixed, position);
	}
	
	// Constructor of a GameEntity without initial position
	public GameEntity(ActorGame game, boolean fixed) {
		if (game == null) {
			throw new NullPointerException ("The game entered is null");
		}
		this.game = game;
		body = game.entityConstructor(fixed, Vector.ZERO);
	}
	
	// Method that checks if the Part given in argument is a Part of the body (this) or not
	protected boolean partIn(Part part) {
		if (part.getEntity() == this.body) {
			return true;
		} else {
			return false;
		}
	}
	
	// Method that can destroy the Entity
	public void destroy() {
		body.destroy();
	}
	
	// a get-method witch return the entity
	public Entity getEntity() {
		return body;
	}
	
	// a get-method witch return the game (ActorGame) in which the actor evolute
	protected ActorGame getOwner() {
		return game;
	}

}
