/*
 *	Author:      Auguste Lefevre
 *	Date:        24 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.bikegame;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;


/*
 * The Bike object. It's a "GameEntity" with
 * with some particular added methods and variables
 * that differentiates the bike from any GameEntity.
 * This class is in the package /bikegame because
 * this object is specific to this game.
 * 
 */
public class Bike extends GameEntity implements Actor {
	
	// Declare and initialize useful attributes ("lookRight" for the direction
	// the rider is looking, "hit" to know if the
	// hitbox has been in contact with something that must end the game)
	private final static float MAX_WHEEL_SPEED = 20.0f;
	private boolean lookRight = true;
	private boolean hit = false;
	private boolean armUp = false;
	
	// Declare two Wheels (left and right)
	private Wheel leftWheel;
	private Wheel rightWheel;
	
	// Declare the head representation of the person on the bike
	private Circle head;
	private ShapeGraphics graphicHead;
	
	// Declare the arm representation of the person on the bike
	private Polyline arm;
	private ShapeGraphics graphicArm;
	
	// Declare the back representation of the person on the bike
	private Polyline back;
	private ShapeGraphics graphicBack;
	
	// Declare the thigh representation of the person on the bike
	private Polyline thigh;
	private ShapeGraphics graphicThigh;
	
	// Declare the first leg/foot representation of the person on the bike
	private Polyline foot1;
	private ShapeGraphics graphicFoot1;
	
	// Declare the forst legg/foot representation of the person on the bike
	private Polyline foot2;
	private ShapeGraphics graphicFoot2;
	
	// Create a specific Contact listener by creating an anonymous Class
	private ContactListener listener = new ContactListener() { 
		@Override
		public void beginContact(Contact contact) {
			// If the bike have contact with a "ghost" object, we do nothing
			if (contact.getOther().isGhost()) {
				return;
			} else {
				// If the bike contacts with a Part of himself (typically left/right Wheel), do nothing
				if (partIn(contact.getOther())) {
					return;
				} else {
					// Set "hit" as true to identify a deadly contact
					hit = true;
					return;
				}
			}
		}
		
		@Override
		// Nothing specific must happen if the contact ends. The bike will allready have disapeared
		public void endContact(Contact contact) {}
		};
	
	
	// Bike's constructor with initial position
	public Bike(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		
		// Initialize the two wheels 
		leftWheel = new Wheel(getOwner(), false,this.getPosition().add(-1.0f, 0.0f),
				this.getPosition().add(-1.0f, 0.0f), 0.5f );
		rightWheel = new Wheel(getOwner(), false, this.getPosition().add(1.0f, 0.0f),
				this.getPosition().add(-1.0f, 0.0f), 0.5f);
		
		// create the constraint between the wheels and the bike
		this.leftWheel.attach(getEntity(), new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f));
		this.rightWheel.attach(getEntity(), new Vector(1.0f, 0.0f), new Vector(-0.5f, 1.0f));
		
