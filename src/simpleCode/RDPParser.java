package simpleCode;


import java.io.*;
import java.util.*;

public class RDPParser
{
	public static void main(String[] args)
	{
		/* 
		 * Check against hierarchy list, get -1 for the taxa name and -2 for the confidence
		 * 
		 * 
		 * */
		//Make this changeable from the command line later
		double threshold = 0.8;
		if(args.length == 1){
			threshold = Double.parseDouble(args[0]);
			//Will have to try it out etc.
			//Just to test it out...
			/*
			for(int i = 0; i < args.length; i++){
				System.out.print(args[i] + " ");
			}
			System.out.println();
			*/
		}
		
		//String[] hierarchy = {"domain", "phylum", "class", "order", "family", "genus"};
		List<String> hierarchy = new ArrayList<String>();
		hierarchy.add("domain");
		hierarchy.add("phylum");
		hierarchy.add("class");
		hierarchy.add("order");
		hierarchy.add("family");
		hierarchy.add("genus");
		BufferedReader br = null;
		
		try{
			//Change this to behave nicely later
			//Structure it to iterate over files in the directory.
			br = new BufferedReader(new FileReader("/Users/mbrown67/Documents/Fodor/Datasets/MarkExperiment/FullPooled/parserTest/test_09.fastqtoRDP.txt"));
			String byLine = br.readLine();
			//This can definitely be done better later
			//Make sure that it doesn't redo this for each of the files...
			HashMap<String, HashMap<String, Integer>> countAppearances = new HashMap<String, HashMap<String, Integer>>();
			countAppearances.put("domain", new HashMap<String, Integer>());
			countAppearances.put("phylum", new HashMap<String, Integer>());
			countAppearances.put("class", new HashMap<String, Integer>());
			countAppearances.put("order", new HashMap<String, Integer>());
			countAppearances.put("family", new HashMap<String, Integer>());
			countAppearances.put("genus", new HashMap<String, Integer>());
			//Maybe this should actually be a hashmap of hashmaps...
			//Need to make sure this persists across files being opened.
			//System.out.println(countAppearances.size());
			//HashMap<String, Integer> countAppearances = new HashMap<String, Integer>();
			//Will need to add corrections for this
			while(byLine != null){
				//System.out.println(byLine);
				String[] splitString = byLine.split("\t");
				for(int i = 0; i < splitString.length; i++){
					//System.out.println(splitString[i]);
					if(hierarchy.contains(splitString[i])){
						//System.out.println(splitString[i]);
						//System.out.println(hierarchy.indexOf(splitString[i]));
						//countAppearances[hierarchy.indexOf(splitString[i])]
						//System.out.println(splitString[i-2]);
						//Grab the index of the
						if(Double.parseDouble(splitString[i+1]) >= threshold){
							//System.out.println(splitString[i+1]);
							//HashMap testVal = countAppearances.get(hier)
							int matchVal = hierarchy.indexOf(splitString[i]);
							//System.out.println(splitString[i] + " " + matchVal);
							if((countAppearances.get(hierarchy.get(matchVal)).containsKey(splitString[i-1]))){
								(countAppearances.get(hierarchy.get(matchVal))).put(splitString[i-1], (countAppearances.get(hierarchy.get(matchVal))).get(splitString[i-1]) + 1);
							}
							else{
								(countAppearances.get(hierarchy.get(matchVal))).put(splitString[i-1], 1);
							}
						}
					}
				}
				byLine = br.readLine();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/Users/mbrown67/Documents/Fodor/Datasets/MarkExperiment/FullPooled/parserTest/simple.out")));
			for(String taxaLevel : hierarchy) {
				//System.out.print(taxaLevel + " ");
				for(String itemLevel : stringMap(countAppearances.get(taxaLevel))) {
					bw.write(taxaLevel + " " + itemLevel + "\n");
					//bw.write(cbuf, off, len);
				}
				//bw.write(stringMap(countAppearances.get(taxaLevel)));
			}
			bw.flush();
			bw.close();
			//printMap(countAppearances.get("phylum"));
			//System.out.println(countAppearances.keySet());
			//List<Map<String, Integer>> sortMap = new ArrayList<TreeMap<String, Integer>>(countAppearances);
			//printMap(countAppearances);
			//System.out.println();
			//printMap(sortMap);
			
		}
		
		catch (IOException ioe){ 
		   ioe.printStackTrace();
	       }
		finally{
			try {
			      if (br != null)
				 br.close();
			}
			catch(IOException ioe){
				System.out.println("Error in closing the buffered reader");
			}
		}
		
		//printMap(countAppearances.get("domain"));
		
	}
	public static void printMap(Map<String, Integer> map) {
		//Adding in code here to sort it as well.
		//Can't tell yu the taxonomic level, however.
	    for (Map.Entry<String, Integer> entry: map.entrySet()){
	    	System.out.println(entry.getKey() + " " + entry.getValue());
	    }
		/*
	    Iterator<> it = mp.entrySet().iterator();
	    
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    */
	}
	public static List<String> stringMap(Map<String, Integer> map) {
		//Adding in code here to sort it as well.
		//Can't tell yu the taxonomic level, however.
		List<String> returnedStrings = new ArrayList<String>();
	    for (Map.Entry<String, Integer> entry: map.entrySet()){
	    	//System.out.println(entry.getKey() + " " + entry.getValue());
	    	returnedStrings.add(entry.getKey() + " " + entry.getValue());
	    }
	    
	    return returnedStrings;
		/*
	    Iterator<> it = mp.entrySet().iterator();
	    
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    */
	}
	/*
	public static String[] parseLine(String someString){
		
	}
	*/
	
}
