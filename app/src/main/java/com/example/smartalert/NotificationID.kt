package com.example.smartalert

import java.util.concurrent.atomic.AtomicInteger

object NotificationID {
    private val c: AtomicInteger = AtomicInteger(0)
    val id: Int
        get() = c.incrementAndGet()
}