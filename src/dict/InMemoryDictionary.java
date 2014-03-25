package dict;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.TreeMap;



/**
 * Implements a persistent dictionary that can be held entirely in memory.
 * When flushed, it writes the entire dictionary back to a file.
 * 
 * The file format has one keyword per line:
 * <pre>word:def1:def2:def3,...</pre>
 * 
 * Note that an empty definition list is allowed (in which case the entry would have the form: <pre>word:</pre> 
 * 
 * @author talm
 *
 */
public class InMemoryDictionary extends TreeMap<String,String> implements PersistentDictionary  {
	private static final long serialVersionUID = 1L; // (because we're extending a serializable class)

	private File dictFile;
	
	public InMemoryDictionary(File dictFile) {
		this.dictFile = dictFile;
	}
	
	@Override
	public void open() throws IOException {
		if(!dictFile.exists()){
			return;
		}
		
		this.clear();
		
		RandomAccessFile newFile = new RandomAccessFile(dictFile, "rw");
		
		while(newFile.getFilePointer() < newFile.length()-1){
			String tempWord = newFile.readLine();
			String[] wordAndDef = tempWord.split(":");
			if(wordAndDef.length == 0){
				put("", "");
			}
			else if(wordAndDef.length == 1){
				put(wordAndDef[0], "");
			}
			else if(wordAndDef.length == 2){
				put(wordAndDef[0], wordAndDef[1]);
			}
			
		}
		
		newFile.close();
		
	}

	@Override
	public void close() throws IOException {
		dictFile.delete();
		dictFile.createNewFile();
		
		RandomAccessFile newFile = new RandomAccessFile(dictFile, "rw");
		
		for(String key : keySet()){
			newFile.writeBytes(key + ":" + get(key) + "\n");
		}
		
		newFile.close();
		
	}
	
}
