#  Reinforcement Learning Optimality SARSA VS QLearning
## CS452

## Author(s):

Lucas Rappette

## Date:

3/16/18


## Description:

- This Project's "main" class is ReinforcementLearning.java; It doesn't take any arguments.
	- To start learning on a new Ice World simply replace "iceWorld.txt" with any valid grid specification.
	- The 4 Text Files included are automatically generated by the program. The "Rewards" text files are the rewards gained for each episode for a specific algorithm. This was to allow ease of copying and pasting in to Excel. They are many additional formatting Strings (for your readability) commented out inside the source code.
	- __A more in depth description is attached, it is the original assignment description.__ This file is called _README2.pdf_. 
- PDF Note: Since I have no idea how to create PDF files on the fly I have included a premade pdf of a sample run I performed earlier today.
You can create your own graphs following my exact same layout by copying and pasting the rewards distribution from the output files into the appropriate columns in the Xcel document I have included.
- Overall the Q-Learning Algorithm works way better then the SARSA algorithm, which is counterintuitive. Additional guidance on why SARSA settles for
local minimums which leads to painful converging would be needed to make this a good program. **(Read below)**.

## How to build the software

Add this project to any Java IDE, it will automatically compile.
If this does not work execute the command below on the command line to build the project.

```
javac -d bin -sourcepath src src/*
```


## How to use the software

Execute the command below on a command line in the directory, or run from the 
IDE with runtime arguments. Output is generated in the text files, and can be copied to the excel spreadsheat in the appropriate columns to illustrate
the algorithms running over the same IceWorld instance. Results can then be inferred based on the graph produced.

```
java -cp bin; ReinforcementLearning
```


## How the software was tested

Testing was completed by using many many test runs for both SARSA and QLearning.


## Known bugs and problem areas

None.

**Instructor Note: The "weird" results you noted for SARSA are actually exactly what we expect.  Due to its "on-policy" approach it tends to play it safe.  With a much longer time horizon and slower randomness reduction, we could probably get rid of this, but over the time given, the difference between the two algorithms is to be expected.**