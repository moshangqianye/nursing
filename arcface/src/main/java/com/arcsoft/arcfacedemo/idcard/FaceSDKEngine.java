package com.arcsoft.arcfacedemo.idcard;

import android.content.Context;

import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.VersionInfo;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;

import java.util.List;

public class FaceSDKEngine {

    private static final String TAG = FaceSDKEngine.class.getSimpleName();
    private FaceEngine faceEngine;
    private FaceEngine faceEnginePreview;
    private int engineErrorCode = -1;
    private int enginePreviewErrorCode = -1;
    private static volatile FaceSDKEngine mInstance;
    private static final Object FACE_ENGINE_UTIL_LOCK = new Object();

    private FaceSDKEngine() {
    }

    public static FaceSDKEngine getInstance() {
        if (mInstance == null) {
            Object var0 = FACE_ENGINE_UTIL_LOCK;
            synchronized(FACE_ENGINE_UTIL_LOCK) {
                if (mInstance == null) {
                    mInstance = new FaceSDKEngine();
                }
            }
        }

        return mInstance;
    }

    public int active(Context context, String appId, String sdkKey) {
        if (this.faceEngine == null) {
            this.faceEngine = new FaceEngine();
        }

        int errCode = this.faceEngine.active(context, appId, sdkKey);
        return errCode != 0 ? errCode : 0;
    }

    public int init(Context context) {
        if (this.faceEngine == null) {
            this.faceEngine = new FaceEngine();
        }

        if (this.faceEnginePreview == null) {
            this.faceEnginePreview = new FaceEngine();
        }

        this.engineErrorCode = this.faceEngine.init(context, DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_ALL_OUT, 16, 5, 5);
        if (this.engineErrorCode != 0) {
            return this.engineErrorCode;
        } else {
            this.enginePreviewErrorCode = this.faceEnginePreview.init(context, DetectMode.ASF_DETECT_MODE_VIDEO, DetectFaceOrientPriority.ASF_OP_ALL_OUT, 16, 5, 5);
            return this.enginePreviewErrorCode != 0 ? this.enginePreviewErrorCode : 0;
        }
    }

    public void unInit() {
        if (this.faceEngine != null) {
            this.faceEngine.unInit();
        }

        if (this.faceEnginePreview != null) {
            this.faceEnginePreview.unInit();
        }

    }

    public int faceDetection(byte[] data, int width, int height, int format, List<FaceInfo> list) {
        return this.faceEngine == null ? 5 : this.faceEngine.detectFaces(data, width, height, format, list);
    }

    public int faceTrack(byte[] data, int width, int height, int format, List<FaceInfo> list) {
        return this.faceEnginePreview == null ? 5 : this.faceEnginePreview.detectFaces(data, width, height, format, list);
    }

    public int frExtractFeature(byte[] data, int width, int height, int format, FaceInfo faceInfo, FaceFeature feature, boolean isIDCard) {
        if (isIDCard) {
            return this.faceEngine == null ? 5 : this.faceEngine.extractFaceFeature(data, width, height, format, faceInfo, feature);
        } else {
            return this.faceEnginePreview == null ? 5 : this.faceEnginePreview.extractFaceFeature(data, width, height, format, faceInfo, feature);
        }
    }

    public int frFacePairMatching(FaceFeature ref, FaceFeature input, FaceSimilar score) {
        return this.faceEngine == null ? 5 : this.faceEngine.compareFaceFeature(ref, input, score);
    }

    public String getVersion() {
        if (this.faceEngine == null) {
            return "";
        } else {
            VersionInfo versionInfo = new VersionInfo();
            this.faceEngine.getVersion(versionInfo);
            return versionInfo.getVersion();
        }
    }
}
