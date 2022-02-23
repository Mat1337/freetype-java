#include "free_type.h"

jlong library_ptr;

JNIEXPORT jboolean JNICALL Free(JNIEnv* env, jobject object)
{
    return FT_Done_FreeType((FT_Library)library_ptr);
}

FreeType::FreeType(jlong ptr) 
    : Class::Class("me/mat/freetype/FreeType")
{
    library_ptr = ptr;

    this->Register(
        "free",
        "()V",
        (void*)&Free
    );
}