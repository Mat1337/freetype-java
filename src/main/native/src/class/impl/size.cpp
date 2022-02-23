#include "size.h"

jfieldID size_pointer_id;

JNIEXPORT jlong JNICALL GetSizeMetrics(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, size_pointer_id);
	return (jlong)&(((FT_Size)pointer)->metrics);
}

Size::Size()
    : MemoryClass::MemoryClass("me/mat/freetype/font/size/Size")
{
    size_pointer_id = m_PointerID;

    this->Register(
        "getSizeMetricsPointer",
        "()J",
        (void*)&GetSizeMetrics
    );
}