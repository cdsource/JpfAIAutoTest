/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2018年1月23日 上午11:18:18 
* 类说明 
*/ 

package org.jpf.aut.gts.gtm.genbynlps;

/**
 * 通过插入删除或替换使得一个字符串变为另一个字符串的最小操作次数。

 */
public class nlpsUtil {

     
        public static void main(String[] args)
        {
        	System.out.println("sailn,failing");
            System.out.println(ed("sailn","failing"));
            
            System.out.println("recoginze,recognize");
            System.out.println(ed("recoginze", "recognize"));
            
            System.out.println("hack,hankcs");
            System.out.println(ed("hack", "hankcs"));
            
            System.out.println("sailn,failing");
            System.out.println(ed2("sailn","failing"));
            
            System.out.println("recoginze,recog_nize");
            System.out.println(ed2("recoginze", "recog_nize"));

            System.out.println("hack,hankcs");
            System.out.println(ed2("hack", "hankcs"));
            
            System.out.println("addmonth,add_month");
            System.out.println(ed2("addmonth", "add_month"));
            
            System.out.println("monthadd,add_month");
            System.out.println(ed2("monthadd", "add_month"));
            
            System.out.println("sailn,failing");
            System.out.println(ed3("sailn","failing"));
            
            System.out.println("recoginze,recognize");
            System.out.println(ed3("recoginze", "recognize"));
            
            System.out.println("hack,hankcs");
            System.out.println(ed3("hack", "hankcs"));
        }
     
        /**
         * 
         * @category  我这里取了花费固定为1，几个常量而已，随便改。
         * @author 吴平福 
         * @param wrongWord
         * @param rightWord
         * @return
         * update 2018年1月23日
         */
        public static int ed(String wrongWord, String rightWord)
        {
            final int m = wrongWord.length();
            final int n = rightWord.length();
     
            int[][] d = new int[m + 1][n + 1];
            for (int j = 0; j <= n; ++j)
            {
                d[0][j] = j;
            }
            for (int i = 0; i <= m; ++i)
            {
                d[i][0] = i;
            }
     
//            for (int[] l : d)
//            {
//                System.out.println(Arrays.toString(l));
//            }
     
            for (int i = 1; i <= m; ++i)
            {
                char ci = wrongWord.charAt(i - 1);
                for (int j = 1; j <= n; ++j)
                {
                    char cj = rightWord.charAt(j - 1);
                    if (ci == cj)
                    {
                        d[i][j] = d[i - 1][j - 1];
                    }
                    else
                    {
                        // 等号右边的分别代表 将ci改成cj                   错串加cj         错串删ci
                        d[i][j] = Math.min(d[i - 1][j - 1] + 1, Math.min(d[i][j - 1] + 1, d[i - 1][j] + 1));
                    }
                }
            }
     
//            System.out.println();
//            for (int[] l : d)
//            {
//                System.out.println(Arrays.toString(l));
//            }
     
            return d[m][n];
        }
        
        /**
         * 一种常数时间花费较高但是很好理解的实现
         * @category 
         * @author 吴平福 
         * @param wrongWord
         * @param rightWord
         * @return
         * update 2018年1月23日
         */
        public static int ed2(String wrongWord, String rightWord)
        {
            final int m = wrongWord.length();
            final int n = rightWord.length();
     
            int[][] d = new int[m + 1][n + 1];
            for (int j = 0; j <= n; ++j)
            {
                d[0][j] = j;
            }
            for (int i = 0; i <= m; ++i)
            {
                d[i][0] = i;
            }
     
//            for (int[] l : d)
//            {
//                System.out.println(Arrays.toString(l));
//            }
     
            for (int i = 1; i <= m; ++i)
            {
                char ci = wrongWord.charAt(i - 1);
                for (int j = 1; j <= n; ++j)
                {
                    char cj = rightWord.charAt(j - 1);
                    if (ci == cj)
                    {
                        d[i][j] = d[i - 1][j - 1];
                    }
                    else if (i > 1 && j > 1 && ci == rightWord.charAt(j - 2) && cj == wrongWord.charAt(i - 2))
                    {
                        // 交错相等
                        d[i][j] = 1 + Math.min(d[i - 2][j - 2], Math.min(d[i][j - 1], d[i - 1][j]));
                    }
                    else
                    {
                        // 等号右边的分别代表 将ci改成cj                   错串加cj         错串删ci
                        d[i][j] = Math.min(d[i - 1][j - 1] + 1, Math.min(d[i][j - 1] + 1, d[i - 1][j] + 1));
                    }
                }
            }
     
//            System.out.println();
//            for (int[] l : d)
//            {
//                System.out.println(Arrays.toString(l));
//            }
     
            return d[m][n];
        }

        /**
         * 
         * @category 一种稍微饶了点弯弯并且自我感觉怪怪的实现
         * @author 吴平福 
         * @param wrongWord
         * @param rightWord
         * @return
         * update 2018年1月23日
         */
        public static int ed3(String wrongWord, String rightWord)
        {
            // 构造两个 NULL+字串，免得下标越界
            wrongWord = '+' + wrongWord;
            rightWord = '+' + rightWord;
            final int m = wrongWord.length();
            final int n = rightWord.length();
     
            int[][] d = new int[m + 1][n + 1];
            final int boarder = Math.max(m, n);
            for (int j = 2; j <= n; ++j)
            {
                d[0][j] = boarder;
                d[1][j] = j;
            }
            for (int i = 2; i <= m; ++i)
            {
                d[i][0] = boarder;
                d[i][1] = i;
            }
     
//            for (int[] l : d)
//            {
//                System.out.println(Arrays.toString(l));
//            }
     
            for (int i = 2; i <= m; ++i)
            {
                char ci = wrongWord.charAt(i - 1);
                for (int j = 2; j <= n; ++j)
                {
                    char cj = rightWord.charAt(j - 1);
                    if (ci == cj)
                    {
                        d[i][j] = d[i - 1][j - 1];
                    }
                    else if (ci == rightWord.charAt(j - 2) && cj == wrongWord.charAt(i - 2))
                    {
                        d[i][j] = Math.min(d[i - 2][j - 2], Math.min(d[i - 1][j], d[i][j - 1])) + 1;
                    }
                    else
                    {
                        // 等号右边的分别代表 将ci改成cj                   错串加cj         错串删ci
                        d[i][j] = Math.min(d[i - 1][j - 1] + 1, Math.min(d[i][j - 1] + 1, d[i - 1][j] + 1));
                    }
                }
            }
     
//            System.out.println();
//            for (int[] l : d)
//            {
//                System.out.println(Arrays.toString(l));
//            }
     
            return d[m][n];
        }
}
