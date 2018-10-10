/*
 *	Author:      Auguste Lefevre
 *	Date:        24 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

/*
 * The Wheel object. It's a "GameEntity" with
 * with some particular added methods and variables
 * that differentiates the wheel from any GameEntity.
 * This class is in package /general because it can be used
 * for other games as well (a car?)
 * 
 */
public class Wheel extends GameEntity implements Actor {
	
	// Declare the useful attributes for the Class Wheel
	private Vector centerPosition;
	private float wheelRadius;
	private Circle circle;
	private ShapeGraphics graphicWheel;
	private WheelConstraint constraint;
	private PartBuilder partBuilderWheel;
	private ShapeGraphics graphicRayon1, graphicRayon2, graphicRayon3, graphicRayon4;
	
	
	// Wheel's constructor with initial position
	public Wheel(ActorGame game, boolean fixed, Vector position, Vector centerPosition, float radius) {
		super(game, fixed, position);
		if (radius <=0) {
			throw new IllegalArgumentException ("The radius of the Wheel must be positive !!");
		}
		this.wheelRadius = radius;
		if (centerPosition == null) {
			throw new NullPointerException ("The position of the Wheel is null !!");
		}
		this.centerPosition = centerPosition;
		partBuilderConstructor();
		graphicConstructor();
		graphicRayon();
	}
	
	// Wheel's constructor without initial position 
	public Wheel(ActorGame game, boolean fixed,Vector centerPosition, float radius) {
		super(game, fixed);
		if (radius <=0) {
			throw new IllegalArgumentException ("The radius of the Wheel must be positive !!");
		}
		this.wheelRadius = radius;
		if (centerPosition == null) {
			throw new NullPointerException ("The position of the Wheel is null !!");
		}		this.centerPosition = centerPosition;
		partBuilderConstructor();
		graphicConstructor();
		graphicRayon();
	}
	
	// Methods that creates the Part of the Wheel
	private void partBuilderConstructor() {
		partBuilderWheel = getEntity().createPartBuilder();
		this.circle = new Circle(wheelRadius /*, centerPostion*/ );
		partBuilderWheel.setFriction(15.0f);
		partBuilderWheel.setShape(this.circle);
		partBuilderWheel.build();
	}
	
	// Methods that creates the graphical representation of the wheel and links it with the Entity
	private void graphicConstructor() {
		graphicWheel = new ShapeGraphics(circle, Color.BLACK, Color.RED, 0.1f, 1.0f, 0.0f);
		graphicWheel.setParent(getEntity());
		
	}
	
	// Method that creates the graphical representation of radius line of the wheel & links it to the Entity
	private void graphicRayon() {
		Polyline radius1;
		radius1 = new Polyline(
				circle.getCenter().add(- wheelRadius + 0.05f, 0.0f),
				circle.getCenter().add(wheelRadius - 0.05f, 0.0f)
				);
		graphicRayon1 = new ShapeGraphics(radius1, Color.BLACK, Color.WHITE, 0.05f, 1.0f, 0.0f);
		graphicRayon1.setParent(getEntity());
		
		Polyline radius2;
		radius2 = new Polyline(
				circle.getCenter().add(0.0f, wheelRadius - 0.05f),
				circle.getCenter().add(0.0f, -wheelRadius + 0.05f)
				);
		graphicRayon2 = new ShapeGraphics(radius2, Color.BLACK, Color.WHITE, 0.05f, 1.0f, 0.0f);
		graphicRayon2.setParent(getEntity());
		
		Polyline radius3;
		radius3 = new Polyline(
				circle.getCenter().add(1.0f*wheelRadius*(1.4142f/2.0f) - 0.05f, 1.0f*wheelRadius*(1.4142f/2.0f) -0.05f),
				circle.getCenter().add(-1.0f*wheelRadius*(1.4142f/2.0f)+0.05f, -1.0f*wheelRadius*(1.4142f/2.0f) + 0.05f));
		graphicRayon3 = new ShapeGraphics(radius3, Color.BLACK, Color.WHITE, 0.05f, 1.0f, 0.0f);
		graphicRayon3.setParent(getEntity());
		
		Polyline radius4;
		radius4 = new Polyline(
				circle.getCenter().add(-1.0f*wheelRadius*(1.4142f/2.0f) + 0.05f, 1.0f*wheelRadius*(1.4142f/2.0f) -0.05f),
				circle.getCenter().add(1.0f*wheelRadius*(1.4142f/2.0f)-0.05f, -1.0f*wheelRadius*(1.4142f/2.0f) + 0.05f));
		graphicRayon4 = new ShapeGraphics(radius4, Color.BLACK, Color.WHITE, 0.05f, 1.0f, 0.0f);
		graphicRayon4.setParent(getEntity());
						
	}
	
	
	// Methods that creates the constraint between the wheel and the vehicle
	public void attach(Entity vehicle, Vector anchor, Vector axis) {
		
		// Use a method added on ActorGame
		WheelConstraintBuilder constraintBuilder = getOwner().constraintBuilderConstructor(); 
		constraintBuilder.setFirstEntity(vehicle);
		
		// point d'ancrage du véhicule : 
		constraintBuilder.setFirstAnchor(anchor);
		
		// Entity associée à la roue :
		constraintBuilder.setSecondEntity(getEntity());
		
		// point d'ancrage de la roue (son centre):
		constraintBuilder.setSecondAnchor(Vector.ZERO);
		
		// axe le long duquel la roue peut se déplacer :
		constraintBuilder.setAxis(axis);
		
		// fréquence du ressort associé
		constraintBuilder.setFrequency(3.0f); constraintBuilder.setDamping(0.5f);
		
		// force angulaire maximale pouvant être appliquée 
		// à la roue pour la faire tourner : 
		constraintBuilder.setMotorMaxTorque(10.0f); 
		
		this.constraint = constraintBuilder.build();
	}
	
	// Method that sets MotorSpeed and set the motor as enabled
	public void power(float speed) {	
		constraint.setMotorEnabled(true);
		constraint.setMotorSpeed(speed);
		return;
	}
	
	// Method that disables the motor
	public void relax() {
		constraint.setMotorEnabled(false);
		return;
	}
	
	// Method that destroys the constraint between the wheel and the vehicle to detach them
	public void detach() {
		constraint.destroy();
		return;
	}
	
	// Method that returns the relative speed
	public float getSpeed() {
		return constraint.getMotorSpeed();
	}
	
	// Method that destroys the object (this)
	public void destroy() {
		super.destroy();	
		this.getOwner().removeActor(this);
	}
	
	// Redefines the method draw by drawing the graphic
	@Override
	public void draw(Canvas canvas) {
		graphicWheel.draw(canvas);
		graphicRayon1.draw(canvas);
		graphicRayon2.draw(canvas);
		graphicRayon3.draw(canvas);
		graphicRayon4.draw(canvas);
	}

	// Defines the method getTransform that will be returning the Transform of the entity
	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	// Defines the method getVelocity that will be returning the Velocity of the entity
	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}
	
}