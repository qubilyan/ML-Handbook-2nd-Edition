package mlbook.ch04.examples;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinearRegressionBuilder {
    private static String path = "/Users/jasebell/bookwork/mlbook2ndedition/data/ch04/ch4slr.csv";



    public LinearRegressionBuilder() {
        List<String> lines = loadData(path);
        SimpleRegression sr = getLinearRegressionModel(lines);
        System.out.println(runPredictions(sr, 10));
    }

    private SimpleRegression getLinearRegressionModel(