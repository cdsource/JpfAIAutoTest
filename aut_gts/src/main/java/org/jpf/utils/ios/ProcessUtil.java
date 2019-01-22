/**
 * copyrigth by wupf@asiainfo.com
 * 2018年9月12日
 */
package org.jpf.utils.ios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.utils.ios.AiOsUtil;

/**
 * @author wupf@aliyun.com
 *
 */
public class ProcessUtil {
	private static final Logger logger = LogManager.getLogger();

	public static void destroyProcess(Process p) {
		if (p == null)
			return;
		InputStream stdout = p.getInputStream();
		InputStream stderr = p.getErrorStream();
		OutputStream stdin = p.getOutputStream();
		try {
			if (stdout != null)
				stdout.close();
			if (stderr != null)
				stderr.close();
			if (stdin != null)
				stdin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.destroy();
	}

	public static void runexec(String strStartPath, String strCmd) throws Exception {
		if (AiOsUtil.getInstance().isWindows()) {
			runCmd(strStartPath, strCmd);
		} else {
			runShell(strStartPath, strCmd);
		}
	}
	public static void runexec(String strStartPath, String strCmd,StringBuffer sbError) throws Exception {
		if (AiOsUtil.getInstance().isWindows()) {
			runCmd(strStartPath, strCmd,sbError);
		} else {
			runShell(strStartPath, strCmd,sbError);
		}
	}
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strCmd 2018年7月1日
	 */
	public static void runCmd(String strStartPath, String strCmd) throws Exception {

		try {
			// logger.debug(strStartPath);
			if (strStartPath == null) {
				strCmd = "cmd /c " + strCmd;
			} else {
				strCmd = "cmd /c " + strStartPath.substring(0, 2) + " && cd " + strStartPath + "  && " + strCmd;
			}
			logger.info(strCmd);
			Process process = Runtime.getRuntime().exec(strCmd);

			final InputStream is1 = process.getInputStream();
			// 启动单独的线程来清空p.getInputStream()的缓冲区
			new Thread(new Runnable() {
				public void run() {
					BufferedReader br = new BufferedReader(new InputStreamReader(is1));
					try {
						String line = null;
						while ((line = br.readLine()) != null) {
							if (line.indexOf("evosuite")<0)
							//System.out.println("1:" + line);
							/*
							 * if (line.startsWith("[ERROR]") &&
							 * !line.equalsIgnoreCase("[ERROR] There are test failures.")) {
							 * logger.error(line); // throw new Exception(line); }
							 */
							if (line.indexOf("ERROR") >= 0 || line.indexOf("error") >= 0) {
								System.out.println("1:" + line);
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
				if (line.indexOf("evosuite")<0)
				//System.out.println("2:" + line);
				/*
				 * if (line.startsWith("[ERROR]") &&
				 * !line.equalsIgnoreCase("[ERROR] There are test failures.")) {
				 * logger.error(line); // throw new Exception(line); }
				 */
				if (line.indexOf("ERROR") >= 0 || line.indexOf("error") >= 0) {
					System.out.println("2:" + line);
				}
			}

			process.waitFor();
			destroyProcess(process);
		} catch (Exception e) {
			logger.error(e);
			throw e;

		}
	}

	
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strCmd 2018年7月1日
	 */
	public static void runCmd(String strStartPath, String strCmd,StringBuffer sb) throws Exception {

		try {
			sb.setLength(0);
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
							// System.out.println("1:" + line);
							/*
							 * if (line.startsWith("[ERROR]") &&
							 * !line.equalsIgnoreCase("[ERROR] There are test failures.")) {
							 * logger.error(line); // throw new Exception(line); }
							 */
							if (line.indexOf("ERROR") >= 0 || line.indexOf("error") >= 0) {
								System.out.println("1:" + line);
								sb.append("1:" + line).append("\n");
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
				/*
				 * if (line.startsWith("[ERROR]") &&
				 * !line.equalsIgnoreCase("[ERROR] There are test failures.")) {
				 * logger.error(line); // throw new Exception(line); }
				 */
				if (line.indexOf("ERROR") >= 0 || line.indexOf("error") >= 0) {
					System.out.println("2:" + line);
					sb.append("2:" + line).append("\n");
				}
			}

			process.waitFor();
			destroyProcess(process);
		} catch (Exception e) {
			logger.error(e);
			throw e;

		}
	}
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strPomFilePath
	 * @param strCmd
	 * @throws Exception 2018年8月26日
	 */
	public static void runCmd2(String strStartPath, String strCmd) throws Exception {
		try {
			String[] cmd = new String[] { "cmd.exe", "/c", strCmd };

			logger.debug(strCmd);
			Process process = Runtime.getRuntime().exec(cmd);
			InputStream stderr = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("");
			int exitVal = process.waitFor();
			System.out.println("Process exitValue: " + exitVal);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public static void main(String[] args) {
		try {
			runCmd2(null,
					"java -jar  D:\\jworkspaces\\JpfUnitTest2\\evosuite-master-1.0.7.jar  -generateSuite -Dtest_dir=aitest_tests -base_dir=E:\\prj_code\\zjcmc\\adcloud-common\\common -Dno_runtime_dependency=true -Dlog.level=error -Dseed_dir=aitest-seeds -Dsandbox=false -Dreport_dir=aitest-report -Djunit_suffix=_WUTest -Dsearch_budget=20  -class com.asiainfo.auth.sso.gitlib.api.models.GitlabNote -projectCP E:\\prj_code\\zjcmc\\adcloud-common\\common\\target\\classes;");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strPomFilePath
	 * @param strCmd
	 * @throws Exception 2018年8月26日
	 */
	public static void runShell(String strPomFilePath, String strCmd) throws Exception {
		try {

			String[] cmd = null;
			if (strPomFilePath == null) {
				cmd = new String[] { "/bin/sh", "-c", strCmd };
			} else {
				cmd = new String[] { "/bin/sh", "-c", "cd " + strPomFilePath + "  ; " + strCmd };
			}
			Process process = Runtime.getRuntime().exec(cmd);
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			String line = null;
			while ((line = input.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("[ERROR]") && !line.equalsIgnoreCase("[ERROR] There are test failures.")) {
					logger.error(line);
					// throw new Exception(line);
				}
				// System.out.println(line);
			}
			process.waitFor();
			if (process.exitValue() != 0) {
				logger.error(" Failed to check test, exit value is " + process.exitValue());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	/**
	 * 
	 * @author wupf@aliyun.com
	 * @param strPomFilePath
	 * @param strCmd
	 * @throws Exception 2018年8月26日
	 */
	public static void runShell(String strPomFilePath, String strCmd,StringBuffer sbError) throws Exception {
		try {
			sbError.setLength(0);
			String[] cmd = null;
			if (strPomFilePath == null) {
				cmd = new String[] { "/bin/sh", "-c", strCmd };
			} else {
				cmd = new String[] { "/bin/sh", "-c", "cd " + strPomFilePath + "  ; " + strCmd };
			}
			Process process = Runtime.getRuntime().exec(cmd);
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			String line = null;
			while ((line = input.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("[ERROR]") && !line.equalsIgnoreCase("[ERROR] There are test failures.")) {
					logger.error(line);
					sbError.append(line).append("\n");
					// throw new Exception(line);
				}
				// System.out.println(line);
			}
			process.waitFor();
			if (process.exitValue() != 0) {
				logger.error(" Failed to check test, exit value is " + process.exitValue());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
}
