package com.fizz.hadoop.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author fizz
 * @version 2018/11/17 14:37
 *          mr wordcount example
 */
public class WordCount {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if (args.length < 2) {
			System.err.println("Usage: com.fizz.hadoop.mr.WordCount <in> <out>");
			System.exit(-1);
		}
		Job job = Job.getInstance(new Configuration(), "WordCount");
		job.setJarByClass(WordCount.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setReducerClass(MyReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : -1);

	}

	private static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();

			Text outKey = new Text("");
			IntWritable one = new IntWritable(1);

			String[] words = line.split(" ");
			for (String singleWord : words) {
				outKey.set(singleWord);
				context.write(outKey, one);
			}
		}
	}

	private static class MyReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int outNumSum = 0;
			IntWritable outNumSumWt = new IntWritable();
			for (IntWritable value : values) {
				outNumSum += value.get();
			}
			outNumSumWt.set(outNumSum);
			context.write(key, outNumSumWt);
		}
	}
}
