# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.com.immo.winner.utils.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn org.apache.http.**
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
#okgo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.**{*;}

#okrx
-dontwarn com.lzy.okrx.**
-keep class com.lzy.okrx.**{*;}

#okrx2
-dontwarn com.lzy.okrx2.**
-keep class com.lzy.okrx2.**{*;}

#okserver
-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.**{*;}
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*; }

