package hunter.nodes;
//script look for successful/unsuccessful box trap.
import hunter.misc.Constants;
import hunter.misc.Variables;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class HuntFerret extends Node
{

	@Override
	public boolean activate() 
	{
		
		return (Variables.noOfTrap>0);
		
	}

	@Override
	public void execute() 
	{
		Variables.status="Hunting";
		
		final SceneObject BOX_TRAP = SceneEntities.getNearest(Constants.BOX_TRAP);  
		final GroundItem g = GroundItems.getNearest(Constants.BOX_TRAPID);
		
		if(Players.getLocal().getAnimation() == -1 && g != null) //check for fallen traps
		{
			if(g.getLocation().interact("Take"))
			{
				Variables.noOfTrap--;
				sleep(Random.nextInt(1800, 2300));
			}
		}
		
		if (Players.getLocal().getAnimation() == -1 && BOX_TRAP != null) 
		{ //check for traps
			
			if(!BOX_TRAP.isOnScreen())
			{
				Camera.turnTo(BOX_TRAP);
			}
			if(BOX_TRAP.interact("Check"))
				sleep(1800);
			else
			{
				BOX_TRAP.interact("Dismantle");
				sleep(1800);
			}

			Variables.noOfTrap--;//when traps fall, failed entity id will appear hence it will active as well causing a -ve in no.of traps
								 //not a problem as traps in inventory that cannot be placed will still + noOfTraps back
			sleep(300);
		}
		

		
	}
}
