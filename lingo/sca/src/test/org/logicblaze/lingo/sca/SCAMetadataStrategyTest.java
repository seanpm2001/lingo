/**
 *
 * Copyright 2005 LogicBlaze, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **/
package org.logicblaze.lingo.sca;

import org.logicblaze.lingo.*;

import java.lang.reflect.Method;

import junit.framework.TestCase;

/**
 * @version $Revision$
 */
public class SCAMetadataStrategyTest extends TestCase {
    protected MetadataStrategy strategy;

    public void testExampleService_someOneWayMethod() throws Exception {
        MethodMetadata metadata = strategy.getMethodMetadata(findMethod(getServiceClass(), "someOneWayMethod"));
        assertEquals("oneway", true, metadata.isOneWay());
        assertEquals("param 0 remote", false, metadata.isRemoteParameter(0));
        assertEquals("param 1 remote", false, metadata.isRemoteParameter(1));
    }

    public void testExampleService_regularRPC() throws Exception {
        MethodMetadata metadata = strategy.getMethodMetadata(findMethod(getServiceClass(), "regularRPC"));
        assertEquals("oneway", false, metadata.isOneWay());
        assertEquals("param 0 remote", false, metadata.isRemoteParameter(0));
    }

    public void testExampleService_anotherRPC() throws Exception {
        MethodMetadata metadata = strategy.getMethodMetadata(findMethod(getServiceClass(), "anotherRPC"));
        assertEquals("oneway", false, metadata.isOneWay());
    }

    public void testExampleService_asyncRequestResponse() throws Exception {
        MethodMetadata metadata = strategy.getMethodMetadata(findMethod(getServiceClass(), "asyncRequestResponse"));
        assertEquals("oneway", true, metadata.isOneWay());
        assertEquals("param 0 remote", false, metadata.isRemoteParameter(0));
        assertEquals("param 1 remote", true, metadata.isRemoteParameter(1));
    }

    protected Method findMethod(Class type, String name) throws Exception {
        Method[] methods = type.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().equals(name)) {
                return method;
            }
        }
        fail("No such method called: " + name + " on type: " + type.getName());
        return null;
    }

    protected void setUp() throws Exception {
        strategy = createMetadataStrategy();
    }

    protected MetadataStrategy createMetadataStrategy() {
        return MetadataStrategyHelper.newInstance();
    }

    protected Class getServiceClass() {
        return ScaServiceImpl.class;
    }
}