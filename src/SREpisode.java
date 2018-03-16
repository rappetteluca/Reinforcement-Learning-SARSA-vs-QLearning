import java.util.HashMap;

/**
 * An Episode following the SARSA Algorithm.
 * 
 *
 * @author Lucas
 *
 */

public class SREpisode extends Episode
{
	
	Action currentAction;
	SAPair currentPair;
	SAPair nextPair;
	Action nextAction;
	State nextState;
	
	public SREpisode(IceWorld grid, HashMap<SAPair, Double> Q, int n)
	{
		super(grid, Q, n, "SARSA");

	}
	
	public void runEpisode()
	{
		if(canRun == true)
		{
			initialize();
			currentAction = new Action(chooseEGreedy(currentState));
			while(currentState.getStateType() != (State.GOAL_SPACE) && actionsTaken < maxMoves)
			{
				currentPair = new SAPair(currentState, currentAction);
				currentPair.setSlippage();
				
					nextState = evaluateStateTransitionFromAction(currentState, currentAction);
						nextAction = new Action(chooseEGreedy(nextState));
						nextPair = new SAPair(nextState, nextAction);
					
						Double qVal = qTable.get(currentPair);
						if (qVal == null)
						{
							qVal = 0.0;
						}
						double oneStepReward = rewardFunction(currentState, currentAction, nextState);
						totalRewardGained += oneStepReward;
						qTable.put(currentPair, 
								qVal + (oneStepReward + (DISCOUNT_FACTOR * getQValue(nextPair)) - qVal));
					
					
						currentState = nextState;
						currentAction = nextAction;
						actionsTaken++;
					}
			}
		//Only run a unique episode once
		canRun = false;
		}
	}
