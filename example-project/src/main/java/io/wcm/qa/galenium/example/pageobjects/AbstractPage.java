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
package io.wcm.qa.galenium.example.pageobjects;

import io.wcm.qa.galenium.util.GaleniumConfiguration;

import org.openqa.selenium.WebDriver;


abstract class AbstractPage extends AbstractWebDriverPageObject {

  private Navigation navigation;

  AbstractPage(WebDriver driver) {
    super(driver);
  }

  public Navigation getNavigation() {
    if (navigation == null) {
      navigation = new Navigation(getDriver());
    }
    return navigation;
  }

  public Footer getFooter() {
    return new Footer(driver);
  }

  public void load() {
    getDriver().get(getPageUrl());
  }

  private String getPageUrl() {
    return GaleniumConfiguration.getBaseUrl() + getRelativePath();
  }

  protected abstract String getRelativePath();

}
