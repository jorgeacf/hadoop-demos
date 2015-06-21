package com.jorgefigueiredo.hadoop.demos.jobs;

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

import com.jorgefigueiredo.hadoop.demos.mappers.TestMapperKeyValueInputFormat;
import com.jorgefigueiredo.hadoop.demos.reducers.TestReducerKeyValueInputFormat;


public class HadoopJobRunner2 extends Configured implements Tool
{

	
    public static void main( String[] args ) throws Exception
    {
        int res = ToolRunner.run(new Configuration(), new HadoopJobRunner2(), args);
        System.exit(res);
    }

	public int run(String[] args) throws Exception {
		
		Configuration conf = this.getConf();
		
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", ",");

		Job job = Job.getInstance(conf);
		job.setJarByClass(HadoopJobRunner2.class);
		
		
		job.setMapperClass(TestMapperKeyValueInputFormat.class);
		job.setReducerClass(TestReducerKeyValueInputFormat.class);
		
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		// Input
		FileInputFormat.addInputPath(job, new Path("input"));
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		// Output
		FileOutputFormat.setOutputPath(job, new Path("output"));
		//job.setOutputFormatClass(KeyValueTextOutputFormat.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}
}