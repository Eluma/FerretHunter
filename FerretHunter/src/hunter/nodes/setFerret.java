package hunter.nodes;
import hunter.misc.Constants;
import hunter.misc.Variables;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.Path;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class SetFerret extends Node
{
	
	
	@Override
	public boolean activate() 
	{

			Tabs.INVENTORY.open();
		
		return Variables.maxTrapAmt>Variables.noOfTrap && Inventory.contains(Constants.BOX_TRAPID) ;
		
	}

	@Override
	public void execute() 
	{
		Variables.status="Setting Traps";
		
		final Tile ferretTile = new Tile (2308+Random.nextInt(0,6), 3509+Random.nextInt(0,6), 0);//area for catching ferret
		
		Path path = Walking.findPath(ferretTile);
		path.traverse();
		
		sleep(Random.nextInt(2000, 3500));
		
		final SceneObject placedBoxTrap = SceneEntities.getNearest(Constants.PLACED_BOX_TRAPID);
		final SceneObject BOX_TRAP = SceneEntities.getNearest(Constants.BOX_TRAP);
		
		if(placedBoxTrap!= null)
		{
			if(BOX_TRAP != null)
			{
				if(!Players.getLocal().getLocation().equals(BOX_TRAP.getLocation())
						&& !Players.getLocal().getLocation().equals(placedBoxTrap.getLocation()) )
					{
						layTrap();
					}
			}
			else if(!Players.getLocal().getLocation().equals(placedBoxTrap.getLocation() )) 
			{
				layTrap();
			}
		}
		else if(BOX_TRAP != null)
		{
			if(!Players.getLocal().getLocation().equals(BOX_TRAP.getLocation()) ) 
			{
				layTrap();
			}
		}
		else
			layTrap();
			
				
		sleep(300);
		}
		
	
	
	public void layTrap()
	{
		Item trap = Inventory.getItem(Constants.BOX_TRAPID);
		if(trap != null)
		{
			if(Players.getLocal().getAnimation() != -1 )
				sleep(3000);
			
			if(trap.getWidgetChild().interact("Lay"))
			{
				Variables.noOfTrap++;
				sleep(1500);	
			}
			
		}
	}
}
