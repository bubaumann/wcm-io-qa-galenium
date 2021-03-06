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
package io.wcm.qa.galenium.sampling.differences;

import io.wcm.qa.galenium.util.TestDevice;

/**
 * {@link TestDevice} based {@link Difference}.
 */
public class ScreenWidthDifference extends StringDifference {

  /**
   * @param device defines screen width
   */
  public ScreenWidthDifference(TestDevice device) {
    super(Integer.toString(device.getScreenSize().getWidth()));
  }

}
