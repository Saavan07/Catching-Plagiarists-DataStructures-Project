import java.util.*;
/**
 * This HitCounter class represents the number of hits of n-contiguous phrases in two files
 *
 * @author  (your name)
 * @version (todays date)
 */
public class HitCounter
{
    private List<String> list1, list2;
    private FileReader reader;
    private int numWords;
    
    public HitCounter(FileReader reader, List<String> list1, List<String> list2, int numWords)
    {
        this.reader = reader;
        this.list1 = list1;
        this.list2 = list2;
        this.numWords = numWords;
    } 
    
    //main method of this class which counts out all the hits
    public int compareLists() throws java.io.FileNotFoundException
    {
        int hits = 0, index1 = 0, index2 = 0;
        
        while (index1 < list1.size() || index2 < list2.size())
        {
            int value = list1.get(index1).compareTo(list2.get(index2));
            if (value == 0) //case when they are the same
            {
                hits++;
                if (index1 < list1.size()-1) //cases for incrementing
                    index1++;
                else if (index2 < list2.size()-1)
                    index2++;
                else
                    break;
            }
            else if (value < 0) //case when left less than right
            {
                if (index1 < list1.size()-1)
                    index1++;
                else
                    break;
            }
            else if (value > 0) //case when right less than left
            {
                if (index2 < list2.size()-1)
                    index2++;
                else
                    break;
            }
        }
        
        return hits;
    }
}
