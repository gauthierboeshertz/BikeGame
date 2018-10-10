/*
 *	Author:      Auguste Lefevre
 *	Date:        19 nov. 2017
 */

package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;
import java.awt.Color;

import com.sun.glass.events.KeyEvent;
/*
 * Game in which we can control a ball 
 * in a world  with a block linked to a 
 * plank that can then oscillate
 */

public class ScaleGame implements Game {
	
		// Store context
		private Window window;
		
		// Create physic world
		private World world;
		
		// Declare 2 game objects/body that will be in our world
		private Entity ball;
		private Entity block;
		private Entity plank;
		
		// Declare graphical representation of our different body
		private ImageGraphics graphicBall;
		//private ShapeGraphics ballGraphic;
		private ImageGraphics graphicPlank;
		private ImageGraphics graphicBlock;
		
		// Useful Constants
		private final float BLOCK_WIDTH = 10.0f;
		private final float BLOCK_HEIGHT = 1.0f; 
		private final float PLANK_WIDTH = 5.0f;
		private final float PLANK_HEIGHT = 0.2f;
		private final float BALL_RADIUS = 0.5f;
		
		// Sets the new game
	    @Override
	    public boolean begin(Window window, FileSystem fileSystem) {
	        
	        // Store context
	        this.window = window;
	        
	        // Create physics engine
	        world = new World();
	        
	        // Sets gravity as a vertical downwards vector
	        world.setGravity(new Vector(0.0f, -9.81f));
	        
	        // Create the block, make it fixed, set his position and build it
	        EntityBuilder entityBuilderBlock = world.createEntityBuilder();
	        entityBuilderBlock.setFixed(true);
	        entityBuilderBlock.setPosition(new Vector(-5.0f, -1.0f));
	        block = entityBuilderBlock.build();
	        
	        // Create a polygon that fits the shape of the entity and build it with the help of PartBuilder 
	        PartBuilder partBuilderBlock = block.createPartBuilder();
	        Polygon polygon = new Polygon( new Vector(0.0f, 0.0f),
	        								   new Vector(BLOCK_WIDTH, 0.0f),
	        								   new Vector(BLOCK_WIDTH, BLOCK_HEIGHT),
	        								   new Vector(0.0f, BLOCK_HEIGHT) );
	        partBuilderBlock.setShape(polygon);
	        partBuilderBlock.build();
	        
	        // Create the graphical representation of the block and set set the entity (block) as parent
	        graphicBlock = new ImageGraphics("stone.broken.4.png", 10.0f, 1.0f);
	        graphicBlock.setAlpha(1.0f);
	        graphicBlock.setDepth(0.0f);
	        graphicBlock.setParent(block);
	        
	        // Create the ball, set it as not fixed (We want her to move with the help of gravity), set its position and build it
	        EntityBuilder entityBuilderBall = world.createEntityBuilder();
	        entityBuilderBall.setFixed(false);
	        entityBuilderBall.setPosition(new Vector(0.5f, 4.0f));
	        ball = entityBuilderBall.build();
	        
	        // Create a Circle that fits the shape of the entity and build it with the help of PartBuilder 
	        PartBuilder partBuilderBall = ball.createPartBuilder();
	        Circle circle = new Circle(BALL_RADIUS);
	        partBuilderBall.setShape(circle);
	        partBuilderBall.setFriction(3.0f); // this command set the friction of the ball
	        partBuilderBall.build();
	        
	        // Create the graphical representation of the ball and set set the entity (ball) as parent
	        
	        /*
	        ballGraphic = new ShapeGraphics(circle, Color.BLUE, Color.RED, 0.1f, 1.0f, 0.0f);
	        ballGraphic.setAlpha(1.0f);
	        ballGraphic.setDepth(0.0f);
	        ballGraphic.setParent(ball); */
	        
	        graphicBall = new ImageGraphics("explosive.11.png", circle.getRadius()*2, circle.getRadius()*2, new Vector(circle.getRadius(), circle.getRadius()));
	        graphicBall.setAlpha(1.0f);
	        graphicBall.setDepth(0.0f);
	        graphicBall.setParent(ball);
	        
	        // Create the plank, set it as not fixed (We want it to oscillate), set its position and build it
	        EntityBuilder entityBuilderPlank = world.createEntityBuilder();
	        entityBuilderPlank.setFixed(false);
	        entityBuilderPlank.setPosition(new Vector(-2.5f, 0.8f));
	        plank = entityBuilderPlank.build();
	        
	        // Create a polygon that fits the shape of the entity and build it with the help of PartBuilder 
	        PartBuilder partBuilderPlank = plank.createPartBuilder();
	        Polygon polygon2 = new Polygon( new Vector(0.0f, 0.0f),
	        								   new Vector(PLANK_WIDTH, 0.0f),
	        								   new Vector(PLANK_WIDTH, PLANK_HEIGHT),
	        								   new Vector(0.0f, PLANK_HEIGHT) );
	        partBuilderPlank.setShape(polygon2);
	        partBuilderPlank.build();
	        
	        // Create the graphical representation of the plank and set set the entity (plank) as parent
	        graphicPlank = new ImageGraphics("wood.3.png", 5.0f, 0.2f);
	        graphicPlank.setAlpha(1.0f);
	        graphicPlank.setDepth(0.0f);
	        graphicPlank.setParent(plank);
	        
	        
	        // RevoluteConstraint construction
	        RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
	        revoluteConstraintBuilder.setFirstEntity(block);
	        revoluteConstraintBuilder.setFirstAnchor(new Vector(BLOCK_WIDTH/2, (BLOCK_HEIGHT*7)/4));
	        revoluteConstraintBuilder.setSecondEntity(plank);
	        revoluteConstraintBuilder.setSecondAnchor(new Vector(PLANK_WIDTH/2, PLANK_HEIGHT/2));
	        revoluteConstraintBuilder.setInternalCollision(true);
	        revoluteConstraintBuilder.build();
	        
	        // Successfully initiated
	        return true;
	    }
	    // Updates the game as times goes by
		@Override
		public void update(float deltaTime) {
			
			// Game logic comes here 
			// We apply an angular force on the ball when LEFT/RIGHT key is pressed.
			if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
				ball.applyAngularForce(10.0f);
			} else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) { 
				ball.applyAngularForce(-10.0f);
			}
    	
    			// Simulate physics
    			world.update(deltaTime);
    		
    			// we must place the camera where we want
    			// We will look at the origin (identity) and increase the view size a bit
    			window.setRelativeTransform(Transform.I.scaled(10.0f));
    		
    			// We can render our scene now,
    			graphicBlock.draw(window);
    			graphicBall.draw(window);
    			graphicPlank.draw(window);
    			//ballGraphic.draw(window);
    			
		}

		@Override
		public void end() {
			// Empty method
		}
	    
	    

}