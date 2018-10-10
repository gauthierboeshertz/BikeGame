/*
 *	Author:      Marc Watine
 *	Date:        Nov 30, 2017
 */

package ch.epfl.cs107.play.game.actor.particle;


import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.particle.Particle;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Image;

/*
 * The visible Particle object. It derives of Particle but is also caraterized
 * by a size and a level of transparency (alpha) and its depth, but also a method 
 * letting the Particle be drawn
 * 
 */
public class ImageParticle extends Particle{
	 // The useful attributes
	 private String name;
	 private float width;
	 private float height;
	 private float alpha;
	 private float depth;
	 
	 
	// The builder method of a particle from "scratch"
	public ImageParticle(ActorGame game, String name, float width, float height, float alpha, float depth, Vector position, Vector velocity, Vector acceleration, 
			float angularPosition, float angularVelocity, float angularAcceleration) {
		super(game, position, velocity, acceleration, angularPosition, angularPosition, angularPosition);
		if (name==null) {
			throw new NullPointerException ("The image name is null ! We want to draw something");
		}
		this.name = name;
		if (width<=0) {
			throw new IllegalArgumentException ("The width must be positive ! ");
		}
		this.width = width;
		if (height<=0) {
			throw new IllegalArgumentException ("The height must be positive ! ");
		}
		this.height = height;
		if ((alpha<0) || (alpha>1)) {
	   	throw new IllegalArgumentException ("The transparency (alpha) must be between 0 & 1 !");
	    }
	    this.alpha = alpha;
	    this.depth = depth;
	}
	
	// The builder method with a Particle as model, but lets the user changes it's movement properties
	public ImageParticle (ImageParticle particle, Vector position, Vector velocity, Vector acceleration, float angPos, float angVel, float angAcc) {
		super(particle.getOwner(), position, velocity, acceleration, angPos, angVel, angAcc);
		this.name = particle.getName();
		this.width = particle.getWidth();
	}
	
	// The builder method with a Particle as model, but this time only its position can be changed
	public ImageParticle (ImageParticle particle, Vector position) {
		super(particle, position);
		this.name = particle.getName();
		this.width = particle.getWidth();
		this.height = particle.getHeight();
		this.alpha = particle.getAlpha();
		this.depth = particle.getDepth();
	}
	// The method copying a particle and setting the copy to another position
	@Override
	public Particle copy(Vector position) {
		return new ImageParticle (this, position);
	}
	
	// Returns the name of the particle (the image name)
	public void setName(String name) {
        this.name = name;
    }

    // Returns image name, may be null 
    public String getName() {
        return name;
    }

    // Sets actual image width
    public void setWidth(float width) {
        this.width = width;
    }

    // Returns actual image width
    public float getWidth() {
        return width;
    }

    // Sets actual image height
    public void setHeight(float height) {
        this.height = height;
    }

    // Returns actual image height
    public float getHeight() {
        return height;
    }
    
    //Sets transparency
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    // Returns transparency, between 0 (invisible) and 1 (opaque) 
    public float getAlpha() {
        return alpha;
    }

    //Sets rendering depth
    public void setDepth(float depth) {
        this.depth = depth;
    }

    // Returns render priority, lower-values drawn first
    public float getDepth() {
        return depth;
    }
	
    // Draws the particles, making it more transparent each time it is drawn
    // so it has a limited time of life (will be erased by Emitter when invisible)
	@Override
    public void draw(Canvas canvas) {
        if (name == null)
            return;
        Image image = canvas.getImage(name);
        Transform transform = Transform.I.scaled(width, height).transformed(getTransform());
        canvas.drawImage(image, transform, alpha, depth);
        alpha -= 0.01;
    }


}
