# algorithm-for-supporting-industry-conferences-using-a-genetic-algorithm

This project was a study-related (3rd semester). My task was to write an algorithm that handles social networking among the participants of industry conferences. I have chosen a basic genetic algorithm for this excercise.

## More info

Typically, during conferences, participants handle networking and making new contacts by themselves. However, not everyone knows enough about each other or may have personality traits that make it difficult for them to establish such relationships. In this task, a special algorithm will be created to add additional value to such meetings.

A prerequisite is that each participant must specify both **(1)** their own attributes and **(2)** the attributes they are seeking. For example, a person might be an investor and art connoisseur, but at the conference, for certain reasons, they are particularly interested in talking to an investor as well as a potential programmer.

The matching criterion (objective function) is the measure of meeting the expectations of the conference participants, and it should be maximized during the algorithm's operation.


## Input file

The algorithm takes a list of guests and each one of them are described by **(1)** their own attributes and **(2)** the attributes they are looking for.

*An example:*

`1	DEVELOPER	INVESTOR,DEVELOPER`

The first guest is a DEVELOPER and an INVESTOR - he's looking for another DEVELOPER to talk to. 

```
1	DEVELOPER	INVESTOR,DEVELOPER
2	INVESTOR	SALES,MARKETING
3	DEVELOPER	DEVELOPER,ARCHITECT
4	PROJECT_MANAGER	DEVELOPER,ARCHITECT,PROBLEM_SOLVER,DESIGNER
5	DESIGNER,MARKETING	PROJECT_MANAGER,INVESTOR
6	ARCHITECT	INVESTOR,PROJECT_MANAGER,DEVELOPER
7	INVESTOR	INVESTOR
8	SALES	INVESTOR,PROJECT_MANAGER
9	DEVELOPER	DEVELOPER,PROBLEM_SOLVER,ARCHITECT
10	DEVELOPER	MARKETING,SALES
11	PROJECT_MANAGER	ARCHITECT,PROBLEM_SOLVER
12	PROBLEM_SOLVER	SALES
13	MARKETING	MARKETING,SALES
```

## How the algorithm works
*   Initialization.

    Start with initializing the first population. I choose random pairs of guests to match with each other and specify a list of proposed connections for each guest.
     
*   Population grading.

    Fitness score is specified and calculated. The higher the fitness score the better the match of participants' preferences with the proposed connections.

*   Selection of new population

    selectFunction method chooses the best matching propositions based on the fitness score as the majority of the new population. The rest is randomized to minimize the possibility of reaching a local maximum result.

*   Crossing over pairs
    
    crossoverFunction is used for interchanging proposed connections between a pair of guests.

\
    This algorithm iteratively improves the population, maximizing the fulfillment of participats' expectations.
## Results

```
The best solution in form of {GuestId=[ListOfGuestPropositions] is:
{1=[2, 6, 7, 12, 13], 2=[3, 8, 11, 12, 13], 3=[1, 5, 6, 9, 10], 4=[3, 6, 7, 9, 10], 5=[2, 4, 7, 9, 10], 6=[1, 2, 4, 7, 9], 7=[2, 5, 9, 10, 12], 8=[2, 3, 7, 10, 11], 9=[2, 3,
6, 10, 12], 10=[5, 7, 8, 9, 11], 11=[1, 3, 7, 8, 12], 12=[5, 6, 8, 9, 13], 13=[5, 7, 10, 11, 12]}
```
*Note: This is only an example of a solution*.
