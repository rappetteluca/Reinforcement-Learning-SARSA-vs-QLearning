import java.util.HashMap;
/**
 * An Episode following the Q-Learning Algorithm.
 * 
 * Works pretty dang well.
 * @author Lucas
 *
 */

public class QLEpisode extends Episode
{
	protected static final String AlgorithmType = "QLearning";
	
	public QLEpisode(IceWorld grid, HashMap<SAPair, Double> Q, int n)
	{
		super(grid, Q, n, AlgorithmType);

	}
	
	public void runEpisode()
	{
		if(canRun == true)
		{
			initialize();
			State nextState = null;
			while(currentState.getStateType() != (State.GOAL_SPACE) && actionsTaken < maxMoves)
			{
				Action nextAction = chooseEGreedy(currentState);
				SAPair pair = new SAPair(currentState, nextAction);
				pair.setSlippage();
				if (pair.isValid() == true)
				{
					nextState = evaluateStateTransitionFromAction(currentState, nextAction);
					
					Double qVal = qTable.get(pair);
					if (qVal == null)
					{
						qVal = 0.0;
					}
					double oneStepReward = rewardFunction(currentState, nextAction, nextState);
					totalRewardGained += oneStepReward;
					qTable.put(pair, 
						qVal + (oneStepReward + (DISCOUNT_FACTOR * maximalQvalNextState(nextState)) - qVal));
	
					currentState = nextState;
					nextAction.reset();
					actionsTaken++;
				}
			}
		}
		//Only run a unique episode once
		canRun = false;
	}
	
	public double maximalQvalNextState(State nextState) 
	{
		double highestVal = Double.NEGATIVE_INFINITY;
		for (Action a : world.getAllActions())
		{
			SAPair pair = new SAPair(nextState, a);
			Double qVal = qTable.get(pair);
			if (qVal == null)
				qVal = Double.NEGATIVE_INFINITY;
			
			if(highestVal < qVal && qVal > Double.NEGATIVE_INFINITY)
			{
				highestVal = qVal;
			}
		}
		
		if (nextState.getX() == world.getGoalX() && nextState.getY() == world.getGoalY())
		{
			highestVal = 0.0;
		}
		else if (nextState.getStateType() == State.ICY_SPACE || nextState.getStateType() == State.OPEN_SPACE
				|| nextState.getStateType() == State.START_SPACE || nextState.getStateType() == State.HOLE_SPACE)
		{
			if (highestVal == Double.NEGATIVE_INFINITY)
			{
				highestVal = 0.0;
			}
		}
		
		
		return highestVal;
	}
	
	public double[][] dumpQVals()
	{
		double[][] qVals = new double[world.getNumRows()][world.getNumCols()];
		for (int i = 0; i < world.getNumRows(); i++)
		{
			for (int j = 0; j < world.getNumCols(); j++)
			{
				qVals[i][j] = maximalQvalNextState(stateSpace[i][j]);
			}
		}
		return qVals;
	}
}
