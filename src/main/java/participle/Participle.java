package participle;

import cosine.Cosine;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;

public class Participle {

    public static void main(String[] args) {
        Participle participle = new Participle();
        Cosine cosine = new Cosine();
        String regExp = "<[^>]*>";
        String s = "2018秋装新款女装毛衣打底裙韩版百搭修身长款钉珠针织连衣裙";
        String result = s.replaceAll(regExp, " ");
        LinkedHashMap<String,Integer> cosion = participle.annotators(result);
        cosine.setCosineMap(cosion);
        ArrayList<String> s1 = new ArrayList<String>();

        s1.add("棉衣女2018冬季新款舒适外套修身气质羽绒服韩版显瘦时尚棉大衣");
        s1.add("毛呢外套中长款冬季2018年时尚潮流舒适简约个性纯色长袖大衣");
        s1.add("羽绒服女2018新款修身中长款韩版时尚连帽冬大衣拉链纯色直筒外套");
        s1.add("蕾丝连衣裙秋冬女装冬季打底裙长款系带冬裙加绒2018新款圆领套头");
        s1.add("女装拼接棉衣中长款棉服拉链外套2018年冬季长袖时尚潮流连帽外衣");
        s1.add("针织衫打底女秋冬长袖2018新款修身韩版女装套头圆领毛衣内搭外穿");
        s1.add("女装2018秋冬新款呢子大衣女超长韩版流行过膝加厚翻领毛呢外套");
        s1.add("女装2018秋冬中国风时尚修身束腰显廋大摆气质立领舒适棉衣中长款");
        s1.add("2018秋冬新款韩版百搭西装领毛呢外套女短款春秋纯色修身甜美上衣");
        s1.add("秋冬新款连衣裙欧美气质时尚大码收腰显瘦过膝宽松针织长款毛衣裙");
        s1.add("女装H型毛呢连衣裙纯色优雅韩版长袖中长款甜美套头2018年冬季款");
        s1.add("女装外套时尚温暖防寒中长款长袖大衣纯色系带单排扣2018年冬季新");
        s1.add("女装棉服2018新款棉衣时尚舒适气质保暖2018年冬季无帽中长大衣花");
        s1.add("女装冬花色棉衣中长款棉服拉链外套2018年时尚常规保暖舒适大衣");
        s1.add("休闲棉服女中长款显瘦连帽修身大");
        s1.add("电脑椅家用休闲椅职员老板椅子真皮椅办公电竞牛皮按摩可躺大班椅");

        int i=0;
        for (String indexStr:s1){
            String result1 = indexStr.replaceAll(regExp," ");
            LinkedHashMap<String,Integer> cosinTag = participle.annotators(result1);
            cosine.setCosineTargetMap(cosinTag);
            double similars = cosine.similar();
            System.out.println("第"+(i++)+"_相似度："+similars+"%");
            System.out.println("<---------------------------------------->");
            System.out.println();
        }
    }

    public LinkedHashMap<String,Integer> annotators(String text) {
        StanfordCoreNLP corenlp = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
        Annotation document = new Annotation(text);
        corenlp.annotate(document);
        LinkedHashMap<String, Integer> modelMap = parserOutput(document);
        modelMap = sort(modelMap);
        return modelMap;
    }

    public LinkedHashMap<String, Integer> parserOutput(Annotation document) {
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        LinkedHashMap<String, Integer> modelMap = new LinkedHashMap<String, Integer>();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (pos.equals("VV")||pos.equals("VE")||pos.equals("VC")||pos.equals("VA")||pos.equals("LC")||pos.equals("NR") || pos.equals("NT") || pos.equals("NN")) {
                    try {
                        Integer countWord = modelMap.get(word) + 1;
                        modelMap.put(word, countWord);
                    } catch (NullPointerException e) {
                        modelMap.put(word, 0);
                    }
                }
            }
        }
        return modelMap;
    }

    private LinkedHashMap<String,Integer> sort(LinkedHashMap<String,Integer> linkedHashMap){
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(linkedHashMap.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //升序排序
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        LinkedHashMap<String,Integer> returnLinkedHashMap = new LinkedHashMap<String, Integer>();
        int i=0;
        for (Map.Entry<String,Integer> entry:list){
            i++;
            returnLinkedHashMap.put(entry.getKey(),entry.getValue());
            if(i>=20){
                break;
            }
        }
        return returnLinkedHashMap;
    }
}
