/*
 *	Author:      Auguste Lefevre
 *	Date:        24 nov. 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

/*
 * The Terrain object. It's a "GameEntity" with
 * with some particular added methods and variables
 * that differentiates the crate from any GameEntity.
 * This class is in package /general because it can be use
 * for other game that BikeGame
 * 
 */
public class Terrain extends GameEntity implements Actor{
	
	// Declare the attributes useful for the creation of Terrain
	private ShapeGraphics shapeTerrain;
	private ArrayList<Vector> listVector = new ArrayList<Vector>();
	private Polyline polyline;
	
	// The Terrain's constructor with initial position
	public Terrain(ActorGame game, boolean fixed, Vector position, ArrayList<Vector> listVector) {
		super(game, fixed, position);
		if (listVector == null) {
			throw new NullPointerException ("The Array of vectors of the terrain can't be null! We want to have somewhere to ride !");			}
		this.listVector = listVector;
		partBuilderPolyline();
		graphicShapeConstructor(this.polyline);
	}

	// The Terrain's constructor without initial position
	public Terrain(ActorGame game, boolean fixed, ArrayList<Vector> listVector) {
		super(game, fixed);
		if (listVector == null) {
			throw new NullPointerException ("The Array of vectors of the terrain can't be null! We want to have somewhere to ride !");			}
		
		this.listVector = listVector;
		partBuilderPolyline();
		graphicShapeConstructor(this.polyline);
	}
	
	// Method witch creates the Part of Terrain by using the list of Vector 
	private void partBuilderPolyline() {
		PartBuilder partBuilderPolyline = getEntity().createPartBuilder();
		this.polyline = new Polyline (this.listVector);
		partBuilderPolyline.setShape(this.polyline);
		partBuilderPolyline.setFriction(15.0f);
		partBuilderPolyline.build();
	}
	
	// Method witch creates the graphic of Terrain by using the shape of this
	private void graphicShapeConstructor(Polyline polyline) {
		this.shapeTerrain = new ShapeGraphics(polyline, Color.DARK_GRAY, Color.DARK_GRAY, 0.1f, 1.0f, 0.0f);
		this.shapeTerrain.setParent(getEntity());
	}
	
	
	// Redefine the method Draw to draw the graphic of Terrain
	@Override
	public void draw(Canvas canvas) {
		
		shapeTerrain.draw(canvas);
	}
	// Redefine the method getTransorm to get the transform of the Entity
	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	// Redefine the method getVelocity to get the velocity of the Entity
	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}
	
	@Override
	public void destroy() {
		super.destroy();
		this.getOwner().removeActor(this);
	}
	
	
	

}
