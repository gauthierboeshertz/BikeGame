/*
 *	Author:      Auguste Lefevre
 *	Date:        27 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Start extends GameEntity implements Actor {

	// Declare the attributes needed for the creation of a Start
	private final static float START_WIDTH = 1.0f;
	private final static float START_HEIGHT = 2.0f;
	private ImageGraphics graphicStart;
	private Polygon polygon;
	
		// Start's constructor with initial position 
		public Start(ActorGame game, boolean fixed, Vector position, String image) {
			super(game, fixed, position);
			partBuilderConstructor();
			if (image == null) {
				throw new NullPointerException ("The image can't be null... We want to draw something! (Start)");
			}
			graphicCreator(image);
		}
		
		// Method witch create the Part of the Start object
		private void partBuilderConstructor() {
			PartBuilder partBuilderFinish = getEntity().createPartBuilder();
			polygon = new Polygon
					  (new Vector(0.0f, 0.0f),
					   new Vector(START_WIDTH, 0.0f),
					   new Vector(START_WIDTH, START_HEIGHT),
					   new Vector(0.0f, START_HEIGHT));
			partBuilderFinish.setShape(polygon);
			partBuilderFinish.setFriction(1.0f);
			// This line defines the Object as "ghost", useful to not create physic contact
			// with the object of the game 
			partBuilderFinish.setGhost(true);
			partBuilderFinish.build();
		}
		
		// Method witch creates the graphic of the Start Object
		private void graphicCreator(String image) {
			graphicStart = new ImageGraphics(image, START_WIDTH, START_HEIGHT);
			graphicStart.setAlpha(1.0f);
			graphicStart.setDepth(0.0f);
			graphicStart.setParent(getEntity());
		}
		
		// Redefine the method destroy by destroying the graphic, the shape and delete it from the list of actor of the game
		@Override
		public void destroy() {
			super.destroy();
			getOwner().removeActor(this);		
		}

		// Redefine the method draw by drawing the graphic
		@Override
		public void draw(Canvas canvas) {
			graphicStart.draw(canvas);	
			}
			
		// Define the method getTransform return the Transform of the entity
		@Override
		public Transform getTransform() {
			return getEntity().getTransform();
		}

		// Define the method getVelocity by return the Velocity of the entity
		@Override
		public Vector getVelocity() {
			return getEntity().getVelocity();
		}
	
}
