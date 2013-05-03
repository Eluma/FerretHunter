package hunter.nodes;
import hunter.misc.constants;
import hunter.misc.variables;

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

public class setFerret extends Node
{
	
	
	@Override
	public boolean activate() 
	{

			Tabs.INVENTORY.open();
		
		return variables.maxTrapAmt>variables.noOfTrap && Inventory.contains(constants.boxTrapID) ;
		
	}

	@Override
	public void execute() 
	{
		variables.status="Setting Traps";
		
		final Tile ferretTile = new Tile (2308+Random.nextInt(0,6), 3509+Random.nextInt(0,6), 0);//area for catching ferret
		
		Path path = Walking.findPath(ferretTile);
		path.traverse();
		
		sleep(Random.nextInt(2000, 3500));
		
		final SceneObject placedBoxTrap = SceneEntities.getNearest(constants.placedBoxTrapID);
		final SceneObject boxTrap = SceneEntities.getNearest(constants.boxTrap);
		
		if(placedBoxTrap!= null)
		{
			if(boxTrap != null)
			{
				if(!Players.getLocal().getLocation().equals(boxTrap.getLocation()) )
					if(!Players.getLocal().getLocation().equals(placedBoxTrap.getLocation()) )
					{
						layTrap();
					}
			}
			else if(!Players.getLocal().getLocation().equals(placedBoxTrap.getLocation() )) 
			{
				layTrap();
			}
		}
		else if(boxTrap != null)
		{
			if(!Players.getLocal().getLocation().equals(boxTrap.getLocation()) ) 
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
		Item trap = Inventory.getItem(constants.boxTrapID);
		if(trap != null)
		{
			if(Players.getLocal().getAnimation() != -1 )
				sleep(3000);
			
			if(trap.getWidgetChild().interact("Lay"))
			{
				variables.noOfTrap++;
				sleep(1500);	
			}
			
		}
	}
}
