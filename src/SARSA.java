import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A Shell from which many SARSA episodes are processed.
 * 
 * Convergence Rate seems to be at a lofty 50% for this algorithm.
 * 
 * @author Lucas
 *
 */
public class SARSA 
{
	public HashMap<SAPair, Double> qTable;
	public IceWorld environment;
	
	public SARSA(IceWorld grid)
	{
		environment = grid;
		qTable = new HashMap<SAPair, Double>(100);
		State currentState;
		for (int i = 0; i < IceWorld.numRows; i++)
		{
			for (int j = 0; j < IceWorld.numCols; j++)
			{
				currentState = environment.gridStates[i][j];
				if (currentState.getStateType() != State.HOLE_SPACE)
				{
					for (Action a : environment.getAllActions())
					{
						SAPair pair = new SAPair(currentState, a);
						if (pair.isValid())
							qTable.put(pair, 0.0);
						
						
					}
				}
			}
		}
		try
		{
			File rewardFile = new File("SARSARewards.txt");
			File policyDumpFile = new File("SARSAEpisodicPolicies.txt");
			if (rewardFile.exists())
				rewardFile.delete();
			if (policyDumpFile.exists())
				policyDumpFile.delete();
			OutputStreamWriter rewardWriter = new OutputStreamWriter(new FileOutputStream(rewardFile), "UTF-8");
			OutputStreamWriter policyWriter = new OutputStreamWriter(new FileOutputStream(policyDumpFile), "UTF-8");
			
			
			int count = 1;
			while (count <= 2000)
			{
				SREpisode episode = new SREpisode(environment, qTable, count);
				episode.runEpisode();
				//rewardWriter.write("Reward gained from Episode ");
				//rewardWriter.write(String.valueOf(count));
				//rewardWriter.write(": ");
				rewardWriter.write(String.valueOf(episode.getTotalReward()));
				//rewardWriter.write("\tNumber of Actions to reach Goal: ");
				//rewardWriter.write(String.valueOf(episode.getNumberOfActions()));
				rewardWriter.write("\n");
				
				if (count % 100 == 0)
				{
					
					char[][] policyDump = episode.dumpPolicy();
					policyWriter.write("Episode ");
					policyWriter.write(String.valueOf(count));
					policyWriter.write("\n\n");
					for (int i = 0; i < policyDump.length; i++)
					{
						policyWriter.write(policyDump[i]);
						policyWriter.write("\n");
					}
					
					policyWriter.write("\n\n");
				}
				
				count++;
			}
			rewardWriter.flush();
			rewardWriter.close();
			policyWriter.flush();
			policyWriter.close();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
