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

import io.wcm.qa.galenium.selectors.Selector;
import io.wcm.qa.galenium.selectors.SelectorFactory;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Footer with meta navigation, copyright, and disclaimer.
 */
public class Footer extends AbstractWebDriverPageObject {

  private static final Selector SELECTOR_METANAV_SECTION = SelectorFactory.fromCss("nav > section");
  private static final Selector SELECTOR_FOOTER = SelectorFactory.fromCss("footer.footer-site");
  private List<FooterNavSection> navSections;
  private WebElement footer;

  /**
   * @param driver web driver
   */
  Footer(WebDriver driver) {
    super(driver);
  }

  /**
   * @return sections of meta nav
   */
  public List<FooterNavSection> getNavSections() {
    if (navSections == null) {
      navSections = new ArrayList<FooterNavSection>();
      List<WebElement> elements = getFooter().findElements(SELECTOR_METANAV_SECTION.asBy());
      for (WebElement webElement : elements) {
        navSections.add(new FooterNavSection(webElement));
      }
    }
    return navSections;
  }

  private WebElement getFooter() {
    if (footer == null) {
      footer = getDriver().findElement(SELECTOR_FOOTER.asBy());
    }
    return footer;
  }

}
