

import java.io.*;

/*
 * SimpleFileWriter.java
 *
 * Mikael Andersson, Lund Institute of Technology, 2002-2005
 *
 */
 
public class SimpleFileWriter 
{
	FileWriter aFileWriter;
	
	String aNewLine = "\r\n";
	
	/*
	 * Creates a new SimpleFileWriter
	 *
	 * @param	pFileName		The filename to use
	 * @param	pAppend			Whether to append to existing data in file
	 *
	 */
	public SimpleFileWriter(String pFileName,
							boolean pAppend)
	{
		try
		{
			aFileWriter = new FileWriter(pFileName, pAppend);
		}
        catch(IOException pIOE)
        {
        	System.out.println(pIOE.toString());
        }
	}
	
	/*
	 * Closes the fileprinter
	 *
	 */
	public void close()
	{
		try
		{
			aFileWriter.close();
		}
		catch(IOException pIOE)
		{
			System.out.println(pIOE.toString());	
		}
	}
	
	/*
	 * Appends a new line to the file
	 *
	 * @param	pString		The string to append to the file
	 *
	 */
	public void println(String pString)
	{		
		try
		{
			aFileWriter.write(pString, 0, pString.length());
			aFileWriter.write(aNewLine, 0, aNewLine.length());
        }
        catch(IOException pIOE)
        {
        	System.out.println(pIOE.toString());
        }       
	}	
	
	/*
	 * Appends a text to the file
	 *
	 * @param	pString		The string to append to the file
	 *
	 */
	public void print(String pString)
	{		
		try
		{
			aFileWriter.write(pString, 0, pString.length());
        }
        catch(IOException pIOE)
        {
        	System.out.println(pIOE.toString());
        }       
	}	
}