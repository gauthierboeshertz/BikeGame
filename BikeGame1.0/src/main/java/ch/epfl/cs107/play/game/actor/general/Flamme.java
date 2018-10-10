/*
 *	Author:      Auguste Lefevre
 *	Date:        1 déc. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.particle.Emitter;
import ch.epfl.cs107.play.game.actor.particle.ImageParticle;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Flamme extends Trigger implements Actor{
	
	// Declare the attributes needed to create a Flamme
	private float width;
	private float height;
	private ImageGraphics graphicFlam;
	private Polygon polygon;
	private Emitter emitter;

	
	// Public constructor of Flamme
	public Flamme(ActorGame game, boolean fixed, Vector position, float width, float height) {
		super(game, fixed, position);
		if (width<=0) {
			throw new IllegalArgumentException ("The width must be positive ! (Flamme)");
		}
		this.width = width;
		if (height<=0) {
			throw new IllegalArgumentException ("The height must be positive ! (Flamme)");
		}
	    this.height = height;
		partBuilderConstructor();		
		graphicCreator("flam.png");
		addContact(listener);
		
		ImageParticle particle = new ImageParticle(game, "fireball.png", 0.2f, 0.2f, 1,1, new Vector(0f, 0f), new Vector(0f, 4f), new Vector(0, 0), 0,0, 0);
		emitter = new Emitter(game, true, position, particle, 150, polygon);
	}
	

	// cf. Trigger
	@Override
	protected void partBuilderConstructor() {
		PartBuilder partBuilderFinish = getEntity().createPartBuilder();
		polygon = new Polygon
				  (new Vector(0.0f, 0.0f),
				   new Vector(width, 0.0f),
				   new Vector(width, height),
				   new Vector(0.0f, height));
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
		graphicFlam = new ImageGraphics(image, width, height);
		graphicFlam.setAlpha(1.0f);
		graphicFlam.setDepth(0.0f);
		graphicFlam.setParent(getEntity());
	}

	// cf. Trigger
	@Override
	protected void addContact(ContactListener listener) {
		this.getEntity().addContactListener(listener);				
	}
	
	@Override
	public void destroy() {
		super.destroy();
		this.getOwner().removeActor(this);
		emitter.destroy();
	}
	
	@Override
	public void draw(Canvas canvas) {
		graphicFlam.draw(canvas);
		emitter.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}
	
	@Override
	public void update (float deltaTime) {
		emitter.update(deltaTime);
	}

	
}
