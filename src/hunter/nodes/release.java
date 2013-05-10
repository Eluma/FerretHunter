package hunter.nodes;
import hunter.misc.Constants;
import hunter.misc.Variables;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.tab.Inventory;


public class Release extends Node
{

	@Override
	public boolean activate() 
	{
		Tabs.INVENTORY.open();
		return Inventory.contains(Constants.FERRET_ID);
	}

	@Override
	public void execute() 
	{
		Variables.status="Releasing Ferrets";
		while(Inventory.contains(Constants.FERRET_ID))
		{
			if(Inventory.getItem(Constants.FERRET_ID).getWidgetChild().interact("Release"))
			sleep(500);
		}
		
	}

}
