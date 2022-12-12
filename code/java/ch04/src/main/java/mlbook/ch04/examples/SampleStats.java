package mlbook.ch04.examples;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class SampleStats {

    // Example 1 - Loading the file and converting to an Integer collection.
    public List<Integer> loadFile(String filename) throws Exception {
        List<Integer> numList = new ArrayList<Integer>();
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        while ((s = br.readLine()) != null) {
            numList.add(Integer.parseInt(s));
        }
        return numList;
    }


    public List<Double> loadFileToDouble(String filename) throws Exception {
        List<Double> numList = new ArrayList<Double>();
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        while ((s = br.readLine()) != null) {
            numList.add(Double.parseDouble(s))