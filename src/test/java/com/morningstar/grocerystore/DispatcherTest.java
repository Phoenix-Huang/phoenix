package com.morningstar.grocerystore;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.morningstar.grocerystore.util.KeyValuePair;

public class DispatcherTest {

	
	final static Logger logger = LoggerFactory.getLogger(DispatcherTest.class);
	private final static String NEW_LINE = System.getProperties().getProperty(
			"line.separator");
	private List<KeyValuePair<Integer, String>> data = null;

	@Before
	public void setUp() {
		this.data = new ArrayList<KeyValuePair<Integer, String>>();
		InputStreamReader input = null;
		BufferedReader bufferedReader = null;
		try {

			URL path = getClass().getResource("/testData.txt");
			input = new InputStreamReader(new FileInputStream(path.getPath()));

			bufferedReader = new BufferedReader(input);
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line + NEW_LINE);

			}

			String[] list = sb.toString().trim().split("#");
			for (String i : list) {
				
				String trimedString = i.trim();
				
				if (trimedString.isEmpty()) {
					continue;
				}
				
				BufferedReader sr = new BufferedReader(new StringReader(
						trimedString));
				String timeLine = sr.readLine();
				int time = Integer.parseInt(timeLine);
				
				sb = new StringBuilder();
				while ((line = sr.readLine()) != null) {
					sb.append(line + NEW_LINE);
				}
				String value = sb.toString().trim();
				
				KeyValuePair<Integer, String> item = new KeyValuePair<Integer, String>(
						time, value);
				this.data.add(item);				

			}

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				
				logger.error(e.getMessage(), e);
			}
		}

	}
	
	
	@Test
	public void testRun() {
		
		for (KeyValuePair<Integer, String> item : this.data) {
			Dispatcher dis = new Dispatcher();
			int time = dis.run(new StringReader(item.getValue()));			
			assertEquals(item.getKey().intValue(), time);

		}
	}

}
