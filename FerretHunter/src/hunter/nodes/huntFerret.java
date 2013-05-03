package hunter.nodes;
//script look for successful/unsuccessful box trap.
import hunter.misc.constants;
import hunter.misc.variables;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class huntFerret extends Node
{

	@Override
	public boolean activate() 
	{
		
		return (variables.noOfTrap>0);
		
	}

	@Override
	public void execute() 
	{
		variables.status="Hunting";
		
		final SceneObject boxTrap = SceneEntities.getNearest(constants.boxTrap);  
		final GroundItem g = GroundItems.getNearest(constants.boxTrapID);
		
		if(Players.getLocal().getAnimation() == -1 && g != null) //check for fallen traps
		{
			if(g.getLocation().interact("Take"))
			{
				variables.noOfTrap--;
				sleep(Random.nextInt(1800, 2300));
			}
		}
		
		if (Players.getLocal().getAnimation() == -1 && boxTrap != null) 
		{ //check for traps
			
			if(!boxTrap.isOnScreen())
			{
				Camera.turnTo(boxTrap);
			}
			if(boxTrap.interact("Check"))
				sleep(1800);
			else
			{
				boxTrap.interact("Dismantle");
				sleep(1800);
			}

			variables.noOfTrap--;//when traps fall, failed entity id will appear hence it will active as well causing a -ve in no.of traps
								 //not a problem as traps in inventory that cannot be placed will still + noOfTraps back
			sleep(300);
		}
		

		
	}
}
