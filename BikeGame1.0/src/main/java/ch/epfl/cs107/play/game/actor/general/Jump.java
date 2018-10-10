/*
 *	Author:      Auguste Lefevre
 *	Date:        27 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Jump extends GameEntity implements Actor {

	// Declare the attributes needed to the creation of a Jump
	private Polygon jumpPart;
	private ShapeGraphics graphicJump;
	
	// Public constructor of a Jump
	public Jump(ActorGame game, boolean fixed, Vector position, float width, float height) {
		super(game, fixed, position);
		if (width<=0) {
			throw new IllegalArgumentException ("The width must be positive ! (Jump) ");
		}
		if (height<=0) {
			throw new IllegalArgumentException ("The height must be positive ! (Jump) ");
		}
		partBuilderConstructor(width, height);
		graphicConstructor();
	}
	
	// Method that builds the Part of the Jump
	private void partBuilderConstructor(float width, float height) {
		PartBuilder partBuilderFinish = getEntity().createPartBuilder();
		jumpPart = new Polygon
				  (new Vector(0.0f, 0.0f),
				   new Vector(width, 0.0f),
				   new Vector(width, height));
		partBuilderFinish.setShape(jumpPart);
		partBuilderFinish.setFriction(10.0f);
		partBuilderFinish.build();
	}
	
	// Method that creates the graphic of the Jump
	private void graphicConstructor() {
		graphicJump = new ShapeGraphics(jumpPart, Color.DARK_GRAY, Color.DARK_GRAY, 0.1f, 1.0f, 0.0f);
		graphicJump.setParent(getEntity());
	}
	
	@Override
	public void destroy() {
		super.destroy();
		this.getOwner().removeActor(this);
	}

	@Override
	public void draw(Canvas canvas) {
		this.graphicJump.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		return this.getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return this.getEntity().getVelocity();
	}


}
