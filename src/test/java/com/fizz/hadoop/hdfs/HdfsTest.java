package com.fizz.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


/**
 * @author fizz
 * @version 2018/11/10 20:41
 */
public class HdfsTest {

	private Configuration conf = null;
	private FileSystem fs = null;

	@Test
	public void listStatus(Path path) throws IOException {
	}

	@Before
	public void setup() throws IOException {
		conf = new Configuration(true);
		fs = FileSystem.get(conf);
	}

	@After
	public void cleanup() throws IOException {
		if (fs != null) {
			fs.close();
		}
	}


}