		// Create the hitbox, graphical representations and link the contact listener
		hitboxConstructor();
		allGraphicCreator();
		addContact(listener);
	}
	
	// Bike's constructor without initial position
	public Bike(ActorGame game, boolean fixed) {
		super(game, fixed);
		
		// Initialize the two wheels 
				leftWheel = new Wheel(getOwner(), false,this.getPosition().add(-1.0f, 0.0f),
						this.getPosition().add(-1.0f, 0.0f), 0.5f );
				rightWheel = new Wheel(getOwner(), false, this.getPosition().add(1.0f, 0.0f),
						this.getPosition().add(-1.0f, 0.0f), 0.5f);
		
		// create the constraint between the wheels and the bike
		this.leftWheel.attach(getEntity(), new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f));
		this.rightWheel.attach(getEntity(), new Vector(1.0f, 0.0f), new Vector(-0.5f, 1.0f));
		
		// Create the hitbox, graphic and link the contact listener 
		hitboxConstructor();
		allGraphicCreator();
		addContact(listener);
	}
	
	// Method that creates the Part of the hitbox
	private void hitboxConstructor() {
		PartBuilder partBuilderHitbox = getEntity().createPartBuilder();
		Polygon hitbox = new Polygon 
			  ( 0.0f, 0.5f,
				0.5f, 1.0f,
				0.0f, 2.0f,
				-0.5f, 1.0f
				);
		partBuilderHitbox.setShape(hitbox);
		// We set the hitbox as "ghost" which means that it can 
		// cross objects without any physical contact.
		partBuilderHitbox.setGhost(true);
		partBuilderHitbox.build();
	}

	// General method that creates the graphical representation of type ShapeGraphics
	private ShapeGraphics graphicConstructor(Shape shape, Color fillcolor, Color colorline,
			float thickness, float alpha, float depth ) {
		ShapeGraphics graphic = new ShapeGraphics (shape, fillcolor, colorline, thickness, alpha, depth);
		graphic.setParent(getEntity());
		return graphic;
	}
	
	
	// Method that initialize all the graphics of the character on the bike
	private void allGraphicCreator() {
		
		// Creation of the Head graphical representation
		head = new Circle(0.2f, getHeadLocation());
		graphicHead = graphicConstructor(head, Color.BLACK, Color.WHITE, 0.1f, 1.0f, 0.0f);
		
		// Creation of the Arm graphical representation
		arm = new Polyline(
				getShoulderLocation(),
				getHandLocation()
				);
		graphicArm = graphicConstructor(arm, Color.BLACK, Color.WHITE, 0.1f, 1.0f, 0.0f);
		
		// Creation of the Back graphical representation
		back = new Polyline(
				getShoulderLocation(),
				getBackLocation()
				);
		graphicBack = graphicConstructor(back, Color.BLACK, Color.WHITE, 0.1f, 1.0f, 0.0f);
		
		// Creation of the Thigh graphical representation
		thigh = new Polyline(
				getBackLocation(),
				getKneeLocation()
				);
		graphicThigh = graphicConstructor(thigh, Color.BLACK, Color.WHITE, 0.1f, 1.0f, 0.0f);

		// Creation of the Foot1 graphical representation
		foot1 = new Polyline(
		getKneeLocation(),
		getFoot1Location()
		);
		graphicFoot1 = graphicConstructor(foot1, Color.BLACK, Color.WHITE, 0.1f, 1.0f, 0.0f);
		
		// Creation of the Foot2 graphical representation
		foot2 = new Polyline(
				getKneeLocation(),
				getFoot2Location()
				);
		graphicFoot2 = graphicConstructor(foot2, Color.BLACK, Color.WHITE, 0.1f, 1.0f, 0.0f);

		
	}
	

	// Explicit method...
	private Vector getHeadLocation() {
		return new Vector(0.0f, 1.75f);
	}
	
	// Explicit method... with an iteration that control the way the character is looking 
	private Vector getShoulderLocation() {
		
		if (lookRight) {
			return new Vector(-0.15f, 1.55f);
		} else {
			return new Vector(0.15f, 1.55f);
		}
	}
	
	// Explicit method... with an iteration that control the way the character is looking
	private Vector getHandLocation() {
		if (lookRight) {
			if(armUp) {
				return new Vector(0.65f, 1.80f);
			} else {
				return new Vector(0.55f, 1.0f);
			}
			
		} else {
			if (armUp) {
				return new Vector(-0.65f, 1.80f);
			} else {
				return new Vector(-0.55f, 1.0f);
			}
		}
		
	}
	
	// Explicit method... with an iteration that control the way the character is looking 
	private Vector getBackLocation() {
		if (lookRight) {
			return new Vector(-0.45f, 1.0f);
		} else {
			return new Vector(0.45f, 1.0f);
		}
	}
	
	// Explicit method...
	private Vector getKneeLocation() {
		return new Vector(0.1f, 0.6f);
	}
	
	// Explicit method...
	private Vector getFoot1Location() {
		return new Vector (-0.25f, 0.0f);
	}
	
	// Explicit method...
	private Vector getFoot2Location() {
		return new Vector (0.25f, 0.0f);
	}
	
	// Method that links a Contact Listener to the bike Entity
	private void addContact(ContactListener listener) {
		this.getEntity().addContactListener(listener);
	}

	// Explicit method...
	public void setLook(boolean lookRight) {
		this.lookRight = lookRight;
	}
	
	// Explicit method...
	public boolean getLook() {
		return lookRight;
	}
	
	// Method witch RE-creates the graphics. This method is called
	// when the attribut lookRight is change
	public void changeWayOfLook() {
		allGraphicCreator();
	}
	
	public void normalWay() {
		allGraphicCreator();
	}
	
	public void armUp() {
		allGraphicCreator();
	}
	
	public void setArmup(boolean armUp) {
		this.armUp = armUp;
	}
		
	// Explicit method...
	public boolean getHit() {
		return hit;
	}
	
	// Explicit method...
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	
	// Method that turns off the motorisation of the wheel
	public void relaxMotor() {
		this.leftWheel.relax();
		this.rightWheel.relax();
	}
	
	// Method that stops the back wheel by activating the motor and setting the speed to 0 ( calls the method power() of Wheel)
	public void stop() {
		if (this.lookRight) {
			this.leftWheel.power(0.0f);
		} else {
			this.rightWheel.power(0.0f);
		}
	}
	
	// Method that puts in action the correct wheel by enabling the motor 
	// and setting the speed to MAX_WHEEL_SPEED by using the method power() of Wheel
	public void accel() {
		if (this.lookRight) {
			if(this.leftWheel.getSpeed() < - MAX_WHEEL_SPEED) {
				this.leftWheel.relax();
			} else {
				this.leftWheel.power(-MAX_WHEEL_SPEED);
			}
		} else {
			if (this.rightWheel.getSpeed() > MAX_WHEEL_SPEED) {
				this.rightWheel.relax();
			} else {
				this.rightWheel.power(MAX_WHEEL_SPEED);
			}
		}
		
	}

	// Redefines the method destroy to also remove the actor from the list of Actor of the Game
	@Override
	public void destroy() {
		super.destroy();
		getOwner().removeActor(this);
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

	// Redefines the method draw by drawing the graphics of the bike
	@Override
	public void draw(Canvas canvas) {
		leftWheel.draw(canvas);
		rightWheel.draw(canvas);
		graphicHead.draw(canvas);
		graphicArm.draw(canvas);
		graphicBack.draw(canvas);
		graphicThigh.draw(canvas);
		graphicFoot1.draw(canvas);
		graphicFoot2.draw(canvas);
		
	}

}
