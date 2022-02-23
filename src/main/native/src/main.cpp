#include <iostream>

#include "class/impl/free_type.h"
#include "class/impl/memory_class.h"
vm_t vm;

FreeType* free_type;
MemoryClass* memory_class;

jint JNIEXPORT JNI_OnLoad(JavaVM* jvm, void* reserved)
{
    jint res = jvm->GetEnv((void **)&vm.env, JNI_VERSION_10);
	if (res == JNI_EDETACHED)
		res = jvm->AttachCurrentThread((void **)&vm.env, nullptr);

	if (res != JNI_OK) {
		std::cout << "[ERR]> Failed to attach to thread" << std::endl;
		return JNI_VERSION_10;
	}

    jclass freeTypeCls = vm.env->FindClass("me/mat/freetype/FreeType");
    jfieldID freeTypeHandleID = vm.env->GetStaticFieldID(freeTypeCls, "handle", "J");

    FT_Library lib = NULL;
	vm.env->SetStaticLongField(freeTypeCls, freeTypeHandleID, (jlong)(FT_Init_FreeType(&lib) ? 0 : lib));
    
	free_type = new FreeType((jlong)lib);
	memory_class = new MemoryClass();

	for(auto& cls : vm.cp)
	{
		if (!cls->RegisterNatives())
		{
			printf("Failed to register natives for '%s'\n", cls->GetName());
			break;
		}
	}

    return JNI_VERSION_10;
}