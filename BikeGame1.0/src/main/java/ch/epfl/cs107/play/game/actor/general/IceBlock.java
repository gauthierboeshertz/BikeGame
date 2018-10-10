/*
 *	Author:      Auguste Lefevre
 *	Date:        28 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.particle.Emitter;
import ch.epfl.cs107.play.game.actor.particle.ImageParticle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class IceBlock extends GameEntity implements Actor {

	
	private float width;
	private float height;
	private ImageGraphics graphicIce;
	private Polygon polygon;
	private Emitter emitter;
	
	// IceBlock constructor with initial position 
		public IceBlock(ActorGame game, boolean fixed, Vector position, float width, float height) {
			super(game, fixed, position);
			if (width<=0) {
				throw new IllegalArgumentException ("The width must be positive ! (IceBlock) ");
			}
			this.width = width;
			if (height<=0) {
				throw new IllegalArgumentException ("The height must be positive ! (IceBlock)");
			}
		    this.height = height;
			partBuilderConstructor();
			graphicCreator("ice.png");
			
			ImageParticle particle = new ImageParticle(game, "smoke.white.1.png", 0.1f, 0.1f,1, 1, new Vector (0,0), new Vector (2, -10), new Vector(0, 1), 0,0,0 );
			emitter = new Emitter(game, true, position.add(new Vector(0,10)), particle, 400, polygon);
			emitter.attach(getEntity(), position.add(new Vector(0,10)));
		}
		
		// Method that create the Part of the IceBlock object
		private void partBuilderConstructor() {
			PartBuilder partBuilderFinish = getEntity().createPartBuilder();
			polygon = new Polygon
					  (new Vector(0.0f, 0.0f),
					   new Vector(width, 0.0f),
					   new Vector(width, height),
					   new Vector(0.0f, height));
			partBuilderFinish.setShape(polygon);
			// Set the friction to 0 because it's ice ahahah
			partBuilderFinish.setFriction(0.001f);
			partBuilderFinish.build();
		}
		
		// Method that creates the graphic of the IceBlock Object
		private void graphicCreator(String image) {
			graphicIce = new ImageGraphics(image, width, height);
			graphicIce.setAlpha(1.0f);
			graphicIce.setDepth(1.0f);
			graphicIce.setParent(getEntity());
		}
		
		// Redefine the method destroy by destroying the graphic, the shape and delete it from the list of actor of the game
		@Override
		public void destroy() {
			super.destroy();
			getOwner().removeActor(this);
			this.emitter.destroy();
		}

		// Redefine the method draw by drawing the graphic
		@Override
		public void draw(Canvas canvas) {
			graphicIce.draw(canvas);	
			emitter.draw(canvas);
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
		
		@Override
		public void update (float deltaTime) {
			emitter.update(deltaTime);
		}
	
}
