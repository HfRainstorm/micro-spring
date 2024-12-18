/*
 * Copyright 2003 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microspring.cglib.transform.impl;

import com.microspring.cglib.core.EmitUtils;
import com.microspring.cglib.core.TypeUtils;
import com.microspring.cglib.transform.ClassEmitterTransformer;
import com.microspring.cglib.transform.*;
import java.util.*;
import com.microspring.cglib.core.*;
import com.microspring.asm.Type;

public class AddPropertyTransformer extends ClassEmitterTransformer {
    private final String[] names;
    private final Type[] types;

    public AddPropertyTransformer(Map props) {
        int size = props.size();
        names = (String[])props.keySet().toArray(new String[size]);
        types = new Type[size];
        for (int i = 0; i < size; i++) {
            types[i] = (Type)props.get(names[i]);
        }
    }

    public AddPropertyTransformer(String[] names, Type[] types) {
        this.names = names;
        this.types = types;
    }

    public void end_class() {
        if (!TypeUtils.isAbstract(getAccess())) {
            EmitUtils.add_properties(this, names, types);
        }
        super.end_class();
    }
}
