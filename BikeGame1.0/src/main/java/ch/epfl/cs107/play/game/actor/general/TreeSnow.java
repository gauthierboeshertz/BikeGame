/*
 *	Author:      Auguste Lefevre
 *	Date:        28 nov. 2017
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

public class TreeSnow extends GameEntity implements Actor {
	
	// Declare the attributes needed to create a TreeSnow
	private final static float TREE_WIDTH = 2.0f;
	private final static float TREE_HEIGHT = 8.0f;
	private ImageGraphics graphicTree;
	private Polygon polygon;
	
		// TreeSnow constructor with initial position 
		public TreeSnow(ActorGame game, boolean fixed, Vector position) {
			super(game, fixed, position);
			partBuilderConstructor();
			graphicCreator("tree.snow.png");
		}
		
		// Method witch create the Part of the TreeSnow object
		private void partBuilderConstructor() {
			PartBuilder partBuilderFinish = getEntity().createPartBuilder();
			polygon = new Polygon
					  (new Vector(0.0f, 0.0f),
					   new Vector(TREE_WIDTH, 0.0f),
					   new Vector(TREE_WIDTH, TREE_HEIGHT),
					   new Vector(0.0f, TREE_HEIGHT));
			partBuilderFinish.setShape(polygon);
			// Set this to ghost because must not have physic contact with the bike
			partBuilderFinish.setGhost(true);
			partBuilderFinish.build();
		}
		
		// Method witch creates the graphic of the TreeSnow Object
		private void graphicCreator(String image) {
			graphicTree = new ImageGraphics(image, TREE_WIDTH, TREE_HEIGHT);
			graphicTree.setAlpha(0.8f);
			graphicTree.setDepth(-2.0f);
			graphicTree.setParent(getEntity());
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
			graphicTree.draw(canvas);	
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

