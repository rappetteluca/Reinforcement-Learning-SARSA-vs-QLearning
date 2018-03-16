/**
 * A Container describing a State-Action Pair.
 * @author Lucas
 *
 */
public class SAPair 
{
	private State thisState;
	private Action thisAction;
	

	public SAPair(State state, Action action)
	{
		thisState = state;
		thisAction = action;
	}
	
	public boolean isValid()
	{
		if(thisAction.getActionName().equals(Action.UP))
		{
			int newX = thisState.getX() - 1;
			if (newX >= 0)
			{
				if (thisAction.getSlipped() != 0)
				{
					int newY = thisState.getY() + thisAction.getSlipped();
					if(newY >= 0 && (newY < IceWorld.numCols))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(thisAction.getActionName().equals(Action.DOWN))
		{
			int newX = thisState.getX() + 1;
			if (newX < IceWorld.numRows)
			{
				if (thisAction.getSlipped() != 0)
				{
					int newY = thisState.getY() + thisAction.getSlipped();
					if(newY >= 0 && (newY < IceWorld.numCols))
					{
						return true;
					}
					else
						return false;
				}
				else
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}
		else if(thisAction.getActionName().equals(Action.LEFT))
		{
			int newY = thisState.getY() - 1;
			if (newY >= 0)
			{
				if (thisAction.getSlipped() != 0)
				{
					int newX = thisState.getX() + thisAction.getSlipped();
					if(newX >= 0 && (newX < IceWorld.numRows))
					{
						return true;
					}
					else
						return false;
				}
				else
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		
		}
		else if(thisAction.getActionName().equals(Action.RIGHT))
		{
			int newY = thisState.getY() + 1;
			if (newY < IceWorld.numCols)
			{
				if (thisAction.getSlipped() != 0)
				{
					int newX = thisState.getX() + thisAction.getSlipped();
					if(newX >= 0 && (newX < IceWorld.numRows))
					{
						return true;
					}
					else
						return false;
				}
				else
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		
		}
		else
		{
			System.out.println("Critical Error: Next State Chosen incorrectly, returning null");
			return false;
		}
	}
	
	public State getThisState() {
		return thisState;
	}

	public Action getThisAction() {
		return thisAction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((thisState == null) ? 0 : thisState.hashCode());
		result = prime * result + ((thisAction == null) ? 0 : thisAction.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SAPair other = (SAPair) obj;
		if (thisState == null) {
			if (other.thisState != null)
				return false;
		} else if (!thisState.equals(other.thisState))
			return false;
		if (thisAction == null) {
			if (other.thisAction != null)
				return false;
		} else if (!thisAction.equals(other.thisAction))
			return false;
		return true;
	}
	
	public void setSlippage()
	{
		if (thisState.getStateType() == State.ICY_SPACE)
		{
			
			double chance = Math.random();
			//ARBITRARILY SLIPPING
			if (chance > .8 && chance <= .9)
			{
				thisAction.setSlipped(-1);
			}
			else if (chance > .9)
			{
				thisAction.setSlipped(1);
			}
		}
		else
		{
			thisAction.setSlipped(0);
		}
	}

}
