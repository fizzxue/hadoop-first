package com.fizz.hadoop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author fizz
 * @version 2019/2/23 18:12
 * hbase java api test class
 */
public class HBaseTest {

	Configuration conf = null;
	Connection connection = null;
	Admin admin = null;
	TableName tn = TableName.valueOf("mytable");


	@Before
	public void init() throws IOException {
		conf = HBaseConfiguration.create();
		conf.addResource("core-site.xml");
		conf.addResource("hbase-site.xml");
		connection = ConnectionFactory.createConnection(conf);
		admin = connection.getAdmin();
	}


	@After
	public void destroy() throws IOException {
		if (!admin.isAborted()) {
			admin.close();
		}
		if (!connection.isClosed()) {
			connection.close();
		}
	}

	@Test
	public void create() throws IOException {
		HTableDescriptor desc = new HTableDescriptor(tn);
		HColumnDescriptor cf = new HColumnDescriptor("cf");
		desc.addFamily(cf);
		admin.createTable(desc);
	}

	@Test
	public void drop() throws IOException {
		if (admin.tableExists(tn)) {
			admin.disableTable(tn);
			admin.deleteTable(tn);
		}
	}

	@Test
	public void put() throws IOException {
		Table t = connection.getTable(tn);
		Put put = new Put("row1".getBytes());
		put.addColumn("cf".getBytes(), "name".getBytes(), "fizz".getBytes());
		Cell cell = CellUtil.createCell("row1".getBytes(), "cf".getBytes(), "age".getBytes(), System.currentTimeMillis(), KeyValue.Type.Put.getCode(), "25".getBytes());
		put.add(cell);
		t.put(put);
	}

	@Test
	public void scan() throws IOException {
		Table t = connection.getTable(tn);
		Get get = new Get("row1".getBytes());
		Result result = t.get(get);
		System.out.println(result.toString());
	}

	@Test
	public void delete() throws IOException {
		Table t = connection.getTable(tn);
		Delete delete = new Delete("row1".getBytes());
		delete.addColumn("cf".getBytes(), "age".getBytes());
		t.delete(delete);
		scan();
	}

}
