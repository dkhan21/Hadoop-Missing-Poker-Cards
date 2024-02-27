import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.HashSet;
import javax.naming.Context;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FindPokerCards {

  public static class PokerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      //Retrieve each card value from the input text file 
      String[] line = value.toString().split("-");
      Text suit = new Text(line[0]);
      int suitRank = Integer.parseInt(line[1]);
      context.write(new Text(suit), new IntWritable(suitRank));
    }
  }

  public static class MissingPokerReducer
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable missingCardRank = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      ArrayList<Integer> rank = new ArrayList<>();
        //Putting all the values we have in a list
        for(IntWritable value: values) {
           rank.add(value.get());
        }
        //Putting all the missing cards in a list
        for(int i = 1; i <= 13; i++){
          if(!rank.contains(i)){
                missingCardRank.set(i);
                context.write(key, missingCardRank);
          }

        }
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "FindPokerCards");
    job.setJarByClass(FindPokerCards.class);
    job.setMapperClass(PokerMapper.class);
    job.setReducerClass(MissingPokerReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
