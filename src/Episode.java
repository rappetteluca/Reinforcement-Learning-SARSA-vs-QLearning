import java.util.HashMap;

/**
 * An Abstract class designed to extend any typepe of Grid World Episode type.
 * Houses most of the big methods for this project.
 * @author Lucas
 *
 */

public abstract class Episode extends EGreedyPolicy
{
	public static final double DISCOUNT_FACTOR = 0.9;
	protected IceWorld world;
	protected State[][] stateSpace;
	protected State currentState;
	protected double totalRewardGained;
	protected int actionsTaken;
	protected int maxMoves;
	protected boolean canRun;
	protected String algorithmType;
	
	public abstract void runEpisode();
	
	public Episode(IceWorld grid, HashMap<SAPair, Double> Q, int n, String AlgorithmType)
	{
		super(grid, Q, n);
		algorithmType = AlgorithmType;
		world = grid;
		stateSpace = world.gridStates;
		maxMoves = 10 * world.getNumCols() * world.getNumRows();
		totalRewardGained = 0;
		actionsTaken = 0;
		canRun = true;
	}
	
	public void initialize()
	{
		currentState = stateSpace[world.getStartX()][world.getStartY()];
	}
	
	public State evaluateStateTransitionFromAction(State s, Action a)
	{
		State nextState = null;
		
		if (s.getStateType() == State.GOAL_SPACE)
		{
			a.setSlipped(0);
			nextState = s;
		}
		
		else if (s.getStateType() == State.HOLE_SPACE)
		{
			//Should never happen;
			System.out.println("Criticial Error: Hole Space detected as current state");
			//a.setSlipped(0);
			nextState = getStateFromTransition(s, a);
		}
		else
		{
			nextState = getStateFromTransition(s, a);
		}
		
		a.LedToHole(false);
		if (nextState.getStateType() == State.HOLE_SPACE)
		{
			//System.out.println("Fell into hole");
			a.LedToHole(true);
			nextState = s;
		}
		
		return nextState;
	}
	public State getStateFromTransition(State s, Action a)
	{
		State nextState = null;
		if(a.getActionName().equals(Action.UP))
		{
			int newX = s.getX() - 1;
			if (newX >= 0)
			{
				if (a.getSlipped() != 0)
				{
					int newY = s.getY() + a.getSlipped();
					if(newY >= 0 && (newY < world.getNumCols()))
					{
						nextState = stateSpace[newX][newY];
					}
					else
						nextState = s;
				}
				else
				{
					nextState = stateSpace[newX][s.getY()];
				}
			}
			else
			{
				nextState = s;
			}
		}
		else if(a.getActionName().equals(Action.DOWN))
		{
			int newX = s.getX() + 1;
			if (newX < world.getNumRows())
			{
				if (a.getSlipped() != 0)
				{
					int newY = s.getY() + a.getSlipped();
					if(newY >= 0 && (newY < world.getNumCols()))
					{
						nextState = stateSpace[newX][newY];
					}
					else
						nextState = s;
				}
				else
				{
					nextState = stateSpace[newX][s.getY()];
				}
			}
			else
			{
				nextState = s;
			}
		}
		else if(a.getActionName().equals(Action.LEFT))
		{
			int newY = s.getY() - 1;
			if (newY >= 0)
			{
				if (a.getSlipped() != 0)
				{
					int newX = s.getX() + a.getSlipped();
					if(newX >= 0 && (newX < world.getNumRows()))
					{
						nextState = stateSpace[newX][newY];
					}
					else
						nextState = s;
				}
				else
				{
					nextState = stateSpace[s.getX()][newY];
				}
			}
			else
			{
				nextState = s;
			}
		
		}
		else if(a.getActionName().equals(Action.RIGHT))
		{
			int newY = s.getY() + 1;
			if (newY < world.getNumCols())
			{
				if (a.getSlipped() != 0)
				{
					int newX = s.getX() + a.getSlipped();
					if(newX >= 0 && (newX < world.getNumRows()))
					{
						nextState = stateSpace[newX][newY];
					}
					else
						nextState = s;
				}
				else
				{
					nextState = stateSpace[s.getX()][newY];
				}
			}
			else
			{
				nextState = s;
			}
		
		}
		else
		{
			System.out.println("Critical Error: Next State Chosen incorrectly, returning null");
		}
		
		return nextState;
	}

	public double getTotalReward() 
	{
		return totalRewardGained;
	}
	public char[][] dumpPolicy()
	{
		char[][] policy = new char[world.getNumRows()][world.getNumCols()];
		for (int i = 0; i < world.getNumRows(); i++)
		{
			for (int j = 0; j < world.getNumCols(); j++)
			{
				if (i == world.getStartX() && j == world.getStartY())
				{
					policy[i][j] = State.START_SPACE;
				}
				else if (stateSpace[i][j].getStateType() == State.HOLE_SPACE)
				{
					policy[i][j] = State.HOLE_SPACE;
				}
				else
				{	
					policy[i][j] = ActionToChar(chooseEGreedy(stateSpace[i][j]));
				}
			}
		}
		return policy;
	}
	
	public char ActionToChar(Action a)
	{
		char output = Character.UNASSIGNED;
		
		if(a.getActionName().equals(Action.DOWN))
		{
			output = 'D';
		}
		else if(a.getActionName().equals(Action.UP))
		{
			output = 'U';
		}
		else if(a.getActionName().equals(Action.RIGHT))
		{
			output = 'R';
		}
		else if(a.getActionName().equals(Action.LEFT))
		{
			output = 'L';
		}
		
		return output;
	}
	
	public int getNumberOfActions()
	{
		return actionsTaken;
	}
	
	public double rewardFunction(State s, Action a, State sPrime)
	{
		double reward = 0.0;
		if(s.equals(sPrime))
		{
			//An Error was detected; discover the error
			if (a.ActionLedToHole() == true)
			{
				reward = -50;
				a.LedToHole(false);
			}
			else if(s.getX() == world.getGoalX() && s.getY() == world.getGoalY())
			{
				reward = 0.0;
			}
			else
			{
				//Must have slipped off the grid or Chosen a Dumb action
				//Could also be zero. doesn't seem to have a huge effect
				//if it's set to one or zero.
				//Edit: If reward is zero then the Episode is more likely to max out actions
				reward = -1.0;
			}
		}
		else
		{
			reward = sPrime.getReward();		
		}
		
		return reward;
	}


}
