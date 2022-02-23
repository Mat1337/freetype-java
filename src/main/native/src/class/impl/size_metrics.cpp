#include "size_metrics.h"

jfieldID sizemetrics_pointer_id;

JNIEXPORT jint JNICALL GetSizeAscender(JNIEnv *env, jclass object) {
	jlong pointer = env->GetLongField(object, sizemetrics_pointer_id);
    return ((FT_Size_Metrics*)pointer)->ascender;
}

JNIEXPORT jint JNICALL GetSizeDescender(JNIEnv *env, jclass object) {
	jlong pointer = env->GetLongField(object, sizemetrics_pointer_id);
	return ((FT_Size_Metrics*)pointer)->descender;
}

JNIEXPORT jint JNICALL GetSizeHeight(JNIEnv *env, jclass object) {
	jlong pointer = env->GetLongField(object, sizemetrics_pointer_id);
	return ((FT_Size_Metrics*)pointer)->height;
}

JNIEXPORT jint JNICALL GetSizeMaxAdvance(JNIEnv *env, jclass object) {
	jlong pointer = env->GetLongField(object, sizemetrics_pointer_id);
	return ((FT_Size_Metrics*)pointer)->max_advance;
}

JNIEXPORT jint JNICALL GetSizeXppm(JNIEnv *env, jclass object) {
	jlong pointer = env->GetLongField(object, sizemetrics_pointer_id);
	return ((FT_Size_Metrics*)pointer)->x_ppem;
}

JNIEXPORT jint JNICALL GetSizeXScale(JNIEnv *env, jclass object) {
	jlong pointer = env->GetLongField(object, sizemetrics_pointer_id);
	return ((FT_Size_Metrics*)pointer)->x_scale;
}

JNIEXPORT jint JNICALL GetSizeYPpm(JNIEnv *env, jclass object) {
	jlong pointer = env->GetLongField(object, sizemetrics_pointer_id);
	return ((FT_Size_Metrics*)pointer)->y_ppem;
}

JNIEXPORT jint JNICALL GetSizeYScale(JNIEnv *env, jclass object) {
	jlong pointer = env->GetLongField(object, sizemetrics_pointer_id);
	return ((FT_Size_Metrics*)pointer)->y_scale;
}

SizeMetrics::SizeMetrics()
    : MemoryClass::MemoryClass("me/mat/freetype/font/size/SizeMetrics")
{
    sizemetrics_pointer_id = m_PointerID;

    this->Register(
        "getAscender",
        "()I",
        (void*)&GetSizeAscender
    );
    this->Register(
        "getDescender",
        "()I",
        (void*)&GetSizeDescender
    );
    this->Register(
        "getHeight",
        "()I",
        (void*)&GetSizeHeight
    );
    this->Register(
        "getMaxAdvance",
        "()I",
         (void*)&GetSizeMaxAdvance
    );
    this->Register(
        "getXppem",
        "()I",
        (void*)&GetSizeXppm
    );
    this->Register(
        "getYppem",
        "()I",
        (void*)&GetSizeYPpm
    );
    this->Register(
        "getXScale",
        "()I",
        (void*)&GetSizeXScale
    );
    this->Register(
        "getYScale",
        "()I",
        (void*)&GetSizeYScale
    );
}