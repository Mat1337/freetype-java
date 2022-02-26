#include "glyph_slot.h"

jfieldID glyphslot_pointer_id;

JNIEXPORT jlong JNICALL GetBitmapPointer(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphslot_pointer_id);
	return (jlong)(&((FT_GlyphSlot)pointer)->bitmap);
}

JNIEXPORT jlong JNICALL GetLinearHorizontalAdvance(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphslot_pointer_id);
	return ((FT_GlyphSlot)pointer)->linearHoriAdvance;
}

JNIEXPORT jlong JNICALL GetLinearVerticalAdvance(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphslot_pointer_id);
	return ((FT_GlyphSlot)pointer)->linearVertAdvance;
}

JNIEXPORT jobject JNICALL GetAdvance(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphslot_pointer_id);
	FT_Vector vector = ((FT_GlyphSlot)pointer)->advance;

	jclass cls = env->FindClass("me/mat/freetype/font/glyph/Advance");
	jmethodID methodID = env->GetMethodID(cls, "<init>", "(II)V");
	return env->NewObject(cls, methodID, (jlong)vector.x, (jlong)vector.y);
}

JNIEXPORT jint JNICALL GetFormat(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphslot_pointer_id);
	return ((FT_GlyphSlot)pointer)->format;
}

JNIEXPORT jint JNICALL GetBitmapLeft(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphslot_pointer_id);
	return ((FT_GlyphSlot)pointer)->bitmap_left;
}

JNIEXPORT jint JNICALL GetBitmapTop(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphslot_pointer_id);
	return ((FT_GlyphSlot)pointer)->bitmap_top;
}

JNIEXPORT jlong JNICALL GetGlyphSlotMetrics(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, glyphslot_pointer_id);
	return (jlong)&((FT_GlyphSlot)pointer)->metrics;
}

GlyphSlot::GlyphSlot()
    : MemoryClass::MemoryClass("me/mat/freetype/font/glyph/GlyphSlot")
{
    glyphslot_pointer_id = m_PointerID;
    
    this->Register(
        "getBitmapPointer",
        "()J",
        (void*)&GetBitmapPointer
    );
    this->Register(
        "getLinearHorizontalAdvance",
        "()J",
        (void*)&GetLinearHorizontalAdvance
    );
    this->Register(
        "getLinearVerticalAdvance",
        "()J",
        (void*)&GetLinearVerticalAdvance
    );
    this->Register(
        "getAdvance",
        "()Lme/mat/freetype/font/glyph/Advance;",
        (void*)&GetAdvance
    );
    this->Register(
        "getFormat",
        "()I",
        (void*)&GetFormat
    );
    this->Register(
        "getBitmapLeft",
        "()I",
        (void*)&GetBitmapLeft
    );
    this->Register(
        "getBitmapTop",
        "()I",
        (void*)&GetBitmapTop
    );
    this->Register(
        "getMetricsPointer",
        "()J",
        (void*)GetGlyphSlotMetrics
    );
}