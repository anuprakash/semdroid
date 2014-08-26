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

package com.oprisnik.semdroid.filter;

import com.oprisnik.semdroid.app.DexMethod;
import com.oprisnik.semdroid.config.BadConfigException;
import com.oprisnik.semdroid.config.Config;


/**
 * White- and Blacklist filter for methods.
 */
public class MethodWhiteAndBlacklistFilter extends DefaultMethodFilter {

    private WhiteAndBlackList mFilter;

    @Override
    public void init(Config config) throws BadConfigException {
        super.init(config);
        mFilter = new WhiteAndBlackList();
        mFilter.init(config);
    }

    @Override
    public boolean use(DexMethod data) {
        return super.use(data) && mFilter.checkPrefix(data.getMethodName());
    }
}
