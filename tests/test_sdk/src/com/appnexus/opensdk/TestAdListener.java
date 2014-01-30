/*
 *    Copyright 2013 APPNEXUS INC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.appnexus.opensdk;

import com.appnexus.opensdk.shadows.ShadowWebSettings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(shadows = {ShadowWebSettings.class})
@RunWith(RobolectricTestRunner.class)
public class TestAdListener extends BaseRoboTest {

    @Override
    public void setup() {
        super.setup();
        bannerAdView.setAdListener(this);

        adRequest = new AdRequest(bannerAdView.mAdFetcher);

        adLoaded = false;
        adFailed = false;
        adExpanded = false;
        adCollapsed = false;
        adClicked = false;
    }

    @Test
    public void testAdLoaded() {
        Robolectric.addPendingHttpResponse(200, TestResponses.banner());
        adRequest.execute();
        Robolectric.runBackgroundTasks();

        Robolectric.runUiThreadTasks();
        assertCallbacks(true);
    }

    @Test
    public void testAdFailed() {
        Robolectric.addPendingHttpResponse(200, TestResponses.blank());
        adRequest.execute();
        Robolectric.runBackgroundTasks();

        Robolectric.runUiThreadTasks();
        assertCallbacks(false);
    }
}
