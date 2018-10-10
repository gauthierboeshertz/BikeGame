/*
 *	Author:      Auguste Lefevre
 *	Date:        1 déc. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Ball extends GameEntity implements Actor {

	// Declare the attributes needed to the creation of a Ball
	private float radius = 0.5f;
	private ImageGraphics graphicBall;
	private ShapeGraphics shapeBall;
	private Circle circle;

	// Public constructor of a Ball with ImageGraphics
	public Ball(ActorGame game, boolean fixed, Vector position,String image, float depth) {
		super(game, fixed, position);
		partBuilderConstructor();
		
		if (image == null) {
			throw new NullPointerException ("The image name is null! we want to draw something... (Ball)");
		}
		graphicCreator(image, depth);
		
	}
	
	// Public constructor of a Ball with ShapeGraphics (and choose the radius)
	public Ball(ActorGame game, boolean fixed, Vector position, float radius, Color outsideColor, Color insideColor,
			float depth) {
		super(game, fixed, position);
		if (radius<=0) {
			throw new IllegalArgumentException ("The radius must be strictyly positive! ... (Ball)");
		}
		this.radius = radius;
		
		partBuilderConstructor();
		shapeGraphicCreator(outsideColor, insideColor);
		
	}

	
	// Method that builds the Part of the Ball
	private void partBuilderConstructor() {
		PartBuilder partBuilderFinish = getEntity().createPartBuilder();
		circle = new Circle(radius);
		partBuilderFinish.setShape(circle);
		partBuilderFinish.build();
	}

	// Method that creates the graphic of the Ball
	private void graphicCreator(String image, float depth) {
		graphicBall = new ImageGraphics(image, radius*2.0f, radius*2.0f, new Vector(radius, radius));
		graphicBall.setAlpha(1.0f);
		graphicBall.setDepth(depth);
		graphicBall.setParent(getEntity());
	}
	
	private void shapeGraphicCreator(Color out, Color in) {
		shapeBall = new ShapeGraphics(circle, out, in, 0.1f, 1.0f, 0.0f);
		shapeBall.setParent(getEntity());
	}
	
	@Override
	public void destroy() {
		super.destroy();
		getOwner().removeActor(this);
	}

	
	@Override
	public void draw(Canvas canvas) {
		graphicBall.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}

	
}
