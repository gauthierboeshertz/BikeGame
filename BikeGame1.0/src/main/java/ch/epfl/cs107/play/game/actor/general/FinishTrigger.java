/*
 *	Author:      Auguste Lefevre
 *	Date:        27 nov. 2017
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

public class FinishTrigger extends Trigger implements Actor {
	
	// Attributes necessary to functions of Finish
	private static final float FINISH_WIDTH = 1.0f;
	private static final float FINISH_HEIGHT = 2.0f;
	private ImageGraphics graphicFinish;
	private Polygon polygon;
		
	
	// Finish's constructor with initial position 
	public FinishTrigger(ActorGame game, boolean fixed, Vector position, String image) {
		super(game, fixed, position);
		partBuilderConstructor();
		if (image == null) {
			throw new NullPointerException ("The image can't be null... We want to draw something!(FinishTrigger)");
		}
		graphicCreator(image);
		addContact(listener);
	}


	// cf. Trigger
	@Override
	protected void partBuilderConstructor() {
		PartBuilder partBuilderFinish = getEntity().createPartBuilder();
		polygon = new Polygon
				  (new Vector(0.0f, 0.0f),
				   new Vector(FINISH_WIDTH, 0.0f),
				   new Vector(FINISH_WIDTH, FINISH_HEIGHT),
				   new Vector(0.0f, FINISH_HEIGHT));
		partBuilderFinish.setShape(polygon);
		partBuilderFinish.setFriction(1.0f);
		// This line defines the Object as "ghost", useful to not create physic contact
		// with the object of the game 
		partBuilderFinish.setGhost(true);
		partBuilderFinish.build();
	}


	// cf. Trigger
	@Override
	protected void graphicCreator(String image) {
		graphicFinish = new ImageGraphics(image, FINISH_WIDTH, FINISH_HEIGHT);
		graphicFinish.setAlpha(1.0f);
		graphicFinish.setDepth(0.0f);
		graphicFinish.setParent(getEntity());
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
			graphicFinish.draw(canvas);	
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
