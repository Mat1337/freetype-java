#include "freetype_handle.h"

jfieldID freetype_pointer_id;

JNIEXPORT jlong JNICALL CreateFaceFromMemory(JNIEnv* env, jobject object, jobject buffer, jint length, jlong faceIndex)
{
    jlong pointer = env->GetLongField(object, freetype_pointer_id);
    char* data = (char*)(buffer ? env->GetDirectBufferAddress(buffer) : 0);
	FT_Face face = NULL;
	if (FT_New_Memory_Face((FT_Library)pointer, (const FT_Byte*)data, (FT_Long)length, (FT_Long)faceIndex, &face))
		return 0;
	else return (jlong)face;
}

FreeTypeHandle::FreeTypeHandle()
    : MemoryClass::MemoryClass("me/mat/freetype/FreeTypeHandle")
{
    freetype_pointer_id = m_PointerID;
    
    this->Register(
        "createFaceFromMemory",
        "(Ljava/nio/ByteBuffer;II)J",
        (void*)&CreateFaceFromMemory
    );
}