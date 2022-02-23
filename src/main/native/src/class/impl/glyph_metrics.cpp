#include "glyph_metrics.h"

jfieldID glyphmetrics_pointer_id;

JNIEXPORT jint JNICALL GetGlyphWidth(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphmetrics_pointer_id);
	return ((FT_Glyph_Metrics*)pointer)->width;
}

JNIEXPORT jint JNICALL GetGlyphHeight(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphmetrics_pointer_id);
	return ((FT_Glyph_Metrics*)pointer)->height;
}

JNIEXPORT jint JNICALL GetHorizontalAdvance(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphmetrics_pointer_id);
	return ((FT_Glyph_Metrics*)pointer)->horiAdvance;
}

JNIEXPORT jint JNICALL GetVerticalAdvance(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphmetrics_pointer_id);
	return ((FT_Glyph_Metrics*)pointer)->vertAdvance;
}

JNIEXPORT jint JNICALL GetHorizontalBearingX(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphmetrics_pointer_id);
	return ((FT_Glyph_Metrics*)pointer)->horiBearingX;
}

JNIEXPORT jint JNICALL GetHorizontalBearingY(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphmetrics_pointer_id);
	return ((FT_Glyph_Metrics*)pointer)->horiBearingY;
}

JNIEXPORT jint JNICALL GetVerticalBearingX(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphmetrics_pointer_id);
	return ((FT_Glyph_Metrics*)pointer)->vertBearingX;
}

JNIEXPORT jint JNICALL GetVerticalBearingY(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphmetrics_pointer_id);
	return ((FT_Glyph_Metrics*)pointer)->vertBearingY;
}

GlyphMetrics::GlyphMetrics()
    : MemoryClass::MemoryClass("me/mat/freetype/font/glyph/GlyphMetrics")
{
    glyphmetrics_pointer_id = m_PointerID;

    this->Register(
        "getWidth",
        "()I",
        (void*)&GetGlyphWidth
    );
    this->Register(
        "getHeight",
        "()I",
        (void*)&GetGlyphHeight
    );
    this->Register(
        "getHorizontalAdvance",
        "()I",
        (void*)&GetHorizontalAdvance
    );
    this->Register(
        "getVerticalAdvance",
        "()I",
        (void*)&GetVerticalAdvance
    );
    this->Register(
        "getHorizontalBearingX",
        "()I",
        (void*)&GetHorizontalBearingX
    );
    this->Register(
        "getHorizontalBearingY",
        "()I",
        (void*)&GetHorizontalBearingY
    );
    this->Register(
        "getVerticalBearingX",
        "()I",
        (void*)&GetVerticalBearingX
    );
    this->Register(
        "getVerticalBearingY",
        "()I",
        (void*)&GetVerticalBearingY
    );
}