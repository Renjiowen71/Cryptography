import Analyzer.Interface.AnalyzerAlg;

public class TryAnalyzer {

    public static void run(){
        try{
            AnalyzerAlg analyzerAlg = (AnalyzerAlg) ClassUtility.getObj("Analyzer");
            analyzerAlg.analyze();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
