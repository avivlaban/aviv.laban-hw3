package files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Streams {
	/**
	 * Read from an InputStream until a quote character (") is found, then read
	 * until another quote character is found and return the bytes in between the two quotes. 
	 * If no quote character was found return null, if only one, return the bytes from the quote to the end of the stream.
	 * @param in
	 * @return A list containing the bytes between the first occurrence of a quote character and the second.
	 */
	public static List<Byte> getQuoted(InputStream in) throws IOException {
		//Creates new list of Bytes that will hold the relevent Bytes
		List<Byte> returnedList = new ArrayList<Byte>();
		
		int index = 0;
		int currentByte = 0;
		
		
		while(index < 2){
			currentByte = in.read();
			
			if((currentByte == -1) && (index == 0)){
				in.close();
				return null;
			}
			
			else if((currentByte == -1) && (index==1)){
				in.close();
				return returnedList;
			}
			
			else if((currentByte == 34) && (index == 1)){
				in.close();
				return returnedList;
			}
			
			else if((currentByte == 34) && (index == 0)){
				index++;
			}
			
			else if((currentByte != -1) && (currentByte != 34) && (index == 1)){
				returnedList.add((byte)currentByte);
			}
			
			else if((currentByte == 34) && (index == 1)){
				in.close();
				return returnedList;
			}
		}
		return null;
	}
	
	
	/**
	 * Read from the input until a specific string is read, return the string read up to (not including) the endMark.
	 * @param in the Reader to read from
	 * @param endMark the string indicating to stop reading. 
	 * @return The string read up to (not including) the endMark (if the endMark is not found, return up to the end of the stream).
	 */
	public static String readUntil(Reader in, String endMark) throws IOException {
		StringBuilder results = new StringBuilder();
		int count = 0;
		int currentLetter = in.read();
		
		while(currentLetter != -1){
			
			if(count == endMark.length()){
				in.close();
				return results.toString();
			}
			
			if(count == 0){
				
				if((char)currentLetter != endMark.charAt(0)){
					in.mark(endMark.length() + 1);
					results.append((char)currentLetter);
				}
				
				else if((char)currentLetter == endMark.charAt(0)){
					count++;
				}
			}
			
			else if(count != 0){
				
				if((char)currentLetter == endMark.charAt(count)){
					count++;
				}
				
				else if((char)currentLetter != endMark.charAt(count)){
					in.reset();
					currentLetter = in.read();
					count = 0;
					results.append((char)currentLetter);
				}
			}
			currentLetter = in.read();
		}
		
		in.close();
		return results.toString();
	}
	
	/**
	 * Copy bytes from input to output, ignoring all occurrences of badByte.
	 * @param in
	 * @param out
	 * @param badByte
	 */
	public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {
		int currentByte = in.read();
		
		while(currentByte != -1){
			if((byte)currentByte != badByte){
				out.write(currentByte);
			}
			
			currentByte = in.read();
		}
	}
	
	/**
	 * Read a 48-bit (unsigned) integer from the stream and return it. The number is represented as five bytes, 
	 * with the most-significant byte first. 
	 * If the stream ends before 5 bytes are read, return -1.
	 * @param in
	 * @return the number read from the stream
	 */
	public static long readNumber(InputStream in) throws IOException {
		long result = 0;
		
		for(int i = 0; i < 5; i++){
			long temp = in.read();
			if(temp == -1){
				return -1;
			}
			result <<= 8;
			
			result |= temp;
			
		}
		in.close();
		return result;
	}
}
