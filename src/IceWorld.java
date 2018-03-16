import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Houses most of the gridWorld definitions
 * @author Lucas
 *
 */
public class IceWorld 
{
	private static final String INPUT_FILE = "./iceWorld.txt";
	
	private ArrayList<Action> actions;
	private int startX;
	private int startY;
	private int goalX;
	private int goalY;
	public static int numCols;
	public static int numRows;
	public State[][] gridStates;
	
	public IceWorld()
	{
		try
		{
			File worldFile = new File(INPUT_FILE);
			Scanner dimensionScan = new Scanner(worldFile);
			int i = 1;
			numCols = dimensionScan.nextLine().length();
			while (dimensionScan.hasNextLine())
			{
				i++;
				dimensionScan.nextLine();
			}
			dimensionScan.close();
			numRows = i;
			FileReader read = new FileReader(worldFile);
			BufferedReader input = new BufferedReader(read);
			gridStates = new State[numRows][numCols];
			for(int rowNum = 0; rowNum < gridStates.length; rowNum++)
			{
				for (int colNum = 0; colNum < gridStates[rowNum].length; colNum++)
				{
					char stateType = (char) input.read();
					
					if (stateType == State.GOAL_SPACE || stateType == State.HOLE_SPACE || stateType == State.ICY_SPACE ||
					stateType == State.OPEN_SPACE|| stateType == State.START_SPACE)
					{
						gridStates[rowNum][colNum] = new State(stateType, rowNum, colNum);
					}
					else
					{
						stateType = (char) input.read();
						gridStates[rowNum][colNum] = new State(stateType, rowNum, colNum);
					}
					
					if (stateType == State.GOAL_SPACE)
					{
						goalX = rowNum;
						goalY = colNum;
					}
					if (stateType == State.START_SPACE)
					{
						startX = rowNum;
						startY = colNum;
					}
				}
			}
			input.close();
			initializeActions();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<Action> getAllActions() {
		return actions;
	}
	
	public void initializeActions()
	{
		actions = new ArrayList<Action>();
		actions.add(new Action(Action.UP));
		actions.add(new Action(Action.DOWN));
		actions.add(new Action(Action.LEFT));
		actions.add(new Action(Action.RIGHT));
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getGoalX() {
		return goalX;
	}

	public int getGoalY() {
		return goalY;
	}

	public int getNumCols() {
		return numCols;
	}

	public int getNumRows() {
		return numRows;
	}


}
