/*
 *	Author:      Auguste Lefevre
 *	Date:        28 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Sign extends GameEntity implements Actor {
	
		// Declare the attributes needed to the creation of a Sign
		private final static float SIGN_WIDTH = 2.0f;
		private final static float SIGN_HEIGHT = 2.0f;
		private ImageGraphics graphicSign;
		private Polygon polygon;
	
	    // Sign constructor with initial position 
		public Sign(ActorGame game, boolean fixed, Vector position, String image) {
			super(game, fixed, position);
			partBuilderConstructor();
			if (image == null) {
				throw new NullPointerException ("The image can't be null... We want to draw something!(Sign)");
			}
			graphicCreator(image);
		
		}
		
		// Method that create the Part of the Sign object
		private void partBuilderConstructor() {
			PartBuilder partBuilderFinish = getEntity().createPartBuilder();
			polygon = new Polygon
					  (new Vector(0.0f, 0.0f),
					   new Vector(SIGN_WIDTH, 0.0f),
					   new Vector(SIGN_WIDTH, SIGN_HEIGHT),
					   new Vector(0.0f, SIGN_HEIGHT));
			partBuilderFinish.setShape(polygon);
			partBuilderFinish.setFriction(1.0f);
			// This line defines the Object as "ghost", useful to not create physic contact
			// with the object of the game 
			partBuilderFinish.setGhost(true);
			partBuilderFinish.build();
		}
		
		// Method witch creates the graphic of the Finish Object
		private void graphicCreator(String image) {
			graphicSign = new ImageGraphics(image, SIGN_WIDTH, SIGN_HEIGHT);
			graphicSign.setAlpha(1.0f);
			graphicSign.setDepth(-1.0f);
			graphicSign.setParent(getEntity());
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
			graphicSign.draw(canvas);	
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
