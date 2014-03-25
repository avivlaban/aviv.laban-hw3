package files;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccess {
	
	/**
	 * Treat the file as an array of (unsigned) 8-bit values and sort them 
	 * in-place using a bubble-sort algorithm.
	 * You may not read the whole file into memory! 
	 * @param file
	 */
	public static void sortBytes(RandomAccessFile file) throws IOException {
		
		int first = 0;
		int seconed = 0;
		int check = 1;
		int index = 0;
		
		while(check == 1){
			index = 0;
			file.seek(0);
			check = 2;
			
			while(file.read() != -1){
				file.seek(index);
				first = file.read();
				seconed = file.read();
				
				if(first > seconed){
					check = 1;
					file.seek(index);
					file.write(seconed);
					file.write(first);
				}
				index++;
			}
			
		}
	}
	
	/**
	 * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
	 * them in-place using a bubble-sort algorithm. 
	 * You may not read the whole file into memory! 
	 * @param file
	 * @throws IOException
	 */
	public static void sortTriBytes(RandomAccessFile file) throws IOException {
		int first_first = 0;
		int	first_secend = 0;
		int first_thired = 0;
		int seconed_first = 0;
		int seconed_seconed = 0;
		int second_thired = 0;
		
		int check = 1;
		int index = 0;
		
		while(check == 1){
			index = 0;
			file.seek(0);
			check = 2;
			
			while(file.read() != -1){
				file.seek(index);
				first_first = file.read();
				first_secend = file.read();
				first_thired = file.read();
				seconed_first = file.read();
				seconed_seconed = file.read();
				second_thired = file.read();
				
				if((first_first > seconed_first) || ((first_first == seconed_first) && (first_secend > seconed_seconed))
						 || ((first_first == seconed_first) && (first_secend == seconed_seconed) && (first_thired > second_thired))){
					check = 1;
					file.seek(index);
					file.write(seconed_first);
					file.write(seconed_seconed);
					file.write(second_thired);
					file.write(first_first);
					file.write(first_secend);
					file.write(first_thired);
				}
				index = index + 3;
			}
			
		}
	
	}
	
	/**
	 * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
	 * them in-place using a quicksort algorithm. 
	 * You may not read the whole file into memory! 
	 * @param file
	 * @throws IOException
	 */
	public static void sortTriBytesQuick(RandomAccessFile file) throws IOException {
		// TODO: implement
	}
}
