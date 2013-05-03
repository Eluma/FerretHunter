import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.tab.Inventory;


public class Release extends Node{

	@Override
	public boolean activate() {
		Tabs.INVENTORY.open();
		return Inventory.contains(Variables.ferretID);
	}

	@Override
	public void execute() {
		System.out.print("contains ferret");
		while(Inventory.contains(Variables.ferretID))
		{
			Inventory.getItem(Variables.ferretID).getWidgetChild().interact("Release");
			sleep(400);
		}
		
	}

}
