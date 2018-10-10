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

public class CheckPoint extends Trigger implements Actor {
	
	// Declare the attriubutes needed for the creation of CheckPoint
	private static final float CHECK_WIDTH = 1.0f;
	private static final float CHECK_HEIGHT = 2.0f;
	private ImageGraphics graphicCheck;
	private Polygon polygon;
	
	// Public constructor of CheckPoint
	public CheckPoint(ActorGame game, boolean fixed, Vector position, String image) {
		super(game, fixed, position);
		partBuilderConstructor();
		if (image == null) {
			throw new NullPointerException ("The image can't be null... We want to draw something! (CheckPoint)");
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
				   new Vector(CHECK_WIDTH, 0.0f),
				   new Vector(CHECK_WIDTH, CHECK_HEIGHT),
				   new Vector(0.0f, CHECK_HEIGHT));
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

		graphicCheck = new ImageGraphics(image, CHECK_WIDTH, CHECK_HEIGHT);
		graphicCheck.setAlpha(1.0f);
		graphicCheck.setDepth(0.0f);
		graphicCheck.setParent(getEntity());
	}

	// cf. Trigger
	@Override
	protected void addContact(ContactListener listener) {
		this.getEntity().addContactListener(listener);		
	}
	
	@Override
	public void destroy() {
		super.destroy();
		getOwner().removeActor(this);		
	}

	@Override
	public void draw(Canvas canvas) {
		this.graphicCheck.draw(canvas);
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
