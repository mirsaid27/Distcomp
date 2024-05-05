package com.danilovfa.discussion.util

import kotlinx.datetime.Clock

fun currentTime(): Long {
    return Clock.System.now().toEpochMilliseconds()
}