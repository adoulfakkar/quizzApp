package com.adoulfakkar.quizzApp.webapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Tools {

	public static void copyStream (InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[2048];
		int len = -1;
		while ((len = in.read(buffer, 0, 2048)) != -1) {
			out.write(buffer, 0, len);
		}
	}
}
