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

package com.oprisnik.semdroid.feature.instance.method;

import com.oprisnik.semdroid.app.DexMethod;
import com.oprisnik.semdroid.app.LocalVariable;
import com.oprisnik.semdroid.app.Opcode;
import com.oprisnik.semdroid.feature.instance.MethodInstanceGenerator;
import com.oprisnik.semdroid.utils.SparseIntHistogram;

import java.util.HashSet;
import java.util.Set;

import at.tuflowgraphy.semantic.base.domain.data.InstanceDataElement;

/**
 * Simple method based feature vector generator.
 */
public class SimpleMIG extends MethodInstanceGenerator {

    private static final String TAG = "SimpleMIG";

    @Override
    protected void getMethodInstance(DexMethod method,
                                     InstanceDataElement results) {
        Set<String> permissions = new HashSet<String>();
        SparseIntHistogram opcodes = new SparseIntHistogram();
        Set<String> localVarTypes = new HashSet<String>();

        for (String s : method.getPermissions()) {
            permissions.add(s);
        }
        for (Opcode op : method.getOpcodes()) {
            opcodes.increase(getOpcodeGroup(op));
        }
        for (LocalVariable var : method.getLocalVariables()) {
            localVarTypes.add(var.getType());
        }

//		Log.v(TAG, method.toJavaString());

        // add data
        for (String permission : permissions) {
            results.addValue(getSymbolicFeatureDataElement("usesPermission",
                    permission));
//			Log.d(TAG, "usesPermission " + permission);
        }
        for (String t : localVarTypes) {
            results.addValue(getSymbolicFeatureDataElement("localVar", t));
//			Log.v(TAG, "localVar " + t);
        }

        double[] op = getDoubleOpcodeArray(opcodes);
        results.addValue(getDistanceBasedFeatureDataElement("opcodes", op));
//		Log.v(TAG, Arrays.toString(op));
    }

}
