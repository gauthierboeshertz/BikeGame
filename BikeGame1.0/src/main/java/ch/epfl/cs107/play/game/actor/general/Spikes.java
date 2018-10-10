/*
 *	Author:      Auguste Lefevre
 *	Date:        3 déc. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Spikes extends Trigger implements Actor {
	
		// Attributes necessary to functions of Spikes
		private static final float PICS_WIDTH = 5.0f;
		private static final float PICS_HEIGHT = 1.0f;
		private ImageGraphics graphicPics;
		private Polygon polygon;
			
		
		// Spikes's constructor with initial position 
		public Spikes(ActorGame game, boolean fixed, Vector position) {
			super(game, fixed, position);
			partBuilderConstructor();
			graphicCreator("spikes.png");
			addContact(listener);
		}


		// cf. Trigger
		@Override
		protected void partBuilderConstructor() {
			PartBuilder partBuilderFinish = getEntity().createPartBuilder();
			polygon = new Polygon
					  (new Vector(0.0f, 0.0f),
					   new Vector(PICS_WIDTH, 0.0f),
					   new Vector(PICS_WIDTH, PICS_HEIGHT),
					   new Vector(0.0f, PICS_HEIGHT));
			partBuilderFinish.setShape(polygon);
			// This line defines the Object as "ghost", useful to not create physic contact
			// with the object of the game 
			partBuilderFinish.setGhost(true);
			partBuilderFinish.build();
		}


		// cf. Trigger
		@Override
		protected void graphicCreator(String image) {
			graphicPics = new ImageGraphics(image, PICS_WIDTH, PICS_HEIGHT);
			graphicPics.setAlpha(1.0f);
			graphicPics.setDepth(0.0f);
			graphicPics.setParent(getEntity());
		}


		// cf. Trigger
		@Override
		protected void addContact(ContactListener listener) {
			this.getEntity().addContactListener(listener);		
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
			graphicPics.draw(canvas);	
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
