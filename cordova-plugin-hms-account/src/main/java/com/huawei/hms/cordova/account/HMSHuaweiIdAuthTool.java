/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.huawei.hms.cordova.account;

import android.accounts.Account;
import android.app.Activity;
import android.util.Log;

import com.huawei.hms.cordova.account.utils.Error;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.hwid.tools.HuaweiIdAuthTool;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.huawei.hms.cordova.account.utils.HMSAccountUtils.fromJSONArrayToScopeList;
import static com.huawei.hms.cordova.account.utils.HMSAccountUtils.fromJSONObjectToAccount;

public class HMSHuaweiIdAuthTool extends CordovaPlugin {
    public static final String TAG = HMSHuaweiIdAuthTool.class.getSimpleName();

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("requestUnionId".equals(action)) {
            String huaweiAccountName = args.getString(0);
            requestUnionId(huaweiAccountName, callbackContext);
            return true;
        } else if ("requestAccessToken".equals(action)) {
            JSONObject account = args.getJSONObject(0);
            JSONArray scopeList = args.getJSONArray(1);
            requestAccessToken(account, scopeList, callbackContext);
            return true;
        } else if ("deleteAuthInfo".equals(action)) {
            String accessToken = args.getString(0);
            deleteAuthInfo(accessToken, callbackContext);
            return true;
        }

        return false;
    }

    private void requestUnionId(String huaweiAccountName, CallbackContext callbackContext) throws JSONException {
        Log.i(TAG, "requestUnionId start");
        try {
            String unionId = HuaweiIdAuthTool.requestUnionId(cordova.getActivity(), huaweiAccountName);
            callbackContext.success(unionId);
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
            callbackContext.error(Error.getErrorMessage(e));
        }
        Log.i(TAG, "requestUnionId end");
    }

    private void requestAccessToken(JSONObject account, JSONArray scopeList, CallbackContext callbackContext) throws JSONException {
        Log.i(TAG, "requestAccessToken start");
        Account getAccount = fromJSONObjectToAccount(account);
        List<Scope> getScopeList = fromJSONArrayToScopeList(scopeList);
        try {
            String token = HuaweiIdAuthTool.requestAccessToken(cordova.getActivity(), getAccount, getScopeList);
            callbackContext.success(token);
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
            callbackContext.error(Error.getErrorMessage(e));
        }
        Log.i(TAG, "requestAccessToken end");
    }

    private void deleteAuthInfo(String accessToken, CallbackContext callbackContext) throws JSONException {
        Log.i(TAG, "deleteAuthInfo start");
        try {
            HuaweiIdAuthTool.deleteAuthInfo(cordova.getActivity(), accessToken);
            callbackContext.success();
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
            callbackContext.error(Error.getErrorMessage(e));
        }
        Log.i(TAG, "deleteAuthInfo end");
    }
}
