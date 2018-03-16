import java.util.HashMap;
import java.util.LinkedList;
/**
 * Class which stores a set of states and creates actions following the
 * E Greedy Policy.
 * 
 * I think something is wrong with how I choose Greedily however. 
 
 * Namely, we want to be able to choose a **valid** State Action Pair for each state with the Highest Q Value.
 * Does not want to cooperate in the sense that some actions seem non-sensical (Walking a circle in a 4 block square for instance).
 *  
 * More learning episodes or more randomness could potentially solve this problem. (Negative values are always avoided once discovered).
 * @author Lucas
 *
 */
public class EGreedyPolicy 
{
	public static final double INITIAL_E_GREEDY = 0.9;
	protected double eGreedy;
	protected HashMap<SAPair, Double> qTable;
	protected IceWorld world;
	
	public EGreedyPolicy(IceWorld grid, HashMap<SAPair, Double> Q, int n)
	{
		if (n >= 1000)
			eGreedy = 0.;
		else if (n >= 10)
			eGreedy = INITIAL_E_GREEDY / ((int) (n / 10));
		else
			eGreedy = INITIAL_E_GREEDY;
		
		qTable = Q;
		world = grid;
	}
	
	public Action chooseEGreedy(State s)
	{
		Action nextAction;
		double random = Math.random();
		double highestVal = Double.NEGATIVE_INFINITY;
		if (random >= eGreedy || eGreedy == 0.)
		{
			LinkedList<Action> possibleActions = new LinkedList<Action>();
			for (SAPair pairs: qTable.keySet())
			{
				if (pairs.getThisState().equals(s))
				{
					Double qVal = qTable.get(pairs);
			
					if(highestVal < qVal)
					{
						possibleActions.clear();
						highestVal = qVal;
					}
					if (qVal == highestVal)
					{
						possibleActions.add(pairs.getThisAction());
					}
				}
				else
					continue;
			}
			if (possibleActions.size() > 0)
				nextAction = possibleActions.get((int) (Math.random() * (double) (possibleActions.size())));
			else 
				nextAction = world.getAllActions().get((int) (Math.random() * (double)(world.getAllActions().size())));
				
		}
		else
		{
			nextAction = world.getAllActions().get((int) (Math.random() * (double)(world.getAllActions().size())));
		}
		
		return nextAction;
	}
	
	public double getQValue(SAPair pair) 
	{
		Double qVal = qTable.get(pair);
		if (!pair.isValid())
		{
			qVal = -1.0;
		}
		return qVal;
	}
	public void setQValue(SAPair pair, double D)
	{
		qTable.put(pair, D);
	}
	public double geteGreedy() 
	{
		return eGreedy;
	}

}
