#include "char_map.h"

jfieldID charmap_pointer_id;

JNIEXPORT jint JNICALL GetCharMapIndex(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, charmap_pointer_id);
    return FT_Get_Charmap_Index((FT_CharMap)pointer);
}

CharMap::CharMap()
    : MemoryClass::MemoryClass("me/mat/freetype/font/CharMap")
{
    charmap_pointer_id = this->m_PointerID;

    this->Register(
        "getCharMapIndex",
        "()I",
        (void*)&GetCharMapIndex
    );
}