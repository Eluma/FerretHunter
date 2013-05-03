import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.methods.input.Mouse;

@Manifest(authors = {"K"}, name = "K.Ferret Hunter", description = "Hunt Ferrets ", version = 0.2 )
public class Hunter extends ActiveScript implements PaintListener {
	
	public static Tree jobContainer = null;
	public static ArrayList<Node> jobs = new ArrayList<Node>();
	public static Timer runTime = new Timer(0);
	public void onStart()
	{
		Variables.startTime = System.currentTimeMillis();
		Variables.startingExperience = Skills.getExperience(Skills.HUNTER);
		Variables.startingLevel = Skills.getLevel(Skills.HUNTER);
		
	}

	@Override
	public int loop() {
		
		Tabs.INVENTORY.open();
		Variables.maxTrapAmt = (int) 1+(Skills.getLevel(Skills.HUNTER)/20); // allows amount of trap to increase if user level abv requirements

		if (jobContainer != null) {
			final Node job = jobContainer.state();
			if (job != null) {
				jobContainer.set(job);
				getContainer().submit(job);
				job.join();
			}
		} else {
			jobs.add(new AntiBan());
			jobs.add(new Release());
			jobs.add(new setFerret());
			jobs.add(new huntFerret());
			
			jobContainer = new Tree(jobs.toArray(new Node[jobs.size()]));
		}
		
		

		
		return Random.nextInt(100, 350);
	}
	@Override
	public void onRepaint(Graphics graphics) {
	     final Graphics2D g = (Graphics2D) graphics;
	     final Point p = Mouse.getLocation();
	     long expPerHour = (Skills.getExperience(Skills.HUNTER)-Variables.startingExperience)/runTime.getElapsed()*3600000;
	     
	     g.setColor(new Color(0, 0, 0, 99));
	     g.fillRect(1, 338, 517, 83);
	     
	     g.setColor(Color.WHITE);
	     g.drawString("Run Time         :" + runTime.toElapsedString(), 21, 360);
	     g.drawString("Experience Gained:" + (Skills.getExperience(Skills.HUNTER)-Variables.startingExperience)+"("+expPerHour+")", 21, 375);
	     g.drawString("Level (gained)   :" + Skills.getLevel(Skills.HUNTER)+"(+"+ (Skills.getLevel(Skills.HUNTER)-Variables.startingLevel)+")", 21, 390);
	     g.drawString("Status           :" + Variables.status,21,405);
	     g.setColor(Color.GRAY);
	     g.drawString("made by K.", 400, 420);
	     
	     g.setColor(Mouse.isPressed()?Color.WHITE:Color.RED);
	     g.drawOval(p.x-3, p.y-2, 3, 3);
	}
	

	
}
