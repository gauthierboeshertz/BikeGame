/*
 *	Author:      Marc Watine
 *	Date:        Nov 30, 2017
 */

package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Pendulum extends GameEntity implements Actor{
	
	// Declare the attributes needed to the creation of a Pendulum (constitute of 2 block)
	private Block topBlock;
	private Block botBlock;
	
	// Public creator of Pendulum 
	public Pendulum(ActorGame game, boolean fixed, Vector position, Vector topBlockPosition, Vector botBlockPosition, float lenght) {
		super(game, fixed, position);

		// Create 2 blocks
		if (topBlockPosition == null) {
			throw new NullPointerException ("The top Block's position of the pendulum must be somewhere ! Think of a place...");
		}
		topBlock = new Block(game, true, topBlockPosition,"metal.4.png" , 1.0f, 1.0f, false, 1.0f, 0.0f); 
		if (botBlockPosition == null) {
			throw new NullPointerException ("The bot Block's position of the pendulum must be somewhere ! Think of a place...");
		}
		botBlock = new Block(game, false, botBlockPosition, "glass.hollow.4.png", 8.5f, 1.0f, false, 1.0f, 0.0f);			
		
		// Check the lenght
		if (lenght <= 0.0f) {
			throw new IllegalArgumentException("La taille de la corde du pendule est nulle ou inférieur a 0");
		}
		
		// Create a link between the top block and the bot block
		RopeConstraintBuilder ropeConstraintBuilder = getOwner().ropeConstraintBuilderConstructor();
        ropeConstraintBuilder.setFirstEntity(topBlock.getEntity());
        ropeConstraintBuilder.setFirstAnchor(new Vector(topBlock.getWidth()/2, topBlock.getHeight()/2));
        ropeConstraintBuilder.setSecondEntity(botBlock.getEntity());
        ropeConstraintBuilder.setSecondAnchor(new Vector(botBlock.getWidth()/2 + 0.3f*botBlock.getWidth(), botBlock.getHeight()/2));
        if(lenght<0) {
        	throw new IllegalArgumentException("The pendulum's rope length must be positive!");
        }
        ropeConstraintBuilder.setMaxLength(lenght);
        ropeConstraintBuilder.setInternalCollision(false);
        ropeConstraintBuilder.build();
        
		// Create a link between the top block and the bot block
        RopeConstraintBuilder ropeConstraintBuilder2 = getOwner().ropeConstraintBuilderConstructor();
        ropeConstraintBuilder2.setFirstEntity(topBlock.getEntity());
        ropeConstraintBuilder2.setFirstAnchor(new Vector(topBlock.getWidth()/2, topBlock.getHeight()/2));
        ropeConstraintBuilder2.setSecondEntity(botBlock.getEntity());
        ropeConstraintBuilder2.setSecondAnchor(new Vector(botBlock.getWidth()/2 - 0.3f*botBlock.getWidth(), botBlock.getHeight()/2));
        ropeConstraintBuilder2.setMaxLength(lenght);
        ropeConstraintBuilder2.setInternalCollision(false);
        ropeConstraintBuilder2.build();
        
        
	}
	
	@Override
	public void destroy() {
		this.botBlock.destroy();
		this.topBlock.destroy();
		super.destroy();
		this.getOwner().removeActor(this);
	}

	
	@Override
	public void draw(Canvas canvas) {
		topBlock.draw(canvas);
		botBlock.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return this.getTransform();
	}

	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return this.getVelocity();
	}

}

