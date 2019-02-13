package com.fizz.hadoop.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author fizz
 * @version 2019/2/6 15:41
 */
public class WDMapper extends Mapper<LongWritable, Text, TQ, IntWritable> {

	TQ okey = new TQ();
	IntWritable ovalue = new IntWritable();

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		try {
			String str = value.toString();
			String[] split = str.split(":");
			String date = split[0];
			String wd = split[1];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date parse = sdf.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);
			okey.setYear(calendar.get(Calendar.YEAR));
			okey.setMonth(calendar.get(Calendar.MONTH) + 1);
			okey.setDay(calendar.get(Calendar.DAY_OF_MONTH));
			okey.setWd(Integer.parseInt(wd.substring(0, wd.length() - 1)));
			ovalue.set(Integer.parseInt(wd.substring(0, wd.length() - 1)));
			context.write(okey, ovalue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
