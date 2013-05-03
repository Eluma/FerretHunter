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
	public boolean activate() {
		if (Tabs.getCurrent() != Tabs.INVENTORY) {
			Tabs.INVENTORY.open();
		}
		return (Variables.maxTrapAmt>Variables.noOfTrap && Inventory.contains(Variables.boxTrapID));
		
	}

	@Override
	public void execute() {
		Variables.status="Setting Traps";
		
		Tile ferretTile = new Tile (2308+Random.nextInt(0,6), 3509+Random.nextInt(0,6), 0);//area for catching ferret
		
		Path path = Walking.findPath(ferretTile);
		path.traverse();
		
		sleep(Random.nextInt(2000, 3500));
		
		SceneObject placedBoxTrap = SceneEntities.getNearest(Variables.placedBoxTrapID);
		SceneObject boxTrap = SceneEntities.getNearest(Variables.boxTrap);
		
		if(placedBoxTrap!= null)
		{
			if(boxTrap != null)
			{
				if(!(Players.getLocal().getLocation()!= boxTrap.getLocation()|| Players.getLocal().getLocation()!= placedBoxTrap.getLocation()))
				{
					layTrap();
				}
			}
			else if(Players.getLocal().getLocation()!= placedBoxTrap.getLocation()) 
			{
				layTrap();
			}
		}
		else if(boxTrap != null)
		{
			if(Players.getLocal().getLocation()!= boxTrap.getLocation()) 
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
		Item trap = Inventory.getItem(Variables.boxTrapID);
		if(trap != null)
		{
			while(Players.getLocal().getAnimation() != -1 )
				sleep(500);
			
			trap.getWidgetChild().interact("Lay");
			sleep(1500);
			
			Variables.noOfTrap++;
		}
	}
}
