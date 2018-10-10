/*
 *	Author:      Auguste Lefevre
 *	Date:        22 nov. 2017
 */

package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Positionable;

public interface Actor extends Graphics, Positionable{

	/**
	 * Simulates a single time step.
	 * @param deltaTime elapsed time since last update, in
	seconds, non-negative */
	public default void update(float deltaTime) {
	// By default, actors have nothing to update
	}
	public default void destroy(){
	// By default, actors have nothing to destroy
	}
	
	// Add for the contact of finish line and bike
	public Entity getEntity();
}

