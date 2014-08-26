/*
 * Copyright 2014 Alexander Oprisnik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oprisnik.semdroid.training;

import com.oprisnik.semdroid.analysis.BaseAnalysisPlugin;
import com.oprisnik.semdroid.analysis.results.AppAnalysisReport;
import com.oprisnik.semdroid.app.App;
import com.oprisnik.semdroid.app.DexClass;
import com.oprisnik.semdroid.app.DexMethod;
import com.oprisnik.semdroid.app.manifest.AndroidReceiver;
import com.oprisnik.semdroid.app.manifest.IntentFilter;
import com.oprisnik.semdroid.utils.Log;

import java.util.List;

/**
 * SMS classifier to label SMS sniffers and catchers according to the folder.
 * Only onReceive-Methods of classes with SMS permissions are labelled.
 */
public class SMSAndFolderCombinedAnalysisPlugin extends BaseAnalysisPlugin {

    private static final String TAG = "SMSAndFolderCombinedClassifier";

    @Override
    public String getName() {
        String name = super.getName();
        if (name == null)
            return TAG;
        return name;
    }

    @Override
    public void analyze(AppAnalysisReport report, App app) {
        System.out.println(app.getApkFile());
        String label = app.getApkFile().getParentFile().getName();

        List<AndroidReceiver> receivers = app.getManifest().getReceivers();
        for (AndroidReceiver r : receivers) {
            // Log.d(TAG, r.getFullyQualifiedName());
            for (IntentFilter f : r.getIntentFilters()) {
                // Log.d(TAG, "INTENTFILTER " + r.getFullyQualifiedName());
                if (f.getAction() != null
                        && f.getAction().contains("SMS")) {
                    // SMS action found => sniffer or catcher
                    Log.d(TAG, "Receiver " + r.getFullyQualifiedName()
                            + " uses " + f.getAction());
                    DexClass clazz = app.getClass(r.getFullyQualifiedName());
                    if (clazz != null) {
                        int size = clazz.getMethods().size();
                        if (size > 0)
                            Log.d(TAG, "Class methods: " + size);
                        else {
                            Log.e(TAG, "Class methods: " + size + " !!!");
                        }
                        for (DexMethod m : clazz.getMethods()) {
                            if (m.getName().contains("onReceive")) {
                                report.label(m, label);
                                Log.d(TAG, "Added label " + label + " to " + m.getDexClass().getFullName() + " " + m.toJavaString());
                            }
                        }
                    } else {
                        Log.e(TAG,
                                "Could not find class "
                                        + r.getFullyQualifiedName()
                        );
                        for (DexClass c : app.getClasses()) {
                            Log.e(TAG, "only found: " + c.getFullName());
                        }

                    }
                }
            }
        }
    }


}
