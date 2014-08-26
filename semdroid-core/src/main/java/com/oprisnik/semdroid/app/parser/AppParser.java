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

package com.oprisnik.semdroid.app.parser;

import com.oprisnik.semdroid.app.App;
import com.oprisnik.semdroid.config.Configurable;

import java.io.File;

/**
 * Parse a given app and return the corresponding App object.
 * <p/>
 * All subsequent analysis plugins operate on the contents of the App objects. Hence all required information has to be
 * extracted by the AppParser.
 *
 * @see com.oprisnik.semdroid.app.App
 */
public interface AppParser extends Configurable {

    public App parse(byte[] app) throws Exception;

    public App parse(File app) throws Exception;

    public void registerProgressListener(ProgressListener listener);

    public void unregisterProgressListener(ProgressListener listener);

    public interface ProgressListener {
        public void onProgressUpdated(int percentage);
    }
}




