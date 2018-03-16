/**
 * Definitions class describing an action and any unforseen circumstances
 * Caused by that action (such as slipping)
 * 
 * @author Lucas
 *
 */

public class Action 
{
	private String actionName;
	/**
	 * Super Important: Slipped denotes the status of slipping in a certain direction
	 * -1 = Slipped to the Left
	 * 0 = Did not slip
	 * 1 = Slipped to the right
	 */
	private int slipped = 0;
	private boolean ledToHole = false;
	public static final String UP = "Up";
    public static final String DOWN = "Down";
    public static final String LEFT = "Left";
    public static final String RIGHT = "Right";
    
	public Action(String action)
	{
		actionName = action;
	}
	
	public Action (Action toReplicate)
	{
		actionName = new String(toReplicate.getActionName());
		slipped = toReplicate.getSlipped();
		ledToHole = toReplicate.ActionLedToHole();
	}
	public int getSlipped() {
		return slipped;
	}

	public void setSlipped(int slipped) {
		this.slipped = slipped;
	}
	public String getActionName() {
		return actionName;
	}
	public boolean ActionLedToHole() {
		return ledToHole;
	}
	public void LedToHole(boolean ledToHole) {
		this.ledToHole = ledToHole;
	}
	
	public void reset()
	{
		slipped = 0;
		ledToHole = false;
	}
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionName == null) ? 0 : actionName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Action)
		{
			Action other = (Action) obj;
			if (actionName == null) {
				if (other.actionName != null)
					return false;
			} else if (!actionName.equals(other.actionName))
				return false;

		return true;
		}
		return false;
	}
	
}