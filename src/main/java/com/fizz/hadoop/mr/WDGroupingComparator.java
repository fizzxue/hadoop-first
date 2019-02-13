package com.fizz.hadoop.mr;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author fizz
 * @version 2019/2/13 20:03
 */
public class WDGroupingComparator extends WritableComparator {

	public WDGroupingComparator() {
		super(TQ.class, true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		TQ t1 = (TQ) a;
		TQ t2 = (TQ) b;
		int r1 = Integer.compare(t1.getYear(), t2.getYear());

		if (r1 == 0) {

			return Integer.compare(t1.getMonth(), t2.getMonth());
		}

		return r1;
	}
}
