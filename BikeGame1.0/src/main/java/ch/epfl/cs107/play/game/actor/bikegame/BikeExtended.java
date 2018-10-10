/*
 *	Author:      Marc Watine
 *	Date:        Dec 4, 2017
 */

package ch.epfl.cs107.play.game.actor.bikegame;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.particle.Emitter;
import ch.epfl.cs107.play.game.actor.particle.ImageParticle;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
/*
 * Extends the class Bike. We want to add the Emitter to this bike
 * and want to keep the other "original" bike
 * 
 */
public class BikeExtended extends Bike {
	// We declare our Emitter and the particles that will be emitted by him
	private Emitter emitter;
	private ImageParticle particle;
	// We set the shape of our Emitter. He is polymorphique and can take
	// any shape
	private Shape shape = new Polygon (
			 new Vector(0.0f, 0.0f),
			 new Vector(-0.3f, 0.0f),
			 new Vector(-0.3f, 2),
			 new Vector(0,2)
			 );
	
	// BikeExtended's constructor
	public BikeExtended(ActorGame game, boolean fixed, Vector position) {
		// We call the super constructor of the class Bike: it will have all of bikes properties
		super(game, fixed, position);
		
		// And we add the specific particle to be emitted and the Emitter itself
		particle = new ImageParticle(game, "laser.blue.png", 0.1f, 0.1f, 1,1, new Vector(0f, 0f), new Vector(0f, 0f), new Vector(0, 0), 0,0, 0);
		emitter = new Emitter (game, false, position.add(0.0f, 0.f),particle,  150 , shape);
		
		// We then attach it to the bike as we did with the wheels 
		emitter.attach(getEntity(), new Vector (0f, -0.1f));
		
	}
	// BikeExtended's constructor without position
	public BikeExtended(ActorGame game, boolean fixed) {
		super(game, fixed);
		particle = new ImageParticle(game, "laser.blue.png", 0.1f, 0.1f, 1,1, new Vector(-75f, 3f), new Vector(5f, 5f), new Vector(0,0), 0, 0, 0);
		emitter = new Emitter (game, false, this.getPosition().add(0.0f, 0.f),particle,  150, shape );
		//emitter.attach(getEntity(), new Vector (0f, -0.1f));
		
	}
	// We Override the method destroy() of Bike to destroy the emitter too
	@Override
	public void destroy() {
		super.destroy();
		destroyEmitter();
	}
	
	// Method letting you destroy the emitter directly
	public void destroyEmitter() {
		emitter.destroy();
	}
	
	// We Override the method draw() of Bike to let the emitter draw the particles
	@Override
	public void draw (Canvas canvas) {
		super.draw(canvas);
		emitter.draw(canvas);
		
	}
	public void update(float deltaTime) {
		emitter.update(deltaTime);	
	}

}
