/*
 *	Author:      Auguste Lefevre
 *	Date:        18 nov. 2017
 */

package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;
/*
 * First game ever, Crate and block test
 */

public class SimpleCrateGame implements Game {
	
	// Store context
	private Window window;
	
	// Create physic world
	private World world;
	
	// Declare 2 object that will be in our world
	private Entity crate;
	private Entity block;
	
	// Declare graphical representation of the different body
	private ImageGraphics graphicCrate;
	private ImageGraphics graphicBlock;
	
	// Useful Constants
	private final static float BLOCK_WIDTH = 1;
    private final static float BLOCK_HEIGHT = 1;
    private final static float CRATE_WIDTH = 1;
    private final static float CRATE_HEIGHT = 1;
	
	// This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World();
        
        // Sets gravity as a vertical downwards vector
        world.setGravity(new Vector(0.0f, -9.81f));
        
        // We create a builder that will let us create our entity
        EntityBuilder entityBuilderBlock = world.createEntityBuilder();
        EntityBuilder entityBuilderCrate = world.createEntityBuilder();
        
        // We set the block as fixed and the crate as not fixed
        entityBuilderBlock.setFixed(true);
        entityBuilderCrate.setFixed(false);
        
        // Define properties. Here, its initial location
        entityBuilderBlock.setPosition(new Vector(1.0f, 0.5f));
        entityBuilderCrate.setPosition(new Vector(0.2f, 4.0f));
        
        // Built the Entity "block" with the previously defined builder
        block = entityBuilderBlock.build();
        
        // At this point, our body is in the world, but it has no geometry 
        // We need to use another builder to add shapes
        PartBuilder partBuilder = block.createPartBuilder();
        
        // Create a polygon that fits the shape of the entity and set 
        // the shape of the PartBuilder with it
        Polygon polygon = new Polygon( new Vector(0.0f, 0.0f),
        								   new Vector(BLOCK_WIDTH, 0.0f),
        								   new Vector(BLOCK_WIDTH, BLOCK_HEIGHT),
        								   new Vector(0.0f, BLOCK_HEIGHT) );
        partBuilder.setShape(polygon);
        
        // Build the Part associated with the shape polygon
        partBuilder.build();
        
        // Note : we do not need to keep a reference on partBuilder
        
        
        // Built the Entity "crate" with the previously defined builder
        crate = entityBuilderCrate.build();
        
        // Create a Part, add the shape polygone and built it
        PartBuilder partBuilderCrate = crate.createPartBuilder();
        partBuilderCrate.setShape(polygon); // Exceptionnaly because the size of crate and block are equals
        partBuilderCrate.build();

        // Define Graphics of the different object
        graphicCrate = new ImageGraphics("box.4.png", CRATE_WIDTH, CRATE_HEIGHT);
        graphicBlock = new ImageGraphics("stone.broken.4.png", BLOCK_WIDTH, BLOCK_HEIGHT);
        
        // Set alpha variable (transparency)
        graphicCrate.setAlpha(1.0f);
        graphicBlock.setAlpha(1.0f);
        
        // Set depth variable
        graphicCrate.setDepth(0.0f);
        graphicBlock.setDepth(0.0f);
        
        // Link the graphical representation with a body
        graphicCrate.setParent(crate);
        graphicBlock.setParent(block);
        
        //Successfully initiated
        return true;
    }
        
    // Updates the game as times goes by
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
        		graphicCrate.draw(window);
        		graphicBlock.draw(window);
        
            
    }
        
     // This event is raised after game ends, to release additional resources
        @Override
        public void end() {
            // Empty on purpose, no cleanup required yet
        }
	
	

}