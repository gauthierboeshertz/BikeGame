/*
 *	Author:      Auguste Lefevre
 *	Date:        26 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/*
 * The Terrain object. It's a "GameEntity" with
 * with some particular added methods and variables
 * that differentiates the Terrain from any GameEntity.
 * This class is in the package /general because
 * this object could be used in any game.
 * 
 */
public class Finish extends GameEntity implements Actor {
	
	// Attributes that are needed in the class Finish
	private static final float FINISH_WIDTH = 1.0f;
	private static final float FINISH_HEIGHT = 2.0f;
	private ImageGraphics graphicFinish;
	private Polygon polygon;
	// false if the bike did not cross the finish line, true if the bike crossed it
	private boolean bikeFinish = false;
	
	// Create a special Contact Listener specific to the class 
	// Finish by using the creation of an anonymous class
	ContactListener listener = new ContactListener() { 
		@Override
		public void beginContact(Contact contact) {
			// If the entity that is in contact is the bike :
			if (getOwner().getPayLoad() != null) {
				if (contact.getOther().getEntity() == getOwner().getPayLoad().getEntity()) {
					bikeFinish = true;
				} else {
					return;
				}
			}
		}
		
		@Override
		// Nothing specific must happen if the contact ends. The game will already have "finished"
		public void endContact(Contact contact) {}
		};
		
	// Finish's constructor with initial position 
	public Finish(ActorGame game, boolean fixed, Vector position, String image) {
		super(game, fixed, position);
		partBuilderConstructor();
		if (image == null) {
			throw new NullPointerException ("The image can't be null... We want to draw something!(Finish)");
		}
		graphicCreator(image);
		addContact(listener);
	}
	
	// Method that creates the Part of the Finish object
	private void partBuilderConstructor() {
		PartBuilder partBuilderFinish = getEntity().createPartBuilder();
		polygon = new Polygon
				  (new Vector(0.0f, 0.0f),
				   new Vector(FINISH_WIDTH, 0.0f),
				   new Vector(FINISH_WIDTH, FINISH_HEIGHT),
				   new Vector(0.0f, FINISH_HEIGHT));
		partBuilderFinish.setShape(polygon);
		// This line defines the Object as "ghost", useful to not create physic contact
		// with the object of the game 
		partBuilderFinish.setGhost(true);
		partBuilderFinish.build();
	}
	
	// Method that creates the graphical representation of Finish 
	private void graphicCreator(String image) {
		graphicFinish = new ImageGraphics(image, FINISH_WIDTH, FINISH_HEIGHT);
		graphicFinish.setAlpha(1.0f);
		graphicFinish.setDepth(0.0f);
		graphicFinish.setParent(getEntity());
	}
	
	// Method used to add and link a ContactListener with the entity associated to Finish
	private void addContact(ContactListener listener) {
		this.getEntity().addContactListener(listener);
	}
	
	// Method that returns bikeFinish (that determines if the bike crossed the finish line or not)
	public boolean getBikeFinish() {
		return bikeFinish;
	}
	
	
	
	// Redefine the method destroy. Destroys the graphic, the shape and delete it from the list of actor of the game
	@Override
	public void destroy() {
		super.destroy();
		getOwner().removeActor(this);		
	}

	// Redefines the method draw by drawing the graphics of the terrain
	@Override
	public void draw(Canvas canvas) {
		graphicFinish.draw(canvas);	
		}
		
	// Defines the method getTransform that will be returning the Transform of the entity
	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	// Defines the method getVelocity that will be returning the Velocity of the entity
	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}
}
