#pragma once

#include "memory_class.h"

class SizeMetrics : public MemoryClass
{
public:
    SizeMetrics();
};

extern SizeMetrics* size_metrics;