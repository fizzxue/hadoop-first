package com.fizz.hadoop.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author fizz
 * @version 2019/2/6 15:26
 * 温度客户端:查找每月温度前两高的两天
 */
public class WDClient {

	public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
		System.setProperty("HADOOP_USER_NAME", "root");
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "提取每月温度最高两天数据");

		job.setJarByClass(WDClient.class);

		FileInputFormat.addInputPath(job, new Path("/wd/input/wd.txt"));
		FileOutputFormat.setOutputPath(job, new Path("/wd/output/"));

		job.setMapperClass(WDMapper.class);
		job.setMapOutputKeyClass(TQ.class);
		job.setMapOutputValueClass(IntWritable.class);

		job.setPartitionerClass(WDPartitioner.class);
		job.setSortComparatorClass(WDSortComparator.class);

		job.setGroupingComparatorClass(WDGroupingComparator.class);

		job.setReducerClass(WDReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setNumReduceTasks(3);

		System.exit(job.waitForCompletion(true) ? 0 : -1);
	}
}
