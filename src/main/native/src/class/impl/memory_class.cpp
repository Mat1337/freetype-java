#include "memory_class.h"

JNIEXPORT jobject JNICALL CreateBuffer(JNIEnv *env, jclass object, jint size) {
	return env->NewDirectByteBuffer((char*)malloc(size), size);
}

JNIEXPORT void JNICALL FillBuffer(JNIEnv *env, jclass object, jbyteArray bytes, jobject buffer, jint length) {
	unsigned char* dst = (unsigned char*)(buffer ? env->GetDirectBufferAddress(buffer) : 0);
	char* src = (char*)env->GetPrimitiveArrayCritical(bytes, 0);

	memcpy(dst, src, length);

	env->ReleasePrimitiveArrayCritical(bytes, src, 0);
}

JNIEXPORT void JNICALL DeleteBuffer(JNIEnv *env, jclass object, jobject buffer) {
	char* b = (char*)(buffer ? env->GetDirectBufferAddress(buffer) : 0);
	free(b);
}

MemoryClass::MemoryClass()
    : Class::Class("me/mat/freetype/util/MemoryClass")
{
    this->m_PointerID = vm.env->GetFieldID(m_Class, "pointer", "J");

    this->Register(
        "createBuffer",
        "(I)Ljava/nio/ByteBuffer;",
        (void*)&CreateBuffer
    );
    this->Register(
        "fillBuffer",
        "([BLjava/nio/ByteBuffer;I)V",
        (void*)&FillBuffer
    );
    this->Register(
        "deleteBuffer",
        "(Ljava/nio/ByteBuffer;)V",
        (void*)&DeleteBuffer
    );
}

MemoryClass::MemoryClass(const char* name)
    : Class::Class("me/mat/freetype/util/MemoryClass")
{
    this->m_PointerID = vm.env->GetFieldID(m_Class, "pointer", "J");

    this->Register(
        "createBuffer",
        "(I)Ljava/nio/ByteBuffer;",
        (void*)&CreateBuffer
    );
    this->Register(
        "fillBuffer",
        "([BLjava/nio/ByteBuffer;I)V",
        (void*)&FillBuffer
    );
    this->Register(
        "deleteBuffer",
        "(Ljava/nio/ByteBuffer;)V",
        (void*)&DeleteBuffer
    );

    this->m_Name = name;
    this->m_Class = vm.env->FindClass(this->m_Name);
}