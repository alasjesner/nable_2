
package nlg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * @author chandrani96
 */
public class json_object {
    public static void main(String[] args)throws Exception {
        
        String str="{\n" +
"  \"responses\": [\n" +
"    {\n" +
"      \"labelAnnotations\": [\n" +
"        {\n" +
"          \"mid\": \"/m/0bt9lr\",\n" +
"          \"description\": \"dog\",\n" +
"          \"score\": 0.8920206,\n" +
"          \"topicality\": 0.8920206\n" +
"        },\n" +
"        {\n" +
"          \"mid\": \"/m/01z5f\",\n" +
"          \"description\": \"dog like mammal\",\n" +
"          \"score\": 0.8869412,\n" +
"          \"topicality\": 0.8869412\n" +
"        },\n" +
"        {\n" +
"          \"mid\": \"/m/08t9c_\",\n" +
"          \"description\": \"grass\",\n" +
"          \"score\": 0.8723395,\n" +
"          \"topicality\": 0.8723395\n" +
"        },\n" +
"        {\n" +
"          \"mid\": \"/m/0kpmf\",\n" +
"          \"description\": \"dog breed\",\n" +
"          \"score\": 0.86987364,\n" +
"          \"topicality\": 0.86987364\n" +
"        },\n" +
"        {\n" +
"          \"mid\": \"/m/01c7cq\",\n" +
"          \"description\": \"grassland\",\n" +
"          \"score\": 0.7248698,\n" +
"          \"topicality\": 0.7248698\n" +
"        },\n" +
"        {\n" +
"          \"mid\": \"/m/025st_8\",\n" +
"          \"description\": \"meadow\",\n" +
"          \"score\": 0.6538264,\n" +
"          \"topicality\": 0.6538264\n" +
"        },\n" +
"        {\n" +
"          \"mid\": \"/m/05mqq3\",\n" +
"          \"description\": \"snout\",\n" +
"          \"score\": 0.6483446,\n" +
"          \"topicality\": 0.6483446\n" +
"        },\n" +
"        {\n" +
"          \"mid\": \"/m/02wbgd\",\n" +
"          \"description\": \"english cocker spaniel\",\n" +
"          \"score\": 0.6269687,\n" +
"          \"topicality\": 0.6269687\n" +
"        },\n" +
"        {\n" +
"          \"mid\": \"/m/02kysw\",\n" +
"          \"description\": \"spaniel\",\n" +
"          \"score\": 0.62650734,\n" +
"          \"topicality\": 0.62650734\n" +
"        },\n" +
"        {\n" +
"          \"mid\": \"/m/01gd91\",\n" +
"          \"description\": \"pasture\",\n" +
"          \"score\": 0.62508994,\n" +
"          \"topicality\": 0.62508994\n" +
"        }\n" +
"      ]\n" +
"    }\n" +
"  ]\n" +
"}";
        String s1="{\n" +
"  \"responses\": [\n" +
"    {\n" +
"      \"labelAnnotations\": [";
        int i=str.indexOf(s1);
        File file = new File("output.txt");
        System.out.println(file.getAbsolutePath());
            FileWriter fw = new FileWriter(file);
        i=i+1+s1.length();
        for(int x=0; x<5; x++)
        {
            i=str.indexOf("{",i);
            String s2="\"description\": \"";
            i=str.indexOf(s2, i)+s2.length();
            int j=str.indexOf("\"", i);

            fw.append(str.substring(i,j)+"\n");
            

            System.out.println(str.substring(i,j));
            i=j;
            
            
        }
        fw.close();
        
        
        
    }
    
}
