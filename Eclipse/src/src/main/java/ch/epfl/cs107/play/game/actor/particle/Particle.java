/*
 *	Author:      Marc Watine
 *	Date:        Nov 30, 2017
 */

package ch.epfl.cs107.play.game.actor.particle;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.particle.ImageParticle;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
/*
 * The Particle's abstract class. The most important thing is that it is not 
 * a GameEntity. It will only be an object to be drawn and will not need a body (entity)
 * It thus needs a specific way to make it move (not by gravity or forces as any other game entity is)
 * These are the position, velocity, acceleration, and associated angular
 * 
 */
public abstract class Particle implements Graphics, Positionable {
	// Declare the attributes
	private ActorGame game;
	private Vector position; // In the absolute frame
	private Vector velocity; 
	private Vector acceleration;
	
	private float angularPosition;
	private float angularVelocity;
	private float angularAcceleration;
	
	// Constructor for a Particle from "scratch"
	public Particle(ActorGame game, Vector position, Vector velocity, Vector acceleration, 
			float angularPosition, float angularVelocity, float angularAcceleration) {
		if (game == null) {
			throw new NullPointerException ("The game entered is null");
		}
		this.game= game;
		if (position == null) {
			throw new NullPointerException ("The position entered is null");
		}
		this.position= position;
		if (velocity == null) {
			throw new NullPointerException ("The velocity entered is null");
		}
		this.velocity= velocity;
		if (acceleration == null) {
			throw new NullPointerException ("The acceleration entered is null");
		}
		this.acceleration = acceleration;
		this.angularPosition = angularPosition;
		this.angularVelocity = angularVelocity;
		this.angularAcceleration = angularAcceleration;
	}
	// Constructor of a Particle with another one as model but we change the position
	public Particle(Particle particle, Vector position) {
		this.game =particle.getOwner();
		if (position == null) {
			throw new NullPointerException ("The position is null");
		}
		this.position = position;
		this.velocity = particle.getVectors().get(1);
		this.acceleration = particle.getVectors().get(2);
		this.angularPosition = particle.getAngular().get(0);
		this.angularVelocity = particle.getAngular().get(1);
		this.angularAcceleration = particle.getAngular().get(2);

	}
	
	// The method that we will use in ImageParticle to copy each one of them
	public abstract Particle copy(Vector position);
	
	// Method that returns all the movement vector as an array, to simplify this class.
	public ArrayList<Vector> getVectors(){
		return new ArrayList<Vector>() {{
			add(position);
			add(velocity); 
			add(acceleration);
		}};
	}
	
	//  Method that returns all the angular movement properties as an array, to simplify this class.
	public ArrayList<Float> getAngular(){
		return new ArrayList<Float>() {{
			add(angularPosition);
			add(angularVelocity);
			add(angularAcceleration);
		}};
	}
	
	// updates the movement vectors and angular values
	public void update (float deltaTime) {
		velocity = velocity.add(acceleration.mul(deltaTime)) ;
		position = position.add(velocity.mul(deltaTime)) ;
		angularVelocity +=  angularAcceleration * deltaTime ;
		angularPosition += angularVelocity * deltaTime ;
	}

	// A get-method witch return the game (ActorGame)
		protected ActorGame getOwner() {
			return game;
		}

	@Override
	public Transform getTransform() {	
		return Transform.I.rotated(angularPosition).translated(position);
	}

	@Override
	public Vector getVelocity() {
		return velocity;
	}

}
