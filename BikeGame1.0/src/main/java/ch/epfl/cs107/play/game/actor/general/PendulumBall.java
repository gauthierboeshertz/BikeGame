/*
 *	Author:      Auguste Lefevre
 *	Date:        1 déc. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class PendulumBall extends GameEntity implements Actor {
	
	// Declare the attributes needed for the creation of a PendulumBall
	private Block topBlock;
	private Ball ball;
	private ShapeGraphics rope;
	
	// Public constructor of the PendulumBall
	public PendulumBall(ActorGame game, boolean fixed, Vector position, Vector TBlock, Vector ballPosition, float lenght) {
		super(game, fixed, position);

		// Create a block and a ball
		if (TBlock == null) {
			throw new NullPointerException("La position du bloc du pendule est nulle");
		}
		topBlock = new Block(game, true, TBlock,"metal.4.png" , 1.0f, 1.0f, false, 1.0f, 0.0f); 
		if (ballPosition == null) {
			throw new NullPointerException("La position de la balle du pendule est nulle");
		}
		ball = new Ball(game, false, ballPosition, "explosive.hollow.11.png", 0.0f);
		
		// Check the lenght
		if (lenght <= 0.0f) {
			throw new IllegalArgumentException("La taille de la corde du pendule est nulle ou inférieur a 0");
		}
		
		//Create a constraint between the block and the ball
		RopeConstraintBuilder ropeConstraintBuilder = getOwner().ropeConstraintBuilderConstructor();
        ropeConstraintBuilder.setFirstEntity(topBlock.getEntity());
        ropeConstraintBuilder.setFirstAnchor(new Vector(topBlock.getWidth()/2, topBlock.getHeight()/2));
        ropeConstraintBuilder.setSecondEntity(ball.getEntity());
        ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
        ropeConstraintBuilder.setMaxLength(lenght);
        ropeConstraintBuilder.setInternalCollision(false);
        ropeConstraintBuilder.build();
        
        graphicConstructor();
     
	}

	// method that create the graphic of the line between the block and the ball
	private void graphicConstructor() {
		Polyline line = new Polyline(
				topBlock.getPosition().add(topBlock.getWidth()/2, topBlock.getHeight()/2),
				ball.getPosition()
				);
		rope = new ShapeGraphics( line, Color.BLACK, Color.WHITE, 0.1f, 1.0f, 0.0f);		
	}
	
	
	@Override
	public void destroy() {
		this.ball.destroy();
		this.topBlock.destroy();
		super.destroy();
		this.getOwner().removeActor(this);
	}
	
	@Override
	public void draw(Canvas canvas) {
		topBlock.draw(canvas);
		ball.draw(canvas);
		// Actualize the location and the construction of the graphic of the rope
		graphicConstructor();
		rope.draw(canvas);
		
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

}
