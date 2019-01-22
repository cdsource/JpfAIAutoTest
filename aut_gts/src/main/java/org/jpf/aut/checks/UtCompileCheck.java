/**
 * copyrigth by wupf@aliyun.com
 * 2018年6月2日
 */
package org.jpf.aut.checks;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

import org.jpf.aut.common.consts.AiTestConst;
import org.jpf.utils.ios.AiOsUtil;
import org.jpf.utils.ios.ProcessUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wupf@aliyun.com
 *
 */
public class UtCompileCheck {
	private static final Logger logger = LogManager.getLogger();

	String strPomPath = "";
	boolean bOK = false;
	int iDeleteTestFileCount = 0;
	Map<String, String> mapDelTestFile = new HashMap<>();

	/**
	 * 
	 * @param strStartPath
	 */
	public UtCompileCheck(final String strPomPath) {
		// TODO Auto-generated constructor stub
		long start = System.currentTimeMillis();
		this.strPomPath = strPomPath;
		try {
			int iTryCount = 0;

			while (!bOK) {
				bOK = true;
				// runexec("cmd /c " + strStartPath.substring(0, 2) + " && cd " + strStartPath +
				// " && mvn test-compile");
				runexec(strPomPath);
				iTryCount++;
				logger.info("iTryCount=" + iTryCount);
				logger.info("iDeleteTestFileCount=" + mapDelTestFile.size());
			}

			logger.info("iTryCount=" + iTryCount);
			logger.info("iDeleteTestFileCount=" + mapDelTestFile.size());

		} catch (Exception ex) {
			logger.error(ex);
		}
		logger.info("ExcuteTime " + (System.currentTimeMillis() - start) + "ms");
	}

	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strCmd 2018年7月2日
	 */
	private void runShell(String strStartPath) {
		try {
			String[] cmd = new String[] { "/bin/sh", "-c", "cd " + strStartPath + "  ; mvn compile test-compile" };
			Process process = Runtime.getRuntime().exec(cmd);
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			String line;

			while ((line = input.readLine()) != null) {
				line = line.trim();
				logger.info(line);
				if (line.startsWith("[ERROR]")) {

					// logger.debug(URLEncoder.encode(line, "utf-8"));
					// String gStr= new String(uStr.getBytes("UTF-8"), "GB2312");
					int j = line.indexOf(".java:[") + 5;
					// logger.info(line.indexOf("src" + AiTestConst.FileSep + "test" +
					// AiTestConst.FileSep + "java"));
					// logger.info(j);

					if (j > 0
							&& line.indexOf("src" + java.io.File.separator + "test" + java.io.File.separator + "java") > 0) {
						String strDelFileName = line.substring(8, j).trim();
						logger.debug(strDelFileName);

						delFile(strDelFileName);
						iDeleteTestFileCount++;

						bOK = false;

					}

				}
				if (line.trim().equalsIgnoreCase("[INFO] BUILD SUCCESS")) {
					bOK = true;
					logger.info(line);
				}
			}
			process.waitFor();
			if (process.exitValue() != 0) {
				logger.error(" Failed to check test, exit value is " + process.exitValue());
				// return false;
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strCmd 2018年7月1日
	 */
	private void runexec(String strStartPath) {
		if (AiOsUtil.getInstance().isWindows()) {
			runCmd(strStartPath, "mvn compile test-compile");
		} else {
			runShell(strStartPath);
		}
	}

	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param in_FileName 2018年7月2日
	 */
	public void delFile(String in_FileName) {
		try {
			File delFile = new File(in_FileName);
			logger.info(delFile.getAbsolutePath());
			if (delFile.isFile()) {
				delFile.delete();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strCmd 2018年7月1日
	 */
	private void runCmd(String strStartPath) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(strStartPath);
			}
			String strCmd = "cmd /c " + strStartPath.substring(0, 2) + " && cd " + strStartPath
					+ "  && mvn compile test-compile";
			Process process = Runtime.getRuntime().exec(strCmd);
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			String line;

			while ((line = input.readLine()) != null) {

				line = line.trim();
				if (line.startsWith("[ERROR]")) {
					logger.debug(line);
					// logger.debug(URLEncoder.encode(line, "GBK"));
					// String gStr= new String(uStr.getBytes("UTF-8"), "GB2312");
					int j = line.indexOf(".java:[") + 5;
					if (j > 0 && line.indexOf("\\test\\") > 0) {
						String strDelFileName = line.substring(7, j).trim();
						logger.debug(strDelFileName);
						mapDelTestFile.put(strDelFileName, "");
						delFile(strDelFileName);
						iDeleteTestFileCount++;

						bOK = false;

					}

				}
				if (line.trim().equalsIgnoreCase("[INFO] BUILD SUCCESS")) {
					bOK = true;
					logger.info(line);
				}
			}
			process.waitFor();
			int iRetValue = process.exitValue();

		} catch (Exception e) {
			logger.error(e);

		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (1 == args.length) {
			new UtCompileCheck(args[0]);
		} else {
			logger.info("error input!");
		}
	}

	public void runCmd(String strStartPath, String strCmd) {

		try {
			// logger.debug(strStartPath);
			if (strStartPath == null) {
				strCmd = "cmd /c " + strCmd;
			} else {
				strCmd = "cmd /c " + strStartPath.substring(0, 2) + " && cd " + strStartPath + "  && " + strCmd;
			}
			logger.debug(strCmd);
			Process process = Runtime.getRuntime().exec(strCmd);

			final InputStream is1 = process.getInputStream();
			// 启动单独的线程来清空p.getInputStream()的缓冲区
			new Thread(new Runnable() {
				public void run() {
					BufferedReader br = new BufferedReader(new InputStreamReader(is1));
					try {
						String line = null;
						while ((line = br.readLine()) != null) {
							line = line.trim();
							if (line.startsWith("[ERROR]")) {
								logger.debug(line);
								int j = line.indexOf(".java:[") + 5;
								if (j > 0 && line.indexOf("\\test\\") > 0) {
									String strDelFileName = line.substring(7, j).trim();
									logger.debug(strDelFileName);
									mapDelTestFile.put(strDelFileName, "");
									delFile(strDelFileName);
									iDeleteTestFileCount++;
									bOK = false;
								}
							}
							if (line.trim().equalsIgnoreCase("[INFO] BUILD SUCCESS")) {
								bOK = true;
								logger.info(line);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			InputStream is2 = process.getErrorStream();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

			String line = null;
			while ((line = br2.readLine()) != null) {
				// System.out.println("2:" + line);
				line = line.trim();
				if (line.startsWith("[ERROR]")) {
					logger.debug(line);
					// logger.debug(URLEncoder.encode(line, "GBK"));
					// String gStr= new String(uStr.getBytes("UTF-8"), "GB2312");
					int j = line.indexOf(".java:[") + 5;
					if (j > 0 && line.indexOf("\\test\\") > 0) {
						String strDelFileName = line.substring(7, j).trim();
						logger.debug(strDelFileName);
						mapDelTestFile.put(strDelFileName, "");
						delFile(strDelFileName);
						iDeleteTestFileCount++;

						bOK = false;

					}

				}
				if (line.trim().equalsIgnoreCase("[INFO] BUILD SUCCESS")) {
					bOK = true;
					logger.info(line);
				}

			}

			process.waitFor();
			ProcessUtil.destroyProcess(process);

		} catch (Exception e) {
			logger.error(e);
		}
	}
}
