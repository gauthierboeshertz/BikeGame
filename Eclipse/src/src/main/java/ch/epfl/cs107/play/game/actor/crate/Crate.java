/*
 *	Author:      Auguste Lefevre
 *	Date:        23 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/*
 * The Crate object. It's a "GameEntity" with
 * with some particular added methods and variables
 * that differentiates the crate from any GameEntity
 * 
 */
public class Crate extends GameEntity implements Actor {
	
	// Declare the useful variable for the creation of a Crate
	private ImageGraphics graphicCrate;
	private float crateWidth;
	private float crateHeight;
	private Polygon polygon;
	
	// Constructor of Crate with the initial position
	public Crate(ActorGame game, boolean fixed,Vector position, String image, float width, float height) {
		super(game, fixed, position);
		
			if (width<=0) {
				throw new IllegalArgumentException ("The width must be positive ! ");
			}
			this.crateWidth = width;
			if (height<=0) {
				throw new IllegalArgumentException ("The height must be positive ! ");
			}
		   this.crateHeight = height;
			
		partBuilderConstructor();
		
		if (image == null) {
			throw new NullPointerException ("The image can't be null... We want to draw something!");
		}
		graphicsCreator(image);
		
	}

	// Constructor of Crate without initial position
	public Crate(ActorGame game, boolean fixed, String image, float width, float height) {
		super(game, fixed);
		crateWidth = width;
		crateWidth = height;
		partBuilderConstructor();
		graphicsCreator(image);
	}
	
	// Method witch creates a shape ( a polygon here ) and links it with the entity
	private void partBuilderConstructor() {
		PartBuilder partBuilderCrate = getEntity().createPartBuilder();
		polygon = new Polygon
				  (new Vector(0.0f, 0.0f),
				   new Vector(crateWidth, 0.0f),
				   new Vector(crateWidth, crateHeight),
				   new Vector(0.0f, crateHeight));
		partBuilderCrate.setShape(polygon);
		partBuilderCrate.setFriction(0.1f);
		partBuilderCrate.build();
	}
	
	// Method witch creates a ImageGraphics and links it with the entity
	private void graphicsCreator(String image) {
		
		graphicCrate = new ImageGraphics(image, crateWidth, crateHeight);
		graphicCrate.setAlpha(1.0f);
		graphicCrate.setDepth(0.0f);
		graphicCrate.setParent(getEntity());
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
		graphicCrate.draw(canvas);	
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
