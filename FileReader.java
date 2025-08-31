import java.util.*;
import java.io.*;
/**
 * This FileReader class represents a supporting class to read the file into arrays of strings
 *
 * @author  (your name)
 * @version (todays date)
 */
public class FileReader
{
    private String directory;
    private List<String> files;
    private int numberOfFiles;
    
    public FileReader(String directory)
    {
        this.directory = directory;  
        File dir = new File(directory);
        String[] temp = dir.list();
        files = new ArrayList<String>();
        for (int i=0; i<temp.length; i++)
        {
            if (temp[i].endsWith(".txt"))
                files.add(temp[i]);
        }
        
        //sorts the names of the files
        numberOfFiles = files.size();
        for (int i=0; i<numberOfFiles-1; i++)
            for (int j=0; j<numberOfFiles-i-1; j++)
                if (files.get(j).compareTo(files.get(j+1)) > 0)
                {
                    String placeholder = files.get(j);
                    files.set(j, files.get(j+1));
                    files.set(j+1, placeholder);
                }
    }
    
    
    //getters
    public String getFileName(int fileNumber)
    {
        return files.get(fileNumber);
    }
    
    public int getNumberOfFiles()
    {
        return numberOfFiles;
    }
    
    public String generatePathname(int fileNumber)
    {
        return "./" + directory + "/" + files.get(fileNumber);
    }

    //returns file number using a Binary Search to find the file with the name
    public int getFileNumber(String fileName)
    {
        int left = 0;
        int right = numberOfFiles-1;
        while (right-left > 1)
        {
            int middle = (right+left)/2;
            if (fileName.compareTo(files.get(middle)) < 0)
                right = middle;
            else if (fileName.compareTo(files.get(middle)) > 0)
                left = middle;
            else if (fileName.compareTo(files.get(middle)) == 0)
                return middle;
        }
        if (fileName.compareTo(files.get(left)) == 0)
            return left;
        else if (fileName.compareTo(files.get(right)) == 0)
            return right;
        else
            return -1;
    }
    
    public List<String> generatePhrases(int numWords, int fileNumber) throws FileNotFoundException
    {
        String pathname = generatePathname(fileNumber);
        List<String> phrases = new ArrayList<String>();
        
        for (int i=0; i<numWords; i++)
        {
            Scanner file = new Scanner(new File(pathname));
            for (int j=0; j<i; j++)
                file.next();
            while (file.hasNext())
            {
                String phrase = "";
                for (int k=0; k<numWords; k++)
                {
                    if (file.hasNext())
                        phrase += file.next().replaceAll("[^A-z]","").toLowerCase();
                    else
                        phrase = null;
                }
                if (phrase != null)
                {
                    phrases.add(phrase);
                }
            }
        }
        
        return phrases;
    }
}
