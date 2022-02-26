#pragma once

#include "memory_class.h"

class FreeTypeHandle : public MemoryClass
{
public:
    FreeTypeHandle();
};

extern FreeTypeHandle* freetype_handle;