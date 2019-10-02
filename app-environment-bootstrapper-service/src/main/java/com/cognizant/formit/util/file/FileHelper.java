package com.cognizant.formit.util.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.log4j.Logger;


import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.InputStream;
import org.apache.commons.lang.StringUtils;

public class FileHelper {

	static Logger logger = Logger.getLogger(FileHelper.class);

	/**
	 * Fetch the entire contents of a text file, and return it in a String. This style of implementation does not throw Exceptions to the
	 * caller.
	 *
	 * @param aFile is a file which already exists and can be read.
	 * @throws IOException
	 */
	static public String getFileContentAsString(File aFile) throws IOException {
		// ...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return contents.toString();
	}

	/**
	 * Work with file path
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	static public String getFileContentAsString(String filePath)
			throws IOException {
		File aFile = new File(filePath);
		return getFileContentAsString(aFile);
	}

	/**
	 * Reads an xml from a given path
	 *
	 * @param path
	 * @return FileInputStream representation of the xml being read
	 */
	public static FileInputStream readXMLAsFileInputStream(String path) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return fis;
	}
	/*
	 * return file or a null
	 * place appropriate null checking code while invoking this
	 */

	public static File getFile(String from) {
		logger.info("read file from following location : " + from);
		return !StringUtils.isEmpty(from) ? new File(from) : null;
	}

	/**
	 * checks if a file is not null, valid and readable one
	 *
	 * @param buildFile
	 * @return
	 */
	public static boolean isValid(File buildFile) {
		return (null != buildFile) && (buildFile.isFile()) && (buildFile.canRead());
	}

	/**
	 * get a
	 *
	 * @param from
	 * @return
	 */
	public static InputStream getStream(String from) throws FileNotFoundException, IOException {

		InputStream is = null;
		try {
			is = new FileInputStream(from);
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return is;

	}
}