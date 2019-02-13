package com.fizz.hadoop.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author fizz
 * @version 2019/2/13 18:15
 */
public class WDPartitioner extends Partitioner<TQ, IntWritable> {
	@Override
	public int getPartition(TQ key, IntWritable value, int numPartitions) {
		return key.hashCode() % numPartitions;
	}

}
