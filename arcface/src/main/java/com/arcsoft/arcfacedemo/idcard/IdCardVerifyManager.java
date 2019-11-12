package com.arcsoft.arcfacedemo.idcard;

import android.content.Context;
import android.graphics.Rect;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IdCardVerifyManager {
    private static final String TAG = IdCardVerifyManager.class.getSimpleName();
    private FaceFeature currentFace;
    private FaceFeature inputFace;
    private boolean isCanFr = false;
    private boolean isCurrentReady = false;
    private boolean isIdCardReady = false;
    private boolean isCurrentFr = false;
    private boolean isIdCardFr = false;
    private boolean isInit = false;
    private static volatile IdCardVerifyManager mInstance;
    private static final Object INSTANCE_LOCK = new Object();
    private static final Object ID_CARD_VERIFY_LISTENER_LOCK = new Object();
    private IdCardVerifyListener idCardVerifyListener;
    private ExecutorService previewService = Executors.newSingleThreadExecutor();
    private ExecutorService idCardService = Executors.newSingleThreadExecutor();

    private IdCardVerifyManager() {
    }

    public static IdCardVerifyManager getInstance() {
        if (mInstance == null) {
            Object var0 = INSTANCE_LOCK;
            synchronized(INSTANCE_LOCK) {
                if (mInstance == null) {
                    mInstance = new IdCardVerifyManager();
                }
            }
        }

        return mInstance;
    }

    public int active(Context context, String appId, String sdkKey) {
        return FaceSDKEngine.getInstance().active(context, appId, sdkKey);
    }

    public int init(Context context, IdCardVerifyListener listener) {
        this.idCardVerifyListener = listener;
        if (this.previewService == null) {
            this.previewService = Executors.newSingleThreadExecutor();
        }

        if (this.idCardService == null) {
            this.idCardService = Executors.newSingleThreadExecutor();
        }

        int code = FaceSDKEngine.getInstance().init(context);
        this.isInit = true;
        return code;
    }

    public void unInit() {
        this.isInit = false;
        if (this.previewService != null && !this.previewService.isShutdown()) {
            this.previewService.shutdown();
        }

        if (this.idCardService != null && !this.idCardService.isShutdown()) {
            this.idCardService.shutdown();
        }

        while(this.isCurrentFr || this.isIdCardFr) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }

        this.previewService = null;
        this.idCardService = null;
        this.idCardVerifyListener = null;
        FaceSDKEngine.getInstance().unInit();
    }

    public DetectFaceResult onPreviewData(final byte[] data, final int width, final int height, boolean isVideo) {
        final DetectFaceResult result = new DetectFaceResult();
        if (data == null) {
            result.setErrCode(65537);
            return result;
        } else if (data.length != width * height * 3 / 2) {
            result.setErrCode(65538);
            return result;
        } else {
            final byte[] buffer = new byte[data.length];
            System.arraycopy(data, 0, buffer, 0, data.length);
            int code;
            final ArrayList fdFaceList;
            ArrayList fdFaceRectList;
            int maxFaceIndex;
            Rect extractRect;
            if (isVideo) {
                fdFaceList = new ArrayList();
                fdFaceRectList = new ArrayList();
                code = FaceSDKEngine.getInstance().faceTrack(buffer, width, height, 2050, fdFaceList);
                if (code != 0) {
                    result.setErrCode(code);
                    return result;
                } else if (fdFaceList.size() <= 0) {
                    result.setErrCode(65539);
                    return result;
                } else {
                    for(maxFaceIndex = 0; maxFaceIndex < fdFaceList.size(); ++maxFaceIndex) {
                        fdFaceRectList.add(((FaceInfo)fdFaceList.get(maxFaceIndex)).getRect());
                    }

                    maxFaceIndex = this.findMaxAreaFace(fdFaceRectList);
                    if (maxFaceIndex == -1) {
                        result.setErrCode(65539);
                        return result;
                    } else {
                        extractRect = (Rect)fdFaceRectList.get(maxFaceIndex);
                        result.setFaceRect(extractRect);
                        if (this.isCanFr) {
                            this.isCanFr = false;
                            this.currentFace = new FaceFeature();
                            if (this.previewService != null && !this.isCurrentFr) {
                                final int finalMaxFaceIndex1 = maxFaceIndex;
                                this.previewService.execute(new Runnable() {
                                    public void run() {
                                        if (IdCardVerifyManager.this.isInit) {
                                            IdCardVerifyManager.this.isCurrentFr = true;
                                            DetectFaceResult frResult = new DetectFaceResult();
                                            frResult.setFaceRect(result.getFaceRect());
                                            int frCode = FaceSDKEngine.getInstance().frExtractFeature(buffer, width, height, 2050, (FaceInfo)fdFaceList.get(finalMaxFaceIndex1), IdCardVerifyManager.this.currentFace, false);
                                            if (frCode != 0) {
                                                frResult.setErrCode(frCode);
                                                IdCardVerifyManager.this.isCanFr = true;
                                            } else {
                                                IdCardVerifyManager.this.isCanFr = false;
                                                IdCardVerifyManager.this.isCurrentReady = true;
                                                frResult.setErrCode(0);
                                            }

                                            synchronized(IdCardVerifyManager.ID_CARD_VERIFY_LISTENER_LOCK) {
                                                if (IdCardVerifyManager.this.idCardVerifyListener != null) {
                                                    IdCardVerifyManager.this.idCardVerifyListener.onPreviewResult(frResult, data, width, height);
                                                }
                                            }

                                            IdCardVerifyManager.this.isCurrentFr = false;
                                        }

                                    }
                                });
                            }
                        }

                        result.setErrCode(0);
                        return result;
                    }
                }
            } else {
                fdFaceList = new ArrayList();
                fdFaceRectList = new ArrayList();
                code = FaceSDKEngine.getInstance().faceDetection(buffer, width, height, 2050, fdFaceList);
                if (code != 0) {
                    result.setErrCode(code);
                    return result;
                } else if (fdFaceList.size() <= 0) {
                    result.setErrCode(65539);
                    return result;
                } else {
                    for(maxFaceIndex = 0; maxFaceIndex < fdFaceList.size(); ++maxFaceIndex) {
                        fdFaceRectList.add(((FaceInfo)fdFaceList.get(maxFaceIndex)).getRect());
                    }

                    maxFaceIndex = this.findMaxAreaFace(fdFaceRectList);
                    if (maxFaceIndex == -1) {
                        result.setErrCode(65539);
                        return result;
                    } else {
                        extractRect = (Rect)fdFaceRectList.get(maxFaceIndex);
                        result.setFaceRect(extractRect);
                        if (this.isCanFr) {
                            this.isCanFr = false;
                            this.currentFace = new FaceFeature();
                            if (this.previewService != null && !this.isCurrentFr) {
                                final int finalMaxFaceIndex = maxFaceIndex;
                                this.previewService.execute(new Runnable() {
                                    public void run() {
                                        if (IdCardVerifyManager.this.isInit) {
                                            IdCardVerifyManager.this.isCurrentFr = true;
                                            DetectFaceResult frResult = new DetectFaceResult();
                                            frResult.setFaceRect(result.getFaceRect());
                                            int frCode = FaceSDKEngine.getInstance().frExtractFeature(buffer, width, height, 2050, (FaceInfo)fdFaceList.get(finalMaxFaceIndex), IdCardVerifyManager.this.currentFace, false);
                                            if (frCode != 0) {
                                                frResult.setErrCode(frCode);
                                            } else {
                                                IdCardVerifyManager.this.isCanFr = false;
                                                IdCardVerifyManager.this.isCurrentReady = true;
                                                frResult.setErrCode(0);
                                            }

                                            synchronized(IdCardVerifyManager.ID_CARD_VERIFY_LISTENER_LOCK) {
                                                if (IdCardVerifyManager.this.idCardVerifyListener != null) {
                                                    IdCardVerifyManager.this.idCardVerifyListener.onPreviewResult(frResult, data, width, height);
                                                }
                                            }

                                            IdCardVerifyManager.this.isCurrentFr = false;
                                        }

                                    }
                                });
                            }
                        }

                        result.setErrCode(0);
                        return result;
                    }
                }
            }
        }
    }

    public DetectFaceResult inputIdCardData(final byte[] data, final int width, final int height) {
        final DetectFaceResult result = new DetectFaceResult();
        if (data == null) {
            result.setErrCode(65537);
            return result;
        } else if (data.length != width * height * 3 / 2) {
            result.setErrCode(65538);
            return result;
        } else {
            this.reset();
            this.isCanFr = true;
            final byte[] buffer = new byte[data.length];
            System.arraycopy(data, 0, buffer, 0, data.length);
            final List<FaceInfo> fdFaceList = new ArrayList();
            List<Rect> fdFaceRectList = new ArrayList();
            int fdCode = FaceSDKEngine.getInstance().faceDetection(buffer, width, height, 2050, fdFaceList);
            if (fdCode != 0) {
                this.reset();
                result.setErrCode(fdCode);
                return result;
            } else if (fdFaceList.size() <= 0) {
                this.reset();
                result.setErrCode(65539);
                return result;
            } else {
                int maxFaceIndex;
                for(maxFaceIndex = 0; maxFaceIndex < fdFaceList.size(); ++maxFaceIndex) {
                    fdFaceRectList.add(((FaceInfo)fdFaceList.get(maxFaceIndex)).getRect());
                }

                maxFaceIndex = this.findMaxAreaFace(fdFaceRectList);
                if (maxFaceIndex == -1) {
                    this.reset();
                    result.setErrCode(65539);
                    return result;
                } else {
                    Rect extractRect = (Rect)fdFaceRectList.get(maxFaceIndex);
                    result.setFaceRect(extractRect);
                    this.inputFace = new FaceFeature();
                    if (this.idCardService != null && !this.isIdCardFr) {
                        final int finalMaxFaceIndex = maxFaceIndex;
                        this.idCardService.execute(new Runnable() {
                            public void run() {
                                if (IdCardVerifyManager.this.isInit) {
                                    IdCardVerifyManager.this.isIdCardFr = true;
                                    DetectFaceResult frResult = new DetectFaceResult();
                                    frResult.setFaceRect(result.getFaceRect());
                                    int frCode = FaceSDKEngine.getInstance().frExtractFeature(buffer, width, height, 2050, (FaceInfo)fdFaceList.get(finalMaxFaceIndex), IdCardVerifyManager.this.inputFace, true);
                                    if (frCode != 0) {
                                        IdCardVerifyManager.this.reset();
                                        frResult.setErrCode(frCode);
                                    } else {
                                        IdCardVerifyManager.this.isIdCardReady = true;
                                        frResult.setErrCode(0);
                                    }

                                    synchronized(IdCardVerifyManager.ID_CARD_VERIFY_LISTENER_LOCK) {
                                        if (IdCardVerifyManager.this.idCardVerifyListener != null) {
                                            IdCardVerifyManager.this.idCardVerifyListener.onIdCardResult(frResult, data, width, height);
                                        }
                                    }

                                    IdCardVerifyManager.this.isIdCardFr = false;
                                }

                            }
                        });
                    }

                    result.setErrCode(0);
                    return result;
                }
            }
        }
    }

    public CompareResult compareFeature(double threshold) {
        CompareResult compareResult = new CompareResult();
        if (!this.isIdCardReady) {
            this.reset();
            compareResult.setErrCode(65541);
            return compareResult;
        } else if (!this.isCurrentReady) {
            this.reset();
            compareResult.setErrCode(65540);
            return compareResult;
        } else {
            FaceSimilar matching = new FaceSimilar();
            int compareCode = FaceSDKEngine.getInstance().frFacePairMatching(this.inputFace, this.currentFace, matching);
            if (compareCode != 0) {
                this.reset();
                compareResult.setErrCode(compareCode);
                return compareResult;
            } else {
                double score = Double.parseDouble(String.valueOf(matching.getScore()));
                compareResult.setSuccess(score >= threshold);
                compareResult.setResult(score);
                compareResult.setErrCode(0);
                this.reset();
                return compareResult;
            }
        }
    }

    public String getVersion() {
        return FaceSDKEngine.getInstance().getVersion();
    }

    private void reset() {
        this.isCanFr = false;
        this.isCurrentReady = false;
        this.isIdCardReady = false;
    }

    private int findMaxAreaFace(List<Rect> rects) {
        if (rects.size() == 0) {
            return -1;
        } else {
            int index = 0;
            int maxArea = 0;

            for(int i = 0; i < rects.size(); ++i) {
                int area = ((Rect)rects.get(i)).width() * ((Rect)rects.get(i)).height();
                if (area > maxArea) {
                    maxArea = area;
                    index = i;
                }
            }

            return index;
        }
    }
}
