#pragma once

#include "../class.h"

class MemoryClass : public Class
{
public:
    MemoryClass();
    MemoryClass(const char* name);
protected:
    jfieldID m_PointerID;
};

extern MemoryClass* memory_class;