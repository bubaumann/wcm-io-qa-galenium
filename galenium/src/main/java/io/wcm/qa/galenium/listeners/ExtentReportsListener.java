/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2017 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.qa.galenium.listeners;

import static io.wcm.qa.galenium.reporting.GaleniumReportUtil.getLogger;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.IConfigurationListener2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import io.wcm.qa.galenium.reporting.GaleniumReportUtil;
import io.wcm.qa.galenium.util.TestDevice;
import io.wcm.qa.galenium.webdriver.WebDriverManager;


public class ExtentReportsListener implements ITestListener, IConfigurationListener2 {

  @Override
  public void onFinish(ITestContext context) {
    ExtentReports extentReport = GaleniumReportUtil.getExtentReports();
    extentReport.setTestRunnerOutput(StringUtils.join(Reporter.getOutput(), "</br>"));
    extentReport.flush();
  }

  @Override
  public void onStart(ITestContext context) {
    // TODO: Auto-generated method stub
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    // TODO: Auto-generated method stub
  }


  @Override
  public void onTestFailure(ITestResult result) {
    String logMsgHtml = "Error when dealing with test failure.";
    try {

      StringBuilder logMsg = new StringBuilder().append(System.lineSeparator()).append(System.lineSeparator());
      logMsg.append("+++++++++++++++Failed Test++++++++++++++++").append(System.lineSeparator());
      logMsg.append("Testcase: ").append(getTestName(result)).append(System.lineSeparator());
      Throwable throwable = result.getThrowable();
      logMsg.append("Location: ").append(getLineThatThrew(throwable)).append(System.lineSeparator());
      logMsg.append("Error: ").append(throwable.getMessage()).append(System.lineSeparator());

      WebDriver driver = getDriver();
      if (driver != null) {
        logMsg.append(GaleniumReportUtil.takeScreenshot(result, driver));
        logMsg.append("URL: ").append(driver.getCurrentUrl()).append(System.lineSeparator());
      }
      if (getTestDevice() != null) {
        logMsg.append("Browser: ").append(getTestDevice().toString()).append(System.lineSeparator());
      }
      logMsg.append("Duration: ").append(getTestDuration(result)).append(System.lineSeparator());

      GaleniumReportUtil.getLogger().debug(GaleniumReportUtil.MARKER_FAIL, "Full stacktrace", throwable);

      logMsg.append("++++++++++++++++++++++++++++++++++++++++++").append(System.lineSeparator());

      logMsgHtml = logMsg.toString().replace(System.lineSeparator(), "<br />");
      Reporter.log(logMsgHtml, false);
      getLogger().error(logMsg.toString());
    }
    catch (Throwable ex) {
      GaleniumReportUtil.getLogger().error("Error during failure handling", ex);
      throw ex;
    }
    finally {
      GaleniumReportUtil.endExtentTest(result, LogStatus.FAIL, logMsgHtml);
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    // TODO: Auto-generated method stub
  }

  @Override
  public void onTestStart(ITestResult result) {
    GaleniumReportUtil.getExtentTest(result);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    try {
      takeScreenshot(result);

      String msg = getTestName(result) + ": Success (" + getTestDuration(result) + ")";
      Reporter.log(msg + "<br />", false);
      getLogger().info(msg);
    }
    finally {
      GaleniumReportUtil.endExtentTest(result, LogStatus.PASS, "SUCCESS");
    }
  }

  private WebDriver getDriver() {
    return WebDriverManager.getCurrentDriver();
  }

  private String getLineThatThrew(Throwable t) {
    StackTraceElement[] stackTraceLines = t.getStackTrace();
    for (StackTraceElement stackTraceLine : stackTraceLines) {
      String className = stackTraceLine.getClassName();
      if (!className.startsWith("sun.") && !className.startsWith("java.") && !className.startsWith("org.openqa.selenium.")) {
        return stackTraceLine.toString();
      }
    }
    return "??? (cannot find throwing line)";
  }

  private TestDevice getTestDevice() {
    return WebDriverManager.get().getTestDevice();
  }

  private String getTestDuration(ITestResult result) {
    return Long.toString(result.getEndMillis() - result.getStartMillis()) + "ms";
  }

  private void takeScreenshot(ITestResult result) {
    WebDriver driver = getDriver();
    if (driver != null) {
      GaleniumReportUtil.takeScreenshot(result, driver);
    }
  }

  protected String getAdditionalInfo() {
    String additionalInfo = "no additional info";
    TestDevice testDevice = getTestDevice();
    if (testDevice != null) {
      additionalInfo = " [" + testDevice.toString() + "] ";
    }
    return additionalInfo;
  }

  protected String getTestName(ITestResult result) {
    return result.getTestClass().getRealClass().getSimpleName() + "." + result.getName() + "(" + getAdditionalInfo() + ") ";
  }

  @Override
  public void onConfigurationSuccess(ITestResult itr) {
    // TODO: Auto-generated method stub

  }

  @Override
  public void onConfigurationFailure(ITestResult itr) {
    // TODO: Auto-generated method stub

  }

  @Override
  public void onConfigurationSkip(ITestResult itr) {
    // TODO: Auto-generated method stub

  }

  @Override
  public void beforeConfiguration(ITestResult tr) {
    GaleniumReportUtil.getExtentTest(tr);
  }

}
