#include "bbox.h"

jfieldID bbox_pointer_id;

JNIEXPORT jint JNICALL GetBoxXMin(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bbox_pointer_id);
    return ((FT_BBox*)pointer)->xMin;
}

JNIEXPORT jint JNICALL GetBoxYMin(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bbox_pointer_id);
    return ((FT_BBox*)pointer)->yMin;
}

JNIEXPORT jint JNICALL GetBoxXMax(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bbox_pointer_id);
    return ((FT_BBox*)pointer)->xMax;
}

JNIEXPORT jint JNICALL GetBoxYMax(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bbox_pointer_id);
    return ((FT_BBox*)pointer)->yMax;
}

BBox::BBox()
    : MemoryClass::MemoryClass("me/mat/freetype/font/face/BBox")
{
    bbox_pointer_id = m_PointerID;

    this->Register(
        "xMin",
        "()J",
        (void*)&GetBoxXMin
    );
    this->Register(
        "yMin",
        "()J",
        (void*)&GetBoxYMin
    );
    this->Register(
        "xMax",
        "()J",
        (void*)&GetBoxXMax
    );
    this->Register(
        "yMax",
        "()J",
        (void*)&GetBoxYMax
    );
}