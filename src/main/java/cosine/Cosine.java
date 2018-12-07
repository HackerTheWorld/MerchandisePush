package cosine;

/*
* 计算余弦相似度
* */

import java.util.LinkedHashMap;

public class Cosine {
    private LinkedHashMap<String,Integer> cosineMap = new LinkedHashMap<String, Integer>();
    private LinkedHashMap<String,Integer> cosineTargetMap = new LinkedHashMap<String, Integer>();

    public LinkedHashMap<String, Integer> getCosineMap() {
        return cosineMap;
    }

    public void setCosineMap(LinkedHashMap<String, Integer> cosineMap) {
        this.cosineMap = cosineMap;
    }

    public LinkedHashMap<String, Integer> getCosineTargetMap() {
        return cosineTargetMap;
    }

    public void setCosineTargetMap(LinkedHashMap<String, Integer> cosineTargetMap) {
        this.cosineTargetMap = cosineTargetMap;
    }

    public double similar(){
        int denominator = 0;
        int molecule1 = 0;
        int molecule2 = 0;
        for (String key:cosineTargetMap.keySet()){
            molecule1 += (cosineTargetMap.get(key)+1)*(cosineTargetMap.get(key)+1);
            if (cosineMap.containsKey(key)){
                denominator += (cosineTargetMap.get(key)+1)*(cosineMap.get(key)+1);
            }
        }

        for (String key:cosineMap.keySet()){
            molecule2 += (cosineMap.get(key)+1)*(cosineMap.get(key)+1);
        }
        double similar = denominator/(Math.sqrt(molecule1+0.0)+Math.sqrt(molecule2+0.0));
        return similar;
    }

}
