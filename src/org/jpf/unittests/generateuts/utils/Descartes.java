/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年11月20日 上午11:17:01 
* 类说明 
*/ 

package org.jpf.unittests.generateuts.utils;

import java.util.ArrayList;

import org.jpf.unittests.generateuts.GenerateConst;

/**
 * 
 */
public class Descartes {

    public  void run(ArrayList<ArrayList<String>> dimvalue, ArrayList<String> result, int layer, String curstring)
    {
        //大于一个集合时：
        if (result.size()>GenerateConst.Max_CaseCount_PerMethod)
        {
            return;
        }
        if (layer < dimvalue.size() - 1)
        {
            //大于一个集合时，第一个集合为空
            if (dimvalue.get(layer).size() == 0)
                run(dimvalue, result, layer + 1, curstring);
            else
            {
                for (int i = 0; i < dimvalue.get(layer).size(); i++)
                {
                    StringBuilder s1 = new StringBuilder();
                    s1.append(curstring);
                    s1.append(dimvalue.get(layer).get(i));
                    run(dimvalue, result, layer + 1, s1.toString());
                }
            }
        }
        //只有一个集合时：
        else if (layer == dimvalue.size() - 1)
        {
            //只有一个集合，且集合中没有元素
            if (dimvalue.get(layer).size() == 0)
                result.add(curstring);
            //只有一个集合，且集合中有元素时：其笛卡尔积就是这个集合元素本身
            else
            {
                for (int i = 0; i < dimvalue.get(layer).size(); i++)
                {
                    result.add(curstring + dimvalue.get(layer).get(i));
                }
            }
        }
    }

}
