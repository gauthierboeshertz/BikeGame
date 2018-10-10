/*
 *	Author:      Auguste Lefevre
 *	Date:        22 nov. 2017
 */

package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.bikegame.Bike;
import ch.epfl.cs107.play.game.actor.bikegame.BikeExtended;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WeldConstraintBuilder;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;


/*
 * The super class of every game. The interface "Game" that
 * implements it makes it have the "begin, update, end" important methods.
 * It has a list of actor, every game object.
 * 
 */
public abstract class ActorGame implements Game {
	
	// feature attribute of ActorGame class
	private ArrayList<Actor> listActor = new ArrayList<Actor>();
	private World world;
	private Window window;
	private FileSystem fileSystem;
	
	// Viewport properties
	private Vector viewCenter;
	private Vector viewTarget;
	private Positionable viewCandidate;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
	private static final float VIEW_SCALE = 10.0f;
	
	// Method that return the principal Actor of the game (a Bike/BikeExtended for us)
	public Actor getPayLoad() {
		for (int i = 0; i < listActor.size(); ++i) {
			if ((listActor.get(i).getClass() == Bike.class) || (listActor.get(i).getClass() == BikeExtended.class)) {
				return listActor.get(i);
			}
		}	
		return null;
	}
	
	// Method to add an Actor to listActor
	public void addActor(Actor actor) {
		listActor.add(actor);
	}
	
	// Method to remove an Actor from listActor
	public void removeActor(Actor actor) {
		listActor.remove(actor);
	}
	
	// Gives access to the keyboard
	public Keyboard getKeyboard() { 
		return window.getKeyboard();
	}
	
	// Gives access to the window
	public Canvas getCanvas() { 
		return window;
	}
	// Gives access to the fileSystem
	public FileSystem getFileSystem() {
		return this.fileSystem;
	}
	
	// Gives the possibility to initialize viewCandidate
	protected void setViewCandidate(Positionable viewCandidate) {
		this.viewCandidate = viewCandidate;
	}
	// Sets the game
	public boolean begin(Window window, FileSystem fileSystem) {
		
		// Initialization of window, fileSystem and world
		this.window = window;
		this.fileSystem = fileSystem;
		this.world = new World();
		
        // Sets gravity as a vertical downwards vector
		world.setGravity(new Vector(0.0f, -9.81f));
		
		// Initialization of ViewTarget and ViewCenter at 0
		viewCenter = Vector.ZERO;
		viewTarget = Vector.ZERO;
		
		return true;
		
	}
	
	// Methods that contain the different action needed at every actualization of the game (every delta time)
	@Override
    public void update(float deltaTime) {
    		
    		// Simulate physics
    		world.update(deltaTime);
    		
    		// Simulate the evolution of each Actor of the list listActor every deltaTime
    		for (int i = 0; i < listActor.size(); ++i) {
    			listActor.get(i).update(deltaTime);
    		}
    		
    		// Update expected viewport center
    		if (viewCandidate != null) {
    			viewTarget = viewCandidate.getPosition().add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION));
    		}
    		
    		// Interpolate with previous location
    		float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
    		viewCenter = viewCenter.mixed(viewTarget, 1.0f - ratio);
    		
    		// Compute new viewport
    		Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
    		window.setRelativeTransform(viewTransform);
    		
    		// Draw all Actor of the list listActor 
    		for (int i = 0; i < listActor.size(); ++i) {
    			listActor.get(i).draw(getCanvas());
    		}
    		
    }

	public void end() {
		// Do nothing a this stage
	}
	
	// Method that creates an Entity and returns it (used in GameEntity)
	protected Entity entityConstructor(boolean fixed, Vector v) {
		Entity body;
		EntityBuilder entityBuilder =  world.createEntityBuilder();
		entityBuilder.setFixed(fixed);
		entityBuilder.setPosition(v);
		body = entityBuilder.build();
		return body;
	}	
	
	
	// Method that creates a WheelConstraint and returns it
	// ( Used in Bike and Wheel because we can't have access to "world" )
	public WheelConstraintBuilder constraintBuilderConstructor() {
		WheelConstraintBuilder constraintBuilder = this.world.createWheelConstraintBuilder();
		return constraintBuilder;
	}
	
	// Method that creates a ropeConstraint (same reason)
	public RopeConstraintBuilder ropeConstraintBuilderConstructor() {
		RopeConstraintBuilder constraintBuilder = this.world.createRopeConstraintBuilder();
		return constraintBuilder;
	}
	
	// Method that creates a revolutConstraint (same reason)
	public RevoluteConstraintBuilder revoluteConstraintConstructor() {
		RevoluteConstraintBuilder contraintBuilder = this.world.createRevoluteConstraintBuilder();
		return contraintBuilder;
	}
	
	// Method that create a weldConstraint (same reason)
	public WeldConstraintBuilder weldConstraintBuilderConstructor() {
		WeldConstraintBuilder weldConstraint = this.world.createWeldConstraintBuilder();
		return weldConstraint;
	}
	
	
	// Method that clears the list listActor (delete all actor of the game to rebuild it, needed for a reset for example)
	protected void deleteAllActor() {
		this.listActor.clear();
	}
	
	
	
}
