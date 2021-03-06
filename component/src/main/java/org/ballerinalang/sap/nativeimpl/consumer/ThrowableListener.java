/*
 * Copyright (c) 2019, WSO2 Inc. (http:www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http:www.apache.orglicensesLICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.sap.nativeimpl.consumer;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerErrorListener;
import com.sap.conn.jco.server.JCoServerExceptionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ballerinalang.bre.Context;
import org.ballerinalang.connector.api.Resource;
import org.ballerinalang.sap.utils.ResponseCallback;
import org.ballerinalang.sap.utils.SapUtils;

import java.util.Map;

/**
 * A class that handles the errors when JcoServer to be started.
 */
public class ThrowableListener implements JCoServerErrorListener, JCoServerExceptionListener {

    private Context context;
    private ResponseCallback callback;
    private Map<String, Resource> sapService;

    ThrowableListener(Context context, Map<String, Resource> sapService) {
        this.context = context;
        callback = new ResponseCallback();
        this.sapService = sapService;
    }

    @Override
    public void serverErrorOccurred(JCoServer server, String connectionId, JCoServerContextInfo serverCtx,
                                    Error error) {
        SapUtils.invokeOnError(sapService, callback, error.getMessage(), context);
    }

    @Override
    public void serverExceptionOccurred(JCoServer server, String connectionId, JCoServerContextInfo serverCtx,
                                        Exception error) {
        SapUtils.invokeOnError(sapService, callback, error.getMessage(), context);
    }
}
