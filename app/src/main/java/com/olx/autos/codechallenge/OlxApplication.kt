package com.olx.autos.codechallenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Hilt annotation to trigger a generation of Dagger Hilt
 */
@HiltAndroidApp
class OlxApplication:Application()
