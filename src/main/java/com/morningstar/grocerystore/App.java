package com.morningstar.grocerystore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class App {
	public static void main(String[] args) {
		if (args == null || args.length != 1) {

			System.err.println("Please input data file name.");
			return;
		}

		InputStreamReader input = null;

		try {
			
			input = new InputStreamReader(new FileInputStream(args[0]));
			Dispatcher dispatcher = new Dispatcher();
			int time = dispatcher.run(input);
			System.out.println("Finished at: t="+time+"minutes");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		}
	}
}
