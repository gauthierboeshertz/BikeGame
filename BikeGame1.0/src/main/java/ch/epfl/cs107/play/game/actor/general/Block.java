/*
 *	Author:      Auguste Lefevre
 *	Date:        1 déc. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Block extends GameEntity implements Actor {
	
		// Declare the useful variable for the creation of a Block
		private ImageGraphics graphicBlock;
		private float blockWidth;
		private float blockHeight;
		private Polygon polygon;
		
		// Constructor of Block with the initial position
		public Block(ActorGame game, boolean fixed,Vector position, String image, float width, float height, boolean ghost
				, float alpha, float depth) {
			super(game, fixed, position);
			if (width<=0) {
				throw new IllegalArgumentException ("The width must be positive !(Block) ");
			}
			this.blockWidth = width;
			if (height<=0) {
				throw new IllegalArgumentException ("The height must be positive !(Block) ");
			}
			if (image == null) {
				throw new NullPointerException ("The image name is null! we want to draw something...(Block)");
			}
		    this.blockHeight = height;
			partBuilderCreator(ghost);
			graphicsCreator(image, alpha, depth);		
		}

		
		// Method witch creates a shape ( a polygon here ) and links it with the entity
		private void partBuilderCreator(boolean ghost) {
			PartBuilder partBuilderBlock = getEntity().createPartBuilder();
			polygon = new Polygon
					  (new Vector(0.0f, 0.0f),
					   new Vector(blockWidth, 0.0f),
					   new Vector(blockWidth, blockHeight),
					   new Vector(0.0f, blockHeight));
			partBuilderBlock.setShape(polygon);
			partBuilderBlock.setFriction(0.1f);
			partBuilderBlock.setGhost(ghost);
			partBuilderBlock.build();
		}
		
		// Method witch creates a ImageGraphics and links it with the entity
		private void graphicsCreator(String image, float alpha, float depth) {
			
			graphicBlock = new ImageGraphics(image, blockWidth, blockHeight);
			graphicBlock.setAlpha(alpha);
			graphicBlock.setDepth(depth);
			graphicBlock.setParent(getEntity());
		}
		
		// Method that returns the width of the block
		public float getWidth() {
			return this.blockWidth;
		}
		
		// Method that returns the height of the block
		public float getHeight() {
			return this.blockHeight;
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
			graphicBlock.draw(canvas);	
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
