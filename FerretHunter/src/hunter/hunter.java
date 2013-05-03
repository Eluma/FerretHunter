package hunter;
import hunter.misc.variables;
import hunter.nodes.antiBan;
import hunter.nodes.huntFerret;
import hunter.nodes.release;
import hunter.nodes.setFerret;

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
import org.powerbot.game.api.util.SkillData;
import org.powerbot.game.api.util.SkillData.Rate;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.methods.input.Mouse;

@Manifest(authors = {"K"}, name = "K.Ferret Hunter", description = "Hunt Ferrets ", version = 0.2 )
public class hunter extends ActiveScript implements PaintListener 
{
	
	private static Tree jobContainer = null;
	private final static ArrayList<Node> jobs = new ArrayList<Node>();
	private final static Timer runTime = new Timer(0);
	private final SkillData sd = new SkillData(runTime);
	
	public void onStart()
	{
		variables.startTime = System.currentTimeMillis();
		variables.startingExperience = Skills.getExperience(Skills.HUNTER);
		variables.startingLevel = Skills.getLevel(Skills.HUNTER);
		
	}

	@Override
	public int loop() 
	{
		
		Tabs.INVENTORY.open();
		variables.maxTrapAmt = (int) 1+(Skills.getLevel(Skills.HUNTER)/20); // allows amount of trap to increase if user level abv requirements

		if (jobContainer != null) 
		{
			final Node job = jobContainer.state();
			if (job != null) 
			{
				jobContainer.set(job);
				getContainer().submit(job);
				job.join();
			}
		} 
		else 
		{
			jobs.add(new antiBan());
			jobs.add(new release());
			jobs.add(new huntFerret());
			jobs.add(new setFerret());
			
			
			jobContainer = new Tree(jobs.toArray(new Node[jobs.size()]));
		}
		
		

		
		return Random.nextInt(100, 350);
	}
	@Override
	public void onRepaint(Graphics graphics) 
	{
	     final Graphics2D g = (Graphics2D) graphics;
	     final Point p = Mouse.getLocation();
	     
	     
	     g.setColor(new Color(0, 0, 0, 99));
	     g.fillRect(1, 338, 517, 83);
	     
	     g.setColor(Color.WHITE);
	     g.drawString("Run Time         :" + runTime.toElapsedString(), 21, 360);
	     g.drawString("Experience Gained:" + sd.experience(Skills.HUNTER)+"("+sd.experience(Rate.HOUR, Skills.HUNTER)+")", 21, 375);
	     g.drawString("Level (gained)   :" + Skills.getLevel(Skills.HUNTER)+"(+"+ sd.level(Skills.HUNTER)+")", 21, 390);
	     g.drawString("Status           :" + variables.status,21,405);
	     g.setColor(Color.GRAY);
	     g.drawString("made by K.", 400, 420);
	     
	     g.setColor(Mouse.isPressed()?Color.WHITE:Color.RED);
	     g.drawOval(p.x-3, p.y-2, 3, 3);
	}
	

	
}
