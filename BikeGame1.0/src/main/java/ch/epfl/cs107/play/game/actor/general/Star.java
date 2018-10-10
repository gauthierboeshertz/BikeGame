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

public class Star extends Trigger implements Actor{
	
	// Declare the attributes needed to the creation of a Star
	private final static float STAR_WIDTH = 1.0f;
	private final static float STAR_HEIGHT = 1.0f;
	private ImageGraphics graphicStar;
	private Polygon polygon;
	
	// public constructor of a Star
	public Star(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		partBuilderConstructor();
		graphicCreator("star.gold.png");
		addContact(listener);
	}
	

	// cf. Trigger
	@Override
	protected void partBuilderConstructor() {
		PartBuilder partBuilderFinish = getEntity().createPartBuilder();
		polygon = new Polygon
				  (new Vector(0.0f, 0.0f),
				   new Vector(STAR_WIDTH, 0.0f),
				   new Vector(STAR_WIDTH, STAR_HEIGHT),
				   new Vector(0.0f, STAR_HEIGHT));
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
		graphicStar = new ImageGraphics(image, STAR_WIDTH, STAR_HEIGHT);
		graphicStar.setAlpha(1.0f);
		graphicStar.setDepth(0.0f);
		graphicStar.setParent(getEntity());
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

	@Override
	public void draw(Canvas canvas) {
		this.graphicStar.draw(canvas);
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
