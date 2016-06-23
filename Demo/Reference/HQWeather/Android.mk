LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_STATIC_JAVA_LIBRARIES := \
    android-support-v4 
LOCAL_JAVA_LIBRARIES += org.apache.http.legacy

LOCAL_PACKAGE_NAME := HQWeather

# don't apply dalvik preoptimization to ease development
#LOCAL_DEX_PREOPT := false

include $(BUILD_PACKAGE)
