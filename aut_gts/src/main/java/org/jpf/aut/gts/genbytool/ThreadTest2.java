/**
 * copyrigth by wupf@asiainfo.com
 * 2018年8月25日
 */
package org.jpf.aut.gts.genbytool;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ThreadTest2 {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 */
	public ThreadTest2() {
		// TODO Auto-generated constructor stub
		try {
			int i = 0;
			int iThreadNumber = 0;
			int iThreadCount = 0;
			int THREAD_MAX = 5;
			String strCmd = "ping www.163.com -t";
			for (int m = 0; m < 30; m++) {
				while (Thread.currentThread().activeCount() > THREAD_MAX) {
					System.out.println("activeCount() =" + Thread.currentThread().activeCount() + "  "
							+ (iThreadNumber + "/" + iThreadCount + "/" + 100));
					Thread.sleep(4000);
				}
				iThreadNumber++;
				generateThread cgenerateThread = new generateThread(strCmd, String.valueOf(iThreadNumber));
				cgenerateThread.start();
				iThreadCount++;
			}
			while (Thread.currentThread().activeCount() > 1) {
				i++;
				System.out.println("activeCount() =" + Thread.currentThread().activeCount() + ": " + i * 10);
				Thread.sleep(4000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("game over");
	}

	/**
	 * @author wupf@asiainfo.com
	 * @param args 2018年8月25日
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadTest2 cThreadTest2 = new ThreadTest2();
	}

	class generateThread extends Thread {
		String strCmd = "";
		String strJavaFileName = "";

		public generateThread(final String inCmd, String strJavaFileName) {
			this.strCmd = inCmd;
			this.strJavaFileName = strJavaFileName;
		}

		public void run() {
			try {
				// logger.info("strCmd"+strCmd);
				long start = System.currentTimeMillis();
				Process process = Runtime.getRuntime().exec(strCmd);
				// jdk1.8

				InputStreamReader ir = new InputStreamReader(process.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);

				String line;

				while ((line = input.readLine()) != null) {
					System.out.println(strJavaFileName + ":" + line);
					if ((System.currentTimeMillis() - start) > 10000) {
						logger.info("timeout: thread" + strJavaFileName);
						process.destroy();
						break;
					}
				}
				if (process.exitValue() != 0) {
					System.out.println(" Failed to generate test, exit value is " + process.exitValue());
					// return false;
				}
				logger.info("exit: thread" + strJavaFileName);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("generate finish:" + strJavaFileName);
		}
	}

	public void doCommand() {
		try {
			CommandLine cmdLine = new CommandLine("AcroRd32.exe");

			cmdLine.addArgument("/p");

			DefaultExecutor executor = new DefaultExecutor();

            //创建监控时间60秒，超过60秒则中端执行
			ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
			executor.setWatchdog(watchdog);
			executor.setExitValue(1);
			int exitValue = executor.execute(cmdLine);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
