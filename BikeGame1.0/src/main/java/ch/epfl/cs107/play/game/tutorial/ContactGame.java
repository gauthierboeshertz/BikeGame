/*
 *	Author:      Auguste Lefevre
 *	Date:        19 nov. 2017
 */

package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;
import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;
/*
 *  Game in which a ball changes color 
 * 	when it hits another object
 */
public class ContactGame implements Game {
	
		// Store context
		private Window window;
		
		// Create physic world
		private World world;
		
		// Declare 2 game objects/body that will be in our world
		private Entity ball;
		private Entity block;
		
		// Declare graphical representation of body.
		private ShapeGraphics ballGraphics;
		private ImageGraphics graphicBlock;
		//private ImageGraphics graphicBall;
		
		// Declare a basic contact listener which will let us detect contact between two entities.
		private BasicContactListener contactListener;
		
		// Useful Constants
		private final float BALL_RADIUS = 0.5f;
		private final float BLOCK_WIDTH = 10.0f;
		private final float BLOCK_HEIGHT = 1.0f;
		
		
		// Sets the new game
	    @Override
	    public boolean begin(Window window, FileSystem fileSystem) {
	        
	        // Store context
	        this.window = window;
	        
	        // Create physics engine
	        world = new World();
	        
	        // Sets gravity as a vertical downwards vector
	        world.setGravity(new Vector(0.0f, -9.81f));
	        
	        // Create the block, set it as fixed, declare its position and build it
	        EntityBuilder entityBuilderBlock = world.createEntityBuilder();
	        entityBuilderBlock.setFixed(true);
	        entityBuilderBlock.setPosition(new Vector(-5.0f, -1.0f));
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
	        entityBuilderBall.setPosition(new Vector(0.0f, 2.0f));
	        ball = entityBuilderBall.build();
	        
	        // Create a Circle that fits the shape of the entity and build it with the help of PartBuilder 
	        PartBuilder partBuilderBall = ball.createPartBuilder();
	        Circle circle = new Circle(BALL_RADIUS);
	        partBuilderBall.setShape(circle);
	        partBuilderBall.build();
	        
	        // Create the graphical representation of the ball and set set the entity (ball) as parent
	        ballGraphics = new ShapeGraphics(circle, Color.BLUE, Color.BLUE, 0.1f, 1.0f, 0.0f);
	        ballGraphics.setAlpha(1.0f);
	        ballGraphics.setDepth(0.0f);
	        ballGraphics.setParent(ball);
	        
	        /*
	         * graphicBall = new ImageGraphics("explosive.11.png", circle.getRadius()*2.0f, circle.getRadius() * 2.0f, new Vector(circle.getRadius(), circle.getRadius()));
	         * graphicBall.setAlpha(1.0f);
	         * graphicBall.setDepth(0.0f);
	         * graphicBall.setParent(ball);
	         * 
	         */
	        
	        // Create and build contactListener and link it with ball
	        contactListener = new BasicContactListener();
	        ball.addContactListener(contactListener);
	        
	        
	        
	        
	        // Sucsessfully initiated
	        return true;
	    }
	    // Updates the game as times goes by
		@Override
		public void update(float deltaTime) {
			
			// Game logic comes here 
    			// Nothing to do, yet
    	
    			// Simulate physics
    			// Our body is fixed, though, nothing will move
    			world.update(deltaTime);
    		
    			// We must place the camera where we want
    			// We will look at the origin (identity) and increase the view size a bit
    			window.setRelativeTransform(Transform.I.scaled(10.0f));
    			
    			// contactListener is associated to ball
    			// contactListener.getEntities() returns the list of entities in collision with ball
    			int numberOfCollisions = contactListener.getEntities().size();
    			if (numberOfCollisions > 0){
    				ballGraphics.setFillColor(Color.RED);
    				ballGraphics.setOutlineColor(Color.RED);
    			}
    		
    			// We can render our scene now :
    			graphicBlock.draw(window);
    			ballGraphics.draw(window);
    			//graphicBall.draw(window);
			
		}

		@Override
		public void end() {
			// Empty method
		}
	    
	    

}
