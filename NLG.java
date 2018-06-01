package nlg;

/**
 *
 * @author chandrani96
 */

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;
public class nlg {

    public static void main(String[] args)throws Exception {
        //String DB_FILENAME = "/home/chandrani96/NetBeansProjects/NLG/src/lexAccess2016lite/data/HSqlDb/lexAccess2016.data";
        //Lexicon lexicon = Lexicon.NIHDBLexicon(DB_FILENAME);
    	
    	//number of elements to consider
        int no_of_elements_seen=6;
        
        
        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory nlgFactory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);
        
        
        File file =new File("/home/kira/work/output.txt");
        Scanner sc = new Scanner(file);
        String entity[]=new String[no_of_elements_seen];
        for (int i=0; i<no_of_elements_seen && sc.hasNextLine(); i++)
        {
            entity[i]=sc.nextLine();   
            
        }
        
        /*
      //  String new_entity[]=new String[no_of_elements_seen];
        //int pos=-1;
       
        for(int i=0; i<no_of_elements_seen; i++)
       {
           int r_pos=-1;
           String rep_elements[]=new String[no_of_elements_seen];
           String repeat="";
       }
        */
        
        
        
        
        
       /*    for(int j=i+1; j<i; j++)
           {
               if(entity[j].indexOf(entity[i])!=-1 && repeat.compareTo("")==0) //hence e(i) present in e(j)
               {
                   repeat=entity[i];
                   rep_elements[++r_pos]=entity[j];
               }
               else if(repeat.compareTo("")!=0 && entity[j].indexOf(repeat)!=-1)//repeat element present
               {
                   rep_elements[++r_pos]=entity[j];
               }
           }
           
           
           if(repeat.compareTo("")!=-1)
           {
               for(int j=0; j<=r_pos; j++)
               {
                if(rep_elements[j].length()<rep_elements[j+1].length())
                {
                   rep_elements[j]=rep_elements[j+1];
                }
                else
                    rep_elements[j+1]=rep_elements[j];
               }
               new_entity[++pos]=rep_elements[1];
                       
           }
           else
               new_entity[++pos]=entity[i];
       }
       
       for(int i=0;i<5;i++)
           System.out.println(new_entity[i]);
        
        */
        
    
    
    
    CoordinatedPhraseElement obj = nlgFactory.createCoordinatedPhrase(); 
    String nouns[]=new String[no_of_elements_seen];
    int no_of_nouns=0;
    String verbs[]=new String[no_of_elements_seen];
    int no_of_verbs=0;
    
    for (int i=0; i<no_of_elements_seen; i++)
    {
            //test for verbs
        SPhraseSpec test_for_verbs = nlgFactory.createClause();
        test_for_verbs.setSubject("I");
        test_for_verbs.setVerb(entity[i]);
        test_for_verbs.setFeature(Feature.PERFECT, true);
        String test_verbs = realiser.realiseSentence(test_for_verbs);
        String t1=entity[i]+"ed";
        String t2=test_verbs.substring(7,test_verbs.length()-1);
        //System.out.println(test_verbs); //sentence
        //System.out.println(t1); //string i made
        //System.out.println(t2);   //string created by api
        if(t1.compareTo(t2)==0)
            nouns[no_of_nouns++]=entity[i];
        else
        {
            StringTokenizer st1 = new StringTokenizer(t1," ");
            StringTokenizer st2 = new StringTokenizer(t2," ");
        ///    int no_of_tokens=st1.countTokens();	
            int c=0;
            while (st1.hasMoreTokens()) {  
            if(st1.nextToken().compareTo(st2.nextToken())!=0);
            {
                c++;
            }
            }  
            if(c==1)
                verbs[no_of_verbs++]=entity[i];
            else
               nouns[no_of_nouns++]=entity[i]; 
        }
         
    }
    
   //sentence 1 is sentence with nouns
    SPhraseSpec sentence1 = nlgFactory.createClause();
    sentence1.setSubject("I");
    sentence1.setVerb("am  seeing");
    //for nouns, in for loop, add obj.addCoordinate(entity[i]);
    for(int i=0; i<no_of_nouns; i++)
    {
        obj.addCoordinate("a "+ nouns[i]);
    }

    sentence1.setObject(obj);
    sentence1.setFeature(Feature.CONJUNCTION, "and");
    sentence1.setFeature(Feature.TENSE, Tense.PRESENT);
    String output1 = realiser.realiseSentence(sentence1); // Realiser created earlier.
    System.out.println(output1);
    
    
   
    //sentence 2 is sentence with verbs
    /*SPhraseSpec sentence2 = nlgFactory.createClause();
    sentence1.setSubject("I");
    sentence1.setVerb("can also see");*/
    SPhraseSpec sentence2 = nlgFactory.createClause("I", "can also", "see");
    SPhraseSpec sentpart2 = nlgFactory.createClause();
    sentpart2.setFeature(Feature.COMPLEMENTISER, "that");
    SPhraseSpec sentpart3 = nlgFactory.createClause();
    sentpart3.setSubject("someone");
    sentpart3.setVerb(verbs[0]);
    sentpart3.setFeature(Feature.PROGRESSIVE, true);
    sentpart2.addComplement(sentpart3);
    
    for(int i=1; i<no_of_verbs-1; i++)
    {
        SPhraseSpec sentpart = nlgFactory.createClause();
    sentpart.setFeature(Feature.COMPLEMENTISER, ",");
    sentpart.setSubject("someone");
    sentpart.setVerb(verbs[i]);
    sentpart.setFeature(Feature.PROGRESSIVE, true);
    sentpart2.addComplement(sentpart);
    }
    
    
    SPhraseSpec sentpart = nlgFactory.createClause();
    sentpart.setFeature(Feature.COMPLEMENTISER, "and");
    sentpart.setSubject("someone");
    sentpart.setVerb(verbs[no_of_verbs-1]);
    sentpart.setFeature(Feature.PROGRESSIVE, true);
    sentpart2.addComplement(sentpart);
    
    
    
    sentpart2.setFeature(Feature.PROGRESSIVE, true);
    sentence2.addComplement(sentpart2);     
    String output2 = realiser.realiseSentence(sentence2);  //Realiser created earlier
    System.out.println(output2);
    
    sc.close();
    
}
}
