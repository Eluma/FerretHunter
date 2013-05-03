//script look for successful/unsuccessful box trap.
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class huntFerret extends Node
{

	@Override
	public boolean activate() {
		
		return (Variables.noOfTrap>1?true:false);
		
	}

	@Override
	public void execute() {
		Variables.status="Hunting";
		
		SceneObject boxTrap = SceneEntities.getNearest(Variables.boxTrap);  
		GroundItem g = GroundItems.getNearest(Variables.boxTrapID);

		if (Players.getLocal().getAnimation() == -1 && boxTrap != null) { //check for traps
			
			if(!boxTrap.isOnScreen()){
				Camera.turnTo(boxTrap);
			}
			
			if(boxTrap.getId()==19191){
				boxTrap.interact("Check");
				sleep(700);
			}
			else
			{
				boxTrap.interact("Dismantle");
				sleep(700);
			}
		
			Variables.noOfTrap--;//when traps fall, failed entity id will appear hence it will active as well causing a -ve in no.of traps
								 //not a problem as traps in inventory that cannot be placed will still + noOfTraps back
			sleep(400);
		}
		
		if(Players.getLocal().getAnimation() == -1 && g != null) //check for fallen traps
		{
			g.getLocation().interact("Take");
			Variables.noOfTrap--;
		}
		
	}
}
