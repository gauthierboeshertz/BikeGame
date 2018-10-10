/*
 *	Author:      Marc Watine
 *	Date:        Nov 30, 2017
 */

package ch.epfl.cs107.play.game.actor.particle;

import java.awt.Color;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.particle.ImageParticle;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WeldConstraint;
import ch.epfl.cs107.play.math.WeldConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;
/*
 * The Emitter's class. It creates multiple Particles given as arguments
 * when creating an instance. It can take any shape.
 * 
 */
public class Emitter extends GameEntity implements Actor{
	
	// An array of particles that will be drawn
	private ArrayList<ImageParticle> liste ;
	private Particle particle;
	
	// The shape that the Emitter will take
	private Shape area;
	
	// We need a maximum number of particles (it is not "final" as we might 
	// need to set a different number depending on the area the emitter has
	private int maxParticles;
	
	// We declare the builders, the constraint and a boolean to let us stop 
	// the creation of the particles
	private PartBuilder partBuilder;
	private ShapeGraphics graphic;
	private WeldConstraint constraint;
	private boolean stopCreation;
	
	// Emitter's constructor 
	public Emitter(ActorGame game, boolean fixed, Vector position, Particle particle, int maxParticles, Shape area) {
		super(game, fixed, position);
		liste = new ArrayList<ImageParticle>();
		if (particle == null) {
			throw new NullPointerException("The Particle is null");
		}
		this.particle = particle;
		if (maxParticles < 0) {
			throw new IllegalArgumentException ("The number of particles is negative... Keep it real !");
		}
		this.maxParticles = maxParticles;
		if (area == null) {
			throw new NullPointerException ("The area is null... Size matter !  ");
		}
		this.area = area;
		this.stopCreation = false;
		partBuilderConstructor();
		graphicConstructor();
		
	}
	// PartBuilder method
	private void partBuilderConstructor() {
		partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(area);
		partBuilder.setGhost(true);
		partBuilder.setDensity(0.001f);
		partBuilder.build();

	}
	// graphicConstructor method
	private void graphicConstructor() {
		graphic = new ShapeGraphics(area,null, null, 0.1f, 1.0f, 0.0f);
		graphic.setParent(getEntity());
		
	}
	
	// The method that will draw the particles
	@Override
	public void draw(Canvas canvas) {
		// If stopCreation is true, we want to stop this method
		if(stopCreation) {
			return;
		}
		
		// We keep adding as many particles as maxParticles
		if (liste.size() < maxParticles ) {
			liste.add((ImageParticle)particle.copy(getTransform().onPoint(area.sample())));
		}
		
		// We draw them all one by one, and if they are not visible anymore, we erase them
		// and add another one instead
		for (int i = 0; i<liste.size();++i ) {
			liste.get(i).draw(canvas);
			if (liste.get(i).getAlpha() <= 0.01) {
				liste.set(i, (ImageParticle)particle.copy(getTransform().onPoint(area.sample())));
			
			}
		}
		// If the emitter was to have a visible shape (here null)
		graphic.draw(canvas);
	}
	// Similar method as the one used in the Wheel class, but this time with a WeldConstraint
	public void attach(Entity vehicle, Vector position) {
		
		// Use a method added on ActorGame
		WeldConstraintBuilder constraintBuilder = getOwner().weldConstraintBuilderConstructor(); 
		constraintBuilder.setFirstEntity(vehicle);
		
		// point d'ancrage au véhicule : 
		constraintBuilder.setFirstAnchor(position);
		
		// Entity associée à la l'emetteur :
		constraintBuilder.setSecondEntity(getEntity());
		
		this.constraint = constraintBuilder.build();
	}
	
	// returns the entities transform
	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}
	
	// When we destroy the Emitter, we also want the creation of the particles to stop
	public void destroy() {
		super.destroy();
		stopCreation = true;
		
		
	}
	
	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}
	public void update(float deltaTime) {
		for (int i = 0; i<liste.size();++i ) {
			liste.get(i).update(deltaTime);
		}
	}
	
	
}
