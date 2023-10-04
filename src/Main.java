import Algorithm.Interface.*;
import Analyzer.*;
import jdk.jshell.execution.Util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Choose options:");
        System.out.println("1. Algorithm");
        System.out.println("else Analyzer");
        String choise = Utility.SCANNER.nextLine();
        if(choise.equals("1") )TryAlgorithm.run();
        else TryAnalyzer.run();
    }
}