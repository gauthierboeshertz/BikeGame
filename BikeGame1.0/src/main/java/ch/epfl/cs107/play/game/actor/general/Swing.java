/*
 *	Author:      Auguste Lefevre
 *	Date:        3 déc. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Swing extends GameEntity implements Actor {
	
	// Declare the attributes needed to create a Swing
	private Block botBlock;
	private Block plank;
	
	// Public constructor of Swing
	public Swing(ActorGame game, boolean fixed, Vector position, Vector botBlockPosition, Vector plankPosition) {
		
		super(game, fixed, position);
		
		// Create 2 Block
		if (botBlockPosition == null) {
			throw new NullPointerException ("The position of the top Block is null! (Swing)");
		}
		botBlock = new Block(game, true, botBlockPosition, "wood.1.png", 1.0f, 1.0f, false, 1.0f, 0.0f);
		if (plankPosition == null) {
			throw new NullPointerException ("The position of the plank is null ! (Swing)");
		}
		plank = new Block(game, false, plankPosition, "wood.3.png", 9.0f, 0.2f, false, 1.0f, 0.0f);
			
		// Create a constraint between the block and the plank
		swingConstraintCreator(botBlock, plank);
		
	}
	
	// Method that create the constraint needed to create a swing 
	public void swingConstraintCreator(Block b1, Block b2) {
		
		 RevoluteConstraintBuilder revoluteConstraintBuilder = getOwner().revoluteConstraintConstructor();
	     revoluteConstraintBuilder.setFirstEntity(b1.getEntity());
	     revoluteConstraintBuilder.setFirstAnchor(new Vector(b1.getWidth()/2, (b1.getHeight()*7)/4));
         revoluteConstraintBuilder.setSecondEntity(b2.getEntity());
         revoluteConstraintBuilder.setSecondAnchor(new Vector(b2.getWidth()/2, b2.getHeight()/2));
         revoluteConstraintBuilder.setInternalCollision(true);
         revoluteConstraintBuilder.build(); 
	}
	
	
	@Override
	public void destroy() {
		this.plank.destroy();
		this.botBlock.destroy();
		super.destroy();
		this.getOwner().removeActor(this);
	}

	@Override
	public void draw(Canvas canvas) {
		plank.draw(canvas);
		botBlock.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		return null;
	}

	@Override
	public Vector getVelocity() {
		return null;
	}
	

}
