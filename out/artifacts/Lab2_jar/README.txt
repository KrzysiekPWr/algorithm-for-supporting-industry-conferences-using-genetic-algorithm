When running the application you need to specify arguments in the commandline.
the right way to do this is for example:

java -jar Lab2.jar "C:\Users\krisk\OneDrive - Politechnika Wroclawska\Semestr III\Jezyki_programowania\Lab2\src\input.txt" 1000 5 30 6

The overall pattern is the following:
java -jar *NameOfTheJarFile* *PathToTheInputFile* *NumberOfGenerations* *SizeOfPropositionsList* *NumberOfParticipants* *NumberOfDescendants*

Participant - Map<Integer,Set<Integer>> which contains all Guests as a key and Set of guest proposed to them as a value

Generation - an existing population which is a List of Participants

NumberOfGenerations - this number specifies how many times the program will create a generation

SizeOfPropositionsList - this number specifies how long the propositions list is (ex. {1: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], ...} <- this size is 10

NumberOfParticipants - this number specifies how many Participants will be in one Generation

NumberOfDescendants - this number specifies how many Participants will be transfered directly to the next Generation