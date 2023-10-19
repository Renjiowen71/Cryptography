package Utility;

import java.util.Map;

public class IndexOfCoincedence {

    public static double calculateIOC(Map<Character, Integer> letterFreq, int totalChars) {

        double sum = 0.0;
        for (int frequency : letterFreq.values()) {
            sum += frequency * (frequency - 1);
        }
        return sum / (totalChars * (totalChars - 1));
    }

    public static String cleanText(String text){
        return text.replaceAll("[^a-zA-Z0-9_-]", "");
    }

    public static StringBuilder[] splitIntoGroups(String text, int totalGroup){
        String cleanText = cleanText(text);
        StringBuilder[] stringGroups = new StringBuilder[totalGroup];
        for (int i =0;i<cleanText.length();i++){
            if(stringGroups[i%totalGroup]==null){
                stringGroups[i%totalGroup] = new StringBuilder().append(cleanText.charAt(i));
            } else {
                stringGroups[i%totalGroup].append(cleanText.charAt(i));
            }
        }
        return  stringGroups;
    }
    public static double calculateIOC(String text){
        int totalChar = LetterFreq.countTotalChar(text);
        Map<Character,Integer> letterFreq = LetterFreq.countLetterFreq(text);
        double iocValue = IndexOfCoincedence.calculateIOC(letterFreq, totalChar);
        System.out.println("Index of Coincidence: " + iocValue);
        //LetterFreq.showLetterFreq(letterFreq,totalChar);
        System.out.println("============================");
        return iocValue;
    }
    public static void tryCalculateIOC(){
        String text = "L llws, gpysoj, qvzf tubimofbwv zvdx tg ofc jc wlpjf xlzm, lj yphbztu lw yfufvihhh, lor cw zvh fpth uixoqkpnshky oui xbry, ry hkij bfy skwqk xbry, nk gkewm dlfbs ryctsfmkg rrnf oarob dfwf hi ukthro pil Zyzdro icgv, zc umof cok zvh wepfg fl kdv, lor nf uiwptws nyk ahrlds iw zmueyom, cw tsfidtolp lcu cpbfm, zl bhgptguie oosyf. On rtm ueef, hbrz wv asbh qv gfh kzjba ku huc ep ri. Know md uvy ikgrpgf cz Yog Peufgnp'y Urzpsbgvth, hzpsm grt ci xsfa. Nygh lw eis qzrz rj Abffzgahre bbx kns qeejch. Kns Evtuwmy Kasmcf ohu zvh Jcfbwy Xssymmww, cobnio ucavzvhv to hbvof fefts uej wq xsfwl eksg, atmz xvlsqh ep hbv jsdxs uvyzx bdxtws mfoz, dmojba vgqk seisl coyh kzpr wfsfdhpt hi kns xxxpgn fl hkits gnikbjxs. Fjye zvryri zuims wvldhm fl Sxvzqs uej adrj pzx rtr iexpim Jzowid iopv looppo cl dgm iewm whku hki rswj fl hki Rfgnrvc dro bzf kns rhtpim rvddvluim fl Bddt sifv, cs vllmz hfz toer pf zroz. Zi diofc mc rr ep hbv kbg, ap tvucr tlksu wh Wxoqgp, xs mygzo jthvn ft hki dfom rtr rgpbbm, nk gkewm tcxnh zmei ulfcwqk npbzzjsqgp bbx xxczmyh gnikbjxs jb nyk olv, hf gbrrz giqfbx faf Lwwbbx, nnowigff nyk qrwe nos sk, kh wsbzf woukx zo hbv hsdgsfg, qv yvdpw gwayz cq xsf zuejwqk rscoejg, zi diofc lwjle jb nyk tliweg uej wq xsf gniksww, hf gbrrz imrih ce zvh ltmzm; nk gkewm bymkf vycsshukf, dro fjye ot, zltdv C uu brx qpf u duahre csfzkjh, xsjg Cjroqh zs o frxuh tlsh iw oh zicf gospijeefr uej gwecwwhx, zvhr zvf Ydvwui mfmiej hki dfom, rxahh lor algfgio cm nyk Pumejgb Wrshx, hpifu iouvj pb nyk gwvfhufv, abwmw, jb Afj'g jsze hcdk, hki Yfk Qfxzg, atuv ucr www apkyi gbg qthvn, jzssw qpfny zc wlp ssmtas dro uvy cophvluwie ut wlp pzx.";
        System.out.println(text);
        int predictLength = 0;
        double minimumDistance = Double.MAX_VALUE, distance;
        for(int totalGroup = 1; totalGroup<11;totalGroup++) {
            System.out.println("Total Group = "+totalGroup);
            double totalIOC=0;
            StringBuilder[] stringBuilders = splitIntoGroups(text, totalGroup);
            for (StringBuilder stringBuilder : stringBuilders) {
                totalIOC += calculateIOC(stringBuilder.toString());
            }
            distance = 0.067 - totalIOC/totalGroup;
            if (minimumDistance>distance){
                minimumDistance = distance;
                predictLength = totalGroup;
            }
        }
        System.out.println("Predicted Length = " + predictLength );
//        text = "around the world around the world\n" +
//                "around the world around the world\n" +
//                "around the world around the world\n" +
//                "around the world around the world";
//        letterFreq = LetterFreq.countLetterFreq(text);
//        totalChar = LetterFreq.countTotalChar(text);
//        iocValue = IndexOfCoincedence.calculateIOC(letterFreq, totalChar);
//        System.out.println("Index of Coincidence: " + iocValue);
//        LetterFreq.showLetterFreq(letterFreq,totalChar);
    }
}
