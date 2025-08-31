import java.io.*;
import java.util.*;
/**
 * This HitList class makes a hit list of every pair of files in a directory.
 *
 * @author  (your name)
 * @version (todays date)
 */
public class HitList
{
    private String dir;
    private int numWords, minHits;
    private FileReader reader;
    
    public HitList(String directory, int numWords, int minHits)
    {
        this.dir = directory;
        this.numWords = numWords;
        this.minHits = minHits;
        reader = new FileReader(directory);
    }
    
    public List<String> getOrderedList(int fileNumber) throws java.io.FileNotFoundException
    {
        List<String> list = reader.generatePhrases(numWords,fileNumber);
        mergeSort(list, 0, list.size()-1);
        return list;
    }
    
    //recursive helper method mergeSort for getting ordered list
    private void mergeSort(List<String> list, int first, int last)
    {
        if (first < last)
        {
            int middle = (first+last)/2;
            mergeSort(list, first, middle);
            mergeSort(list, middle+1, last);
            merge(list, first, middle, middle+1, last);
        }
    }
    
    //helper method to actually merge the smaller arrays for getting ordered list
    private void merge(List<String> list, int leftFirst, int leftLast, int rightFirst, int rightLast)
    {
        if (list.get(rightFirst).compareTo(list.get(leftLast)) > 0)
            return;
        while (leftFirst <= leftLast && rightFirst <= rightLast)
        {
            if (list.get(rightFirst).compareTo(list.get(leftFirst)) > 0)
                leftFirst++;
            else
            {
                for (int i = rightFirst; i>leftFirst; i--)
                {
                    String temp = list.get(i-1);
                    list.set(i-1, list.get(i));
                    list.set(i, temp);
                }
                leftFirst++;
                leftLast++;
                rightFirst++;
            }
        }
    }
    
    public List<String> createList() throws FileNotFoundException
    {
        int numFiles = reader.getNumberOfFiles();
        
        //for keeping track of progress
        int totalComparisons = (numFiles*(numFiles-1))/2;
        int comparisonCount = 0;
        
        List<String> hitList = new ArrayList<String>();
        List<Integer> rankOrder = new ArrayList<Integer>();
        
        //List<List<String>> allOrderedLists = new ArrayList<List<String>>();
        
        List<List<String>> allPhraseLists = new ArrayList<List<String>>();
        List<HashSet<String>> allHashSets = new ArrayList<HashSet<String>>();
        
        System.out.println("\n" + "Initiating hit list set up for " + numWords + " length phrases...");
        //initially collects all ordered lists from file
        for (int i=0; i<numFiles; i++)
        {
            //List<String> orderedList = getOrderedList(i);
            //allOrderedLists.add(orderedList);
            
            List<String> list = reader.generatePhrases(4,i);
            HashSet<String> hashSet = new HashSet<String>(list);
        
            allPhraseLists.add(list);
            allHashSets.add(hashSet);
            
            if (i % (numFiles/10) == 0)
            {
                System.out.println(i + "/" + numFiles + " lists set up");
            }
        }
        
        
        System.out.println("\n" + "Commencing file comparisons...");
        for (int i=0; i<numFiles; i++)
        {
            for (int j=i+1; j<numFiles; j++)
            {
                /*
                HitCounter counter = new HitCounter(reader,allOrderedLists.get(i),allOrderedLists.get(j),numWords);
                int hits = counter.compareLists();
                comparisonCount++;
                */
               
                HashSet<String> combinedSet = new HashSet<String>(allPhraseLists.get(i));
                combinedSet.addAll(allPhraseLists.get(j));
                
                int hits = allHashSets.get(i).size() + allHashSets.get(j).size() - combinedSet.size(); 
                comparisonCount++;
                
                if (hits >= minHits)
                {
                    int insertIndex = findInsertIndex(rankOrder, hits);
                    rankOrder.add(insertIndex, hits);
                    String string = "[" + reader.getFileName(i) + " , " + reader.getFileName(j) + "]   ->   " + hits;
                    hitList.add(insertIndex, string);
                }
        
                if (comparisonCount % 50000 == 0)
                    System.out.println(comparisonCount + "/" + totalComparisons + " comparisons completed–");
            }
        }
        
        return hitList;
    }
    

    private int findInsertIndex(List<Integer> rankOrder, int hits)
    {
        if (rankOrder.size() == 0)
            return 0;
        int left = 0, right = rankOrder.size();
        
        while (right-left > 1)
        {
            int middle = (left+right)/2;
            int value = rankOrder.get(middle).intValue();
            if (hits == value)
                return middle;
            else if (hits < value)
                left = middle;
            else
                right = middle;
        }
        if (hits >= rankOrder.get(left).intValue())
            return left;
        else
        {
            return right;
        }
    }
}
