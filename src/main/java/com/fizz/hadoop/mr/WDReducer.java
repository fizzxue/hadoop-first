package com.fizz.hadoop.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author fizz
 * @version 2019/2/13 20:06
 */
public class WDReducer extends Reducer<TQ, IntWritable, Text, IntWritable> {

	private Text okey = new Text();
	private IntWritable ovalue = new IntWritable();


	@Override
	protected void reduce(TQ key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

		int flag = 0;
		int fday = 0;

		for (IntWritable value : values) {
			if (flag == 0) {
				okey.set(key.toString());
				ovalue.set(value.get());
				context.write(okey, ovalue);
				flag++;
				fday = key.getDay();
			}

			if (flag != 0 && fday != key.getDay()) {
				okey.set(key.toString());
				ovalue.set(value.get());
				context.write(okey, ovalue);
				break;
			}

		}
	}

}
