import java.io.*;
import java.util.*;
/**
 * This Main class represents . . .
 *
 * @author  (your name)
 * @version (todays date)
 */
public class PlagiaristCatcher
{
    public static void main(String[] args) throws FileNotFoundException
    {
        File dir = new File(".");
        ArrayList<File> directories = new ArrayList<File>();
        for (File folder : dir.listFiles())
            if (folder.isDirectory())
                directories.add(folder);
        
        Scanner input = new Scanner(System.in);        
        
        //user interface
        System.out.println("-----------------------------------------------------------------PLAGIARIST CATCHER------------------------------------------------------------------");
        System.out.println("\n" + "Welcome! This is the Plagiarist Catcher!! Enter a set of files, the phrase length, and the minimum suspicious hit count and this program will list"); 
        System.out.println("out every file comparison that must be reviewed for plagiarism!");
        System.out.println("\n" + "                                             **FULL SCREEN WINDOW & UNLIMITED BUFFERING RECOMMENDED**" + "\n");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\n" + "0  ->  Small Set of Documents");
        System.out.println("1  ->  Medium Set of Documents");
        System.out.println("2  ->  Large Set of Documents");
        System.out.print("Enter the number corresponding to the set of documents you would like to compare: ");
        int set = input.nextInt();
        
        boolean validAnswer = false;
        while (!validAnswer)
        {
            switch (set)
            {
                case  0:
                case  1:
                case  2: validAnswer = true;
                         break;
                default: System.out.print("Number entered was not one of the given options. Please try again: ");
                         set = input.nextInt();
            }
        }
        
        System.out.print("\n" + "Next, enter the phrase length (must be >= 1): ");
        int phraseLength = input.nextInt();
        while (phraseLength < 1)
        {
            System.out.print("Number entered was not >= 1. Please try again: ");
            phraseLength = input.nextInt();
        }
        
        System.out.print("\n" + "Lastly, enter the minimum number of hits to list a case (must be >= 1): ");
        int minHits = input.nextInt();
        while (minHits < 1)
        {
            System.out.print("Number entered was not >= 1. Please try again: ");
            minHits = input.nextInt();
        }
        
        HitList hitList = new HitList(directories.get(set).getName(),phraseLength,minHits);
        List<String> list = hitList.createList();
        
        System.out.println("\n" + "Completed hit list (minimum of " + minHits + " hits of " + phraseLength + " word contiguous phrases):");
    
        for (int i=0; i<list.size(); i++)
            System.out.println(list.get(i));
    }
}
