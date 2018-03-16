/**
 * Class For Storing a specific State in our Grid World
 * @author Lucas
 *
 */
public class State 
{

	public static final char OPEN_SPACE = 'O';
	public static final char HOLE_SPACE = 'H';
	public static final char ICY_SPACE = 'I';
	public static final char START_SPACE = 'S';
	public static final char GOAL_SPACE = 'G';

	private int x;
	private int y;
	private double reward;
	private char stateType;
	
	public State(char type, int x, int y)
	{
		stateType = type;
		this.x = x;
		this.y = y;
		reward = -1; //Safety Check;
		switch(stateType)
		{
			case OPEN_SPACE:
				reward = -1;
				break;
			case ICY_SPACE:
				reward = -1;
				break;
			case START_SPACE:
				reward = -1;
				break;
			case HOLE_SPACE:
				reward = -50;
				break;
			case GOAL_SPACE:
				reward = 100;
				break;
				
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getStateType() {
		return stateType;
	}

	public double getReward() {
		return reward;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)(Math.abs(reward));
		result = prime * result + stateType;
		result = prime * result + x;
		result = prime * result + y;
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
		State other = (State) obj;
		if (reward != other.reward)
			return false;
		if (stateType != other.stateType)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	
	
	
	
}
