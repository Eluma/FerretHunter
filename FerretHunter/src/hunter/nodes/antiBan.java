package hunter.nodes;
import org.powerbot.game.api.util.Random;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;

public class antiBan extends Node
{

		 
	    @Override
	    public boolean activate() 
	    {
	        return Players.getLocal().getAnimation() != -1 ;
	    }
	 
	    @Override
	    public void execute() 
	    {
	    	
	        switch (Random.nextInt(0, 200)) 
	        {
	            case 0:
	                Camera.setAngle(Random.nextInt(10, 80));
	                break;
	            case 1:
	                Camera.setAngle(Random.nextInt(80, 220));
	                break;
	            case 2:
	                Tabs.STATS.open();
	                Mouse.move(644, 465);
	                sleep(1000, 2000);
	                Tabs.INVENTORY.open();
	                break;
	            case 3:
	                Tabs.FRIENDS.open();
	                sleep(1000, 2000);
	                Tabs.INVENTORY.open();
	                break;
	            
	        }
	    }
}
