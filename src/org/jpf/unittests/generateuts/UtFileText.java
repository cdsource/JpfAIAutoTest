/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年11月8日 下午9:21:25 类说明
 */

package org.jpf.unittests.generateuts;

/**
 * 
 */
public class UtFileText {

    /**
     * 
     */
    public UtFileText() {
        // TODO Auto-generated constructor stub
    }

    public StringBuffer sbImports = new StringBuffer();
    public StringBuffer sbPackage = new StringBuffer();
    public StringBuffer sbClassDesc = new StringBuffer();
    private StringBuffer sbMinConstructor = new StringBuffer();
    private StringBuffer sbPrivateConstructor = new StringBuffer();
    public StringBuffer sbMethod = new StringBuffer();
    public StringBuffer sbBasic = new StringBuffer();

    @Override
    public String toString() {
        return sbPackage.append("\n").append(sbImports).append("\n").append(sbClassDesc).append("\n")
                // .append(sbConstructor).append("\n")
                .append(sbMethod).append("\n").append(sbBasic).toString();
    }

    public void clean() {
        sbPackage.setLength(0);
        sbImports.setLength(0);
        sbClassDesc.setLength(0);
        // .append(sbConstructor).append("\n")
        sbMethod.setLength(0);
        sbBasic.setLength(0);
    }

    /**
     * @param sbPrivateConstructor the sbPrivateConstructor to set
     */
    public void setSbPrivateConstructor(StringBuffer sbPrivateConstructor) {
        this.sbPrivateConstructor = sbPrivateConstructor;
    }

    /**
     * 
     * @category 根据方法参数类型增加IMPORT
     * @author 吴平福
     * @param strImport update 2017年11月8日
     */
    public void addImport(String strImport) {
        if (sbImports.indexOf(strImport) < 0) {
            sbImports.append(strImport).append("\n");
        }
    }

    /**
     * @return the sbPrivateConstructor
     */
    public StringBuffer getSbPrivateConstructor() {
        return sbPrivateConstructor;
    }

    public String getMinConstructor() {
        if (sbMinConstructor.length() > 0) {
            return sbMinConstructor.toString();
        } else if (sbPrivateConstructor.length() > 0) {
            return sbPrivateConstructor.toString();
        }
        return "";
    }

    public void setMinConstructor(StringBuffer sb) {
        sbMinConstructor.setLength(0);
        sbMinConstructor.append(sb.toString().replaceAll("result", "fixture"));
    }

    public void setMinConstructor(String sb) {
        sbMinConstructor.setLength(0);
        sbMinConstructor.append(sb.replaceAll("result", "fixture"));
    }
}
