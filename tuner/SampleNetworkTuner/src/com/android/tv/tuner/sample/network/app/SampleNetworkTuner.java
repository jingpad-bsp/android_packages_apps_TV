/*
 * Copyright (C) 2018 The Android Open Source Project
 *
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
 */

package com.android.tv.tuner.sample.network.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.tv.TvContract;
import com.android.tv.common.BaseApplication;
import com.android.tv.common.actions.InputSetupActionUtils;
import com.android.tv.common.flags.impl.DefaultCloudEpgFlags;
import com.android.tv.common.flags.impl.DefaultConcurrentDvrPlaybackFlags;
import com.android.tv.common.singletons.HasSingletons;
import com.android.tv.common.util.CommonUtils;
import com.android.tv.tuner.sample.network.singletons.SampleNetworkSingletons;
import com.android.tv.tuner.sample.network.tvinput.SampleNetworkTunerTvInputService;
import com.android.tv.tuner.setup.LiveTvTunerSetupActivity;

/** The top level application for Sample DVB Tuner. */
public class SampleNetworkTuner extends BaseApplication
        implements SampleNetworkSingletons, HasSingletons<SampleNetworkSingletons> {
    private String mEmbeddedInputId;
    private final DefaultCloudEpgFlags mCloudEpgFlags = new DefaultCloudEpgFlags();
    private final DefaultConcurrentDvrPlaybackFlags mConcurrentDvrPlaybackFlags =
            new DefaultConcurrentDvrPlaybackFlags();

    @Override
    public Intent getTunerSetupIntent(Context context) {
        // Make an intent to launch the setup activity of TV tuner input.
        Intent intent =
                CommonUtils.createSetupIntent(
                        new Intent(context, LiveTvTunerSetupActivity.class), mEmbeddedInputId);
        intent.putExtra(InputSetupActionUtils.EXTRA_INPUT_ID, mEmbeddedInputId);
        return intent;
    }

    @Override
    public synchronized String getEmbeddedTunerInputId() {
        if (mEmbeddedInputId == null) {
            mEmbeddedInputId =
                    TvContract.buildInputId(
                            new ComponentName(this, SampleNetworkTunerTvInputService.class));
        }
        return mEmbeddedInputId;
    }

    @Override
    public DefaultCloudEpgFlags getCloudEpgFlags() {
        return mCloudEpgFlags;
    }

    @Override
    public BuildType getBuildType() {
        return BuildType.ENG;
    }

    @Override
    public DefaultConcurrentDvrPlaybackFlags getConcurrentDvrPlaybackFlags() {
        return mConcurrentDvrPlaybackFlags;
    }

    @Override
    public SampleNetworkSingletons singletons() {
        return this;
    }
}