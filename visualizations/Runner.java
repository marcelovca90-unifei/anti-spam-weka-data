import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Runner
{
    private static final String[][] ENTRIES = {
            { "/Users/marcelocysneiros/Downloads/UNIFEI_2017.trace", "/Users/marcelocysneiros/Downloads/tSNE_UNIFEI_2017/" },
            { "/Users/marcelocysneiros/Downloads/UNIFEI_2018.trace", "/Users/marcelocysneiros/Downloads/tSNE_UNIFEI_2018/" }
    };

    public static void main(String[] args) throws Exception
    {
        for (String[] entry : ENTRIES)
        {
            String source = entry[0], destination = entry[1];
            BufferedReader br = new BufferedReader(new FileReader(new File(source)));
            String line, folder = null, hamFilename = null, spamFilename = null, dupedFilename = null, x, y;
            List<Point> hamData = null, spamData = null, dupedData = null;
            int hamIndex = 0, spamIndex = 0;
            boolean shouldProcessFolder = false;
            while ((line = br.readLine()) != null)
            {
                if (line.contains("TsneAnalyser processing folder"))
                {
                    if (shouldProcessFolder && hamData != null && spamData != null)
                    {
                        log("Processing folder " + folder + ".");
                        dupedData = detectDupesMultiThread(hamData, spamData);
                        log(dupedData.size() + " duplicates detected.");
                        log(hamData.stream().filter(ham -> ham.plot == false).count() + " hams will not be plotted.");
                        log(spamData.stream().filter(spam -> spam.plot == false).count() + " spams will not be plotted.");
                        log(dupedData.stream().filter(dupe -> dupe.plot == true).count() + " duplicates will be plotted.");
                        persist(hamData, hamFilename);
                        persist(spamData, spamFilename);
                        persist(dupedData, dupedFilename);
                        log("Files persisted successfully.");
                    }
                    folder = line
                        .substring(line.lastIndexOf(" ") + 1)
                        .replace("/home/isaac/tsne/04_vectors/", destination);
                    shouldProcessFolder = folder.endsWith("/8") || folder.endsWith("/1024");
                    hamFilename = folder + File.separator + "ham.data";
                    spamFilename = folder + File.separator + "spam.data";
                    dupedFilename = folder + File.separator + "duped.data";
                    hamData = new ArrayList<>();
                    spamData = new ArrayList<>();
                    dupedData = new ArrayList<>();
                    hamIndex = 0;
                    spamIndex = 0;
                }
                else if (shouldProcessFolder && line.contains("HAM"))
                {
                    line = line.substring(line.indexOf("@") + 1).trim();
                    x = line.substring(1, line.indexOf(","));
                    y = line.substring(line.indexOf(" ") + 1).replace(")", "");
                    hamData.add(new Point(x, y, hamIndex++));
                }
                else if (shouldProcessFolder && line.contains("SPAM"))
                {
                    line = line.substring(line.indexOf("@") + 1).trim();
                    x = line.substring(1, line.indexOf(","));
                    y = line.substring(line.indexOf(" ") + 1).replace(")", "");
                    spamData.add(new Point(x, y, spamIndex++));
                }
            }
            br.close();
        }
    }

    private static List<Point> detectDupesMultiThread(List<Point> hamData, List<Point> spamData)
    {
        List<Point> dupedSamples = Collections.synchronizedList(new ArrayList<>());

        int step = hamData.size() / 100;
        hamData.stream().forEach(ham ->
        {
            if ((ham.i % step) == 0)
                log(ham.i / step + "% ...");

            spamData.parallelStream().forEach(spam ->
            {
                if (ham.equals(spam))
                {
                    // log("ham " + ham + " is equal to spam " + spam);
                    dupedSamples.add(new Point(ham));
                    ham.plot = false;
                    spam.plot = false;
                }
            });
        });

        return dupedSamples;
    }

    private static void persist(List<Point> data, String filename) throws IOException
    {
        File file = new File(filename);
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(String.format("#%s\n", filename));
        for (Point point : data)
            if (point.plot)
                bw.write(point.toGnuplotString());
        bw.close();
        fw.close();
    }

    private static void log(String s)
    {
        System.out.println(LocalDateTime.now() + " " + s);
    }
}
