package hunter.nodes;
import hunter.misc.constants;
import hunter.misc.variables;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.tab.Inventory;


public class release extends Node
{

	@Override
	public boolean activate() 
	{
		Tabs.INVENTORY.open();
		return Inventory.contains(constants.ferretID);
	}

	@Override
	public void execute() 
	{
		variables.status="Releasing Ferrets";
		while(Inventory.contains(constants.ferretID))
		{
			if(Inventory.getItem(constants.ferretID).getWidgetChild().interact("Release"))
			sleep(500);
		}
		
	}

}
