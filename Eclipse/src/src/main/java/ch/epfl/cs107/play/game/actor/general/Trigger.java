/*
 *	Author:      Auguste Lefevre
 *	Date:        27 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Vector;

/*
 * Super class of all other class witch defines object 
 * that are ghost, but detect the collision with the the 
 * principal Actor (the Bike for us) 
 */
public abstract class Trigger extends GameEntity {
	
	
	private boolean bikeTouch = false;

	public Trigger(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
	}
	
	// Create a special Contact Listener specific to the class 
	// Finish by using the creation of an anonymous class
	ContactListener listener = new ContactListener() { 
		@Override
		public void beginContact(Contact contact) {
			// If the entity that is in contact is the bike :
			if (getOwner().getPayLoad() != null) {
				if (contact.getOther().getEntity() == getOwner().getPayLoad().getEntity()) {
					bikeTouch = true;
				} else {
					return;
				}
			}
		}
			
		@Override
		public void endContact(Contact contact) {}
		};
		
		// Method witch create the Part of the Finish object
		protected abstract void partBuilderConstructor();
			
		// Function witch is use to create an ImageGraphic
		protected abstract void graphicCreator(String image); 
			
		// Method uses to add link a ContactListener with the entity Finish Object
		protected abstract void addContact(ContactListener listener);
			
		// Method witch returns bikeFInish (witch determines if the bike cross the Finish line or not)
		public boolean getBikeTouch() {
			return bikeTouch;
		}
			
			
		// Method to set getBikeTouch
		public void setBikeTouch(boolean touch) {
			this.bikeTouch = touch;
		}
	

}
