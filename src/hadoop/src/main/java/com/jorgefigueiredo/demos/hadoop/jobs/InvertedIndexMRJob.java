package com.jorgefigueiredo.demos.hadoop.jobs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.jorgefigueiredo.demos.hadoop.mappers.InvertedIndexMapper;
import com.jorgefigueiredo.demos.hadoop.reducers.InvertedIndexReducer;

public class InvertedIndexMRJob extends Configured implements Tool {

	
    public static void main( String[] args ) throws Exception
    {
        int res = ToolRunner.run(new Configuration(), new InvertedIndexMRJob(), args);
        System.exit(res);
    }
	
	public int run(String[] args) throws Exception {
		
		Configuration conf = getConf();

		
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(InvertedIndexMRJob.class);
		
		
		job.setMapperClass(InvertedIndexMapper.class);
		job.setReducerClass(InvertedIndexReducer.class);
		
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		// Input
		FileInputFormat.addInputPath(job, new Path("input/text"));
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		// Output
		FileOutputFormat.setOutputPath(job, new Path("output/text"));
		//job.setOutputFormatClass(KeyValueTextOutputFormat.class);

		return job.waitForCompletion(true) ? 0 : 1;
		
	}

	
	
}
