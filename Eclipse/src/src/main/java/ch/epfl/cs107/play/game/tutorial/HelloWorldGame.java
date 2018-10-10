package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show the basic architecture®
 */
public class HelloWorldGame implements Game {

    // Store context 
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects (entities)
    private Entity body;
    
    // graphical representations of the body
    private ImageGraphics graphicBlock;
    private ImageGraphics graphicBow;
    
    // Useful constants
    private final static float STONE_WIDTH = 1;
    private final static float STONE_HEIGHT = 1;
    private final static float BOW_WIDTH = 1;
    private final static float BOW_HEIGHT = 1;
    
    
    
    
    // Sets the new game
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
                
        
        // Create physics engine
        world = new World();
        
        // Sets gravity as a vertical downwards vector
        world.setGravity(new Vector(0.0f, -9.81f));
        
        // To create an object, you need to use a builder
        EntityBuilder entityBuilder = world.createEntityBuilder();
        
        // Make sure the object won't  move
        entityBuilder.setFixed(true);
        
        // Define the initial position 
        entityBuilder.setPosition(new Vector(1.f, 0.5f));
        
        // Once ready, the body can be built
        body = entityBuilder.build();
        
        // Define graphics
        graphicBow = new ImageGraphics("bow.png", BOW_WIDTH, BOW_HEIGHT);
        graphicBlock = new ImageGraphics("stone.broken.4.png", STONE_WIDTH, STONE_HEIGHT);
        
        // Transparency can be chosen for each drawing (0.0 - transparent, 1.0 - opaque)
        graphicBlock.setAlpha(1.0f);
        graphicBow.setAlpha(1.0f);
   
        // We can chose the depth of each graphical representation
        graphicBlock.setDepth(0.0f);
        graphicBow.setDepth(1.0f);
        
        // Link it with body
        graphicBow.setParent(body);
        graphicBlock.setParent(body);
        
        // Successfully initiated
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
    		
    		// we must place the camera where we want
    		// We will look at the origin (identity) and increase the view size a bit
    		window.setRelativeTransform(Transform.I.scaled(10.0f));
    		
    		// We can render our scene now,
    		graphicBlock.draw(window);
    		graphicBow.draw(window);

        
      
        // The actual rendering will be done now, by the program loop
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
