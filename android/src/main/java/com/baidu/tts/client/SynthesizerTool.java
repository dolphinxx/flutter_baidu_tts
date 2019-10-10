/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.client;

import com.baidu.tts.jni.EmbeddedSynthesizerEngine;
import com.baidu.tts.tools.ResourceTools;
import com.baidu.tts.tools.StringTool;
import java.io.File;

public class SynthesizerTool {
    public static boolean verifyModelFile(String filePath) {
        if (StringTool.isEmpty(filePath)) {
            return false;
        }
        byte[] arrby = ResourceTools.stringToByteArrayAddNull(filePath);
        try {
            int n2 = EmbeddedSynthesizerEngine.bdTTSVerifyDataFile(arrby);
            return n2 >= 0;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static String getEngineInfo() {
        return EmbeddedSynthesizerEngine.bdTTSGetEngineParam();
    }

    public static int getEngineVersion() {
        return EmbeddedSynthesizerEngine.getEngineMinVersion();
    }

    public static String getModelInfo(String filePath) {
        File file;
        if (!StringTool.isEmpty(filePath) && (file = new File(filePath)).exists() && file.canRead()) {
            String string = EmbeddedSynthesizerEngine.bdTTSGetDatParam(filePath);
            return string;
        }
        return null;
    }
}

