/*
 *	Author:      Auguste Lefevre
 *	Date:        18 nov. 2017
 */

package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;
import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;
/*
 * Game in which the ball is linked to an invisible
 * rope and oscillates due to gravity
 * 
 */
public class RopeGame implements Game {
	
		// Store context
		private Window window;
		
		// Create physic world
		private World world;
		
		// Declare 2 game objects/body that will be in our world
		private Entity ball;
		private Entity block;
		
		// Declare graphicals representation of body
		//private ImageGraphics graphicBall;
		private ShapeGraphics ballGraphic;
		private ImageGraphics graphicBlock;
		
		// Useful Constants
		private final float BALL_RADIUS = 0.6f;
		private final float BLOCK_WIDTH = 1.0f;
		private final float BLOCK_HEIGHT = 1.0f;
		
		
		// This event is raised when game has just started
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
	        entityBuilderBlock.setPosition(new Vector(1.0f, 0.5f));
	        block = entityBuilderBlock.build();
	        
	        // Create a polygon that fits the shape of the entity and build it with the help of PartBuilder 
	        PartBuilder partBuilder = block.createPartBuilder();
	        Polygon polygon = new Polygon( new Vector(0.0f, 0.0f),
	        								   new Vector(BLOCK_WIDTH, 0.0f),
	        								   new Vector(BLOCK_WIDTH, BLOCK_HEIGHT),
	        								   new Vector(0.0f, BLOCK_HEIGHT) );
	        partBuilder.setShape(polygon);
	        partBuilder.build();
	        
	        // Create the graphical representation of the block and set set the entity (block) as parent
	        graphicBlock = new ImageGraphics("stone.broken.4.png", BLOCK_WIDTH, BLOCK_HEIGHT);
	        graphicBlock.setAlpha(1.0f);
	        graphicBlock.setDepth(0.0f);
	        graphicBlock.setParent(block);
	        
	        // Create the ball, set it as not fixed (We want her to move with the help of gravity), set its position and build it
	        EntityBuilder entityBuilderBall = world.createEntityBuilder();
	        entityBuilderBall.setFixed(false);
	        entityBuilderBall.setPosition(new Vector(0.6f, 4.0f));
	        ball = entityBuilderBall.build();
	        
	        // Create a Circle that fits the shape of the entity and build it with the help of PartBuilder 
	        PartBuilder partBuilderBall = ball.createPartBuilder();
	        Circle circle = new Circle(BALL_RADIUS);
	        partBuilderBall.setShape(circle);
	        partBuilderBall.build();
	        
	        // Create the graphical representation of the ball and set set the entity (ball) as parent
	        ballGraphic = new ShapeGraphics(circle, Color.BLUE, Color.RED, 0.1f, 1.0f, 0.0f);
	        ballGraphic.setAlpha(1.0f);
	        ballGraphic.setDepth(0.0f);
	        ballGraphic.setParent(ball);
	        
	        /*
	        graphicBall = new ImageGraphics("explosive.11.png", circle.getRadius()*2.0f, circle.getRadius() * 2.0f, new Vector(circle.getRadius(), circle.getRadius()));
	        graphicBall.setAlpha(1.0f);
	        graphicBall.setDepth(0.0f);
	        graphicBall.setParent(ball); */
	        
	       
	        // RopeConstraintBuilder construction
	        RopeConstraintBuilder ropeConstraintBuilder = world.createRopeConstraintBuilder();
	        ropeConstraintBuilder.setFirstEntity(block);
	        ropeConstraintBuilder.setFirstAnchor(new Vector(BLOCK_WIDTH/2, BLOCK_HEIGHT/2));
	        ropeConstraintBuilder.setSecondEntity(ball);
	        ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
	        ropeConstraintBuilder.setMaxLength(3.0f);
	        ropeConstraintBuilder.setInternalCollision(true);
	        ropeConstraintBuilder.build();
	        
	        // Sucsessfully initiated
	        return true;
	    }

		@Override
		public void update(float deltaTime) {
			
			// Game logic comes here 
    			// Nothing to do, yet
    	
    			// Simulate physics
    			world.update(deltaTime);
    		
    			// we must place the camera where we want
    			// We will look at the origin (identity) and increase the view size a bit
    			window.setRelativeTransform(Transform.I.scaled(10.0f));
    		
    			// We can render our scene now,
    			graphicBlock.draw(window);
    			ballGraphic.draw(window);
    			//graphicBall.draw(window);
			
		}

		@Override
		public void end() {
			// Empty method
		}
	    
	    

}
