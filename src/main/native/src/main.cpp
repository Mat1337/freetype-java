#include <iostream>

#include "class/impl/free_type.h"
#include "class/impl/memory_class.h"
#include "class/impl/bitmap.h"
#include "class/impl/char_map.h"
#include "class/impl/freetype_handle.h"
#include "class/impl/bbox.h"
#include "class/impl/face.h"
#include "class/impl/glyph_slot.h"
#include "class/impl/glyph_metrics.h"
#include "class/impl/size.h"
#include "class/impl/size_metrics.h"
vm_t vm;

FreeType* 			free_type;
MemoryClass* 		memory_class;
Bitmap* 			bitmap;
CharMap* 			charmap;
FreeTypeHandle* 	freetype_handle;
BBox* 				b_box;
Face* 				face;
GlyphSlot* 			glyphSlot;
GlyphMetrics* 		glyph_metrics;
Size* 				size;
SizeMetrics*	 	size_metrics;

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
    jfieldID majorID = vm.env->GetStaticFieldID(freeTypeCls, "major", "I");
    jfieldID minorID = vm.env->GetStaticFieldID(freeTypeCls, "minor", "I");
    jfieldID patchID = vm.env->GetStaticFieldID(freeTypeCls, "patch", "I");

    FT_Library lib = NULL;
	vm.env->SetStaticLongField(freeTypeCls, freeTypeHandleID, (jlong)(FT_Init_FreeType(&lib) ? 0 : lib));
    
	int major, minor, patch;
	FT_Library_Version(lib, &major, &minor, &patch);
	vm.env->SetStaticLongField(freeTypeCls, majorID, major);
	vm.env->SetStaticLongField(freeTypeCls, minorID, minor);
	vm.env->SetStaticLongField(freeTypeCls, patchID, patch);

	free_type = new FreeType((jlong)lib);
	memory_class = new MemoryClass();
	bitmap = new Bitmap();
	charmap = new CharMap();
	freetype_handle = new FreeTypeHandle();
	b_box = new BBox();
	face = new Face();
	glyphSlot = new GlyphSlot();
	glyph_metrics = new GlyphMetrics();
	size = new Size();
	size_metrics = new SizeMetrics();

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