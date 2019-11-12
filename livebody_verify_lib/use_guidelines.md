#如何使用livebody_verify_lib库（通过活体检测验证身份）

## 1.在需要引入此库的模块工程中添加如下引用：
```groovy
    compile 'com.android.support.constraint:constraint-layout:1.0.0-alpha7'

    compile "com.android.support:appcompat-v7:${rootProject.ext.support}"

    compile 'com.squareup.okhttp3:logging-interceptor:3.3.0'
    //okhttputils  zhy
    compile 'com.zhy:okhttputils:2.6.2'
    //    gson
    compile 'com.google.code.gson:gson:2.6.2'
    //进度条
    compile 'com.dalong:rotaryballview:1.0.0'


    //选择图片的库工程
    compile project(':picture_library')

    //第三方提供的aar文件(需要拷贝到{添加了此aar库的模块名称}/libs文件夹中)
    compile(name: 'eididentity_sdk_android_1.0.0', ext: 'aar')
    compile(name: 'livebodylibrary-release', ext: 'aar')

    //用此模块打包生成的aar库(需要拷贝到{添加了此aar库的模块名称}/libs文件夹中)
    compile(name: 'livebody_verify_lib-release', ext: 'aar')
```

## 2.在顶层工程中添加对aar库目录的引用：
```groovy
    allprojects {
        repositories {
            flatDir{
                dirs '../{添加了此aar库的模块名称}/libs'
            }

            google()
            jcenter()


        }
    }
```

## 3.在模块工程中添加libs文件夹配置
```groovy
    sourceSets {
        main {
            jniLibs.srcDirs = ["libs"]
        }
    }

```

## 4.在模块工程的AndroidManifest.xml文件中的application节点下增加如下代码
```xml
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true"
            tools:replace="android:authorities">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths" />
        </provider>
```
### 其中provider_path.xml文件中包含类似如下代码以适配android7.0(可以增减可以使别的应用程序读写文件的目录条目)
```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- /storage/emulated/0/Download/${applicationId}/.beta/apk-->
    <external-path name="beta_external_path" path="Download/"/>
    <!--/storage/emulated/0/Android/data/${applicationId}/files/apk/-->
    <external-path name="beta_external_files_path" path="Android/data/"/>
    <external-path name="sdcard_root" path="/"/>
    <external-path name="sdcard_reader_temp" path="TbsReaderTemp/"/>


    <files-path name="jmessage_images" path="photo/"/>
    <files-path name="jmessage_videos" path="photo/"/>
    <files-path name="jmessage_voice" path="voice/"/>

    <external-path name="external_picture_path" path="sdcard/signed_resident_client_new/pictures/"/>
    <external-path name="external_voice_path" path="sdcard/signed_resident_client_new/voice/"/>
    <external-path name="external_file_path" path="sdcard/signed_resident_client_new/recvFiles/"/>

</paths>
```

## 5.在自定义应用程序类Applicaiton中onCreate函数中添加如下代码
```java
        FaceDetector.init(this);
```

## 6.在需要调用此aar库中人脸验证身份功能的类种添加如下代码
```java
    int REQUEST_CODE = 100;

    private void gotoVerify(){
    // 配置服务器地址========================
            //人脸匹配基地址
            Version.setBaseUrl("...");
            //通过人脸照片和身份证照片比对来添加人脸档案
            Version.setCreateFeatureUrl("...");
            //人脸比对
            Version.setCompareUrl("...");
            //推送活体验证成功后的居民信息给各个公卫系统的基地址
            Version.setPushFaceDataBaseUrl("...");
            //推送活体验证成功后的居民信息给各个公卫系统的具体地址
            Version.setPushFaceDataConcrete("...");
    //==================================

        Intent intent = new Intent(LoginActivity.this, HeadCollectActivity.class);
        //身份证号码
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_NUMBER_KEY, "340823198804211229");
        //组织机构编码
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ORG_CODE_KEY, "56EC7921-9F8B-46DC-AE2A-CB72613F9638");
        //用户姓名
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_SUBMIT_USER_KEY, "吴春秀");

        startActivityForResult(intent, REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){
            if (resultCode==RESULT_OK){
                Log.i("verify", "验证身份成功");
            } else {
                Log.i("verify", "验证身份失败");
            }
        }
    }

```