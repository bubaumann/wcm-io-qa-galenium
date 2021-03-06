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
package io.wcm.qa.galenium.example;

import java.util.List;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import io.wcm.qa.galenium.example.pageobjects.Footer;
import io.wcm.qa.galenium.example.pageobjects.FooterNavSection;
import io.wcm.qa.galenium.example.pageobjects.Homepage;
import io.wcm.qa.galenium.example.pageobjects.LinkItem;
import io.wcm.qa.galenium.example.pageobjects.Navigation;
import io.wcm.qa.galenium.example.pageobjects.NavigationTopLevelEntry;
import io.wcm.qa.galenium.example.pageobjects.Stage;
import io.wcm.qa.galenium.util.TestDevice;


public class PageObjectExampleIT extends AbstractExampleBase {

  private Homepage homepage;

  @Factory(dataProviderClass = TestDeviceProvider.class, dataProvider = "devices")
  public PageObjectExampleIT(TestDevice testDevice) {
    super(testDevice);
  }

  @Test(groups = "dev")
  public void testWithPageObjects() {
    getHomepage().load();
    loginToAuthor();
    checkNavigation();
    checkStage();
    checkFooter();
  }

  private void checkFooter() {
    Footer footer = getHomepage().getFooter();
    List<FooterNavSection> navSections = footer.getNavSections();
    assertEquals(navSections.size(), 3, "Meta nav sections");
    FooterNavSection conferenceSection = navSections.get(0);
    assertEquals(conferenceSection.getTitle(), "CONFERENCE", "conference section title");
    assertEquals(conferenceSection.getNavLinks().size(), 3, "conference section link count");
    FooterNavSection venueSection = navSections.get(1);
    assertEquals(venueSection.getTitle(), "VENUE", "venue section title");
    assertEquals(venueSection.getNavLinks().size(), 1, "venue section link count");
    FooterNavSection archiveSection = navSections.get(2);
    assertEquals(archiveSection.getTitle(), "ARCHIVE", "archive section title");
    assertEquals(archiveSection.getNavLinks().size(), 2, "archive section link count");
  }

  private void checkStage() {
    Stage stage = getHomepage().getStage();
    assertEquals(stage.getTitle(), "adaptTo() 2013", "stage title");
    assertEquals(stage.getDescription(), "23.–25. September 2013 Kulturbrauerei Berlin", "stage description");
    List<LinkItem> ctaLinks = stage.getCtaLinks();
    assertEquals(ctaLinks.size(), 2, "two CTA links");
  }

  private void checkNavigation() {
    Navigation navigation = getHomepage().getNavigation();
    List<NavigationTopLevelEntry> topLevelItems = navigation.getTopLevelItems();
    assertEquals(topLevelItems.size(), 2, "two top level items");
    NavigationTopLevelEntry navHome = topLevelItems.get(0);
    assertEquals(navHome.getTitle(), "HOME", "Main navigation home");
    NavigationTopLevelEntry navConference = topLevelItems.get(1);
    assertEquals(navConference.getTitle(), "CONFERENCE", "Main navigation conference");
  }

  @Override
  protected String getRelativePath() {
    // not used
    return getHomepage().getRelativePath();
  }

  private Homepage getHomepage() {
    if (homepage == null) {
      homepage = new Homepage(getDriver());
    }
    return homepage;
  }

}
