# Hadoop-Missing-Poker-Cards

## Introduction 
This MapReduce program, implemented in Java, is designed to analyze sets of poker cards and identify any missing cards within each suit. It leverages the Apache Hadoop framework to distribute the processing of large datasets across a cluster of computers.

## How It Works
1. Map Phase (PokerMapper):

      * Reads input lines, assuming each line represents a poker card in the format "Suit-Rank."
      * Extracts the suit and rank information and emits key-value pairs, where the suit is the key, and the rank is the value.

2. Reduce Phase (MissingPokerReducer):
   
      * Receives key-value pairs grouped by the suit.
      * Creates a list of ranks associated with each suit.
      * Identifies missing ranks within each suit and outputs the missing rank along with the suit.
  
3. Output:

      * The final output includes the suits and the ranks of missing poker cards within each suit.

## Usage Instructions
1. Have 2 Virtual Machine Instances. Running on Ubuntu-Linux-20.04
    * Master
    * Slave
2. Setup Hadoop and deploy it Fully Distributed.
    * Hadoop Version 2.6.5
    * Java Version: 8
    * Node Count: 2
    * SSH Authentication Method: Passphraseless SSH with RSA( Between Both Instances)
## Start running on both Master and Slave Node - Run these commands
3. sbin/start-dfs.sh
4. sbin/start-yarn.sh
## Import java program into hadoop and setup job
5. javac FindPokerCards.java -cp $(hadoop classpath)
6. jar cf fpc.jar FindPokerCards*.class
7. bin/hdfs dfs -rm -r /input
8. bin/hdfs dfs -rm -r /output
9. rm -rf output
10. bin/hdfs dfs -mkdir /input
11. bin/hdfs dfs -put PokerInput.txt /input/
## Start MapReduce Job
12. bin/hadoop jar fpc.jar FindPokerCards /input /output
## See output
13.  bin/hdfs dfs -get /output
14.  cd output
15.  cat part-r-00000

# Example of ["Poker Input Text"](PokerInput.txt)
Club-1
Club-2
Club-3
Club-4
Club-5
Club-6
Club-7
Club-8
Club-9
Club-10
Club-11
Club-12
Heart-1
Heart-2
Heart-3
Heart-4
Heart-5
Heart-6
Heart-7
Heart-8
Heart-9
Heart-10
Heart-11
Heart-12
Spade-1
Spade-2
Spade-3
Spade-4
Spade-5
Spade-6
Spade-7
Spade-8
Spade-9
Spade-10
Spade-11
Spade-12
Diamond-1
Diamond-2
Diamond-3
Diamond-4
Diamond-5
Diamond-6
Diamond-7
Diamond-8
Diamond-9
Diamond-10
Diamond-11
Diamond-12

# Example of ["Poker Output Text"](PokerOutput.txt)

Club    13
Diamond 13
Heart   13
Spade   13
