"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.asyncExec = void 0;
/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
    
       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.
    */
const cordova_1 = require("cordova");
initHms();
function asyncExec(className, funcName, args = []) {
    return new Promise((resolve, reject) => {
        cordova_1.exec(resolve, reject, className, funcName, args);
    });
}
exports.asyncExec = asyncExec;
function initHms() {
    console.log('hms :: init called.');
    initEventHandler();
}
function initEventHandler() {
    if (window.hmsEventHandler != null) {
        return;
    }
    console.log('hms-location :: window.hmsEventHandler');
    if (window.hmsEventHandlers === undefined)
        window.hmsEventHandlers = {};
    window.hmsEventHandler = (eventName, data) => {
        if (window.hmsEventHandlers.hasOwnProperty(eventName)) {
            window.hmsEventHandlers[eventName].forEach((handler) => {
                handler(data);
            });
        }
    };
    window.registerHMSEvent = (eventName, handler) => {
        if (window.hmsEventHandlers.hasOwnProperty(eventName)) {
            window.hmsEventHandlers[eventName].push(handler);
        }
        else {
            window.hmsEventHandlers[eventName] = [handler];
        }
    };
    window.unregisterHMSEvent = (eventName, handler) => {
        if (window.hmsEventHandlers.hasOwnProperty(eventName)) {
            if (handler) {
                const idx = window.hmsEventHandlers[eventName].indexOf(handler);
                if (idx > -1) {
                    window.hmsEventHandlers[eventName].splice(idx, 1);
                }
            }
            else {
                window.hmsEventHandlers[eventName] = [];
            }
        }
    };
}
//# sourceMappingURL=utils.js.map