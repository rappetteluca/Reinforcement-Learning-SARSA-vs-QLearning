/**
 * Main Method for Testing and pumping out PolicyFiles
 * @author Lucas
 *
 */

public class ReinforcementLearning 
{

	public static void main(String[] args) 
	{
		IceWorld i = new IceWorld();
		new QLearn(i);
		new SARSA(i);

	}

}
