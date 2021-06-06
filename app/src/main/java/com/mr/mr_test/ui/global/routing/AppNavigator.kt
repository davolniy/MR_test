package com.mr.mr_test.ui.global.routing

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mr.mr_test.R

abstract class AppNavigator(
    activity: FragmentActivity,
    @IdRes containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager
) : AppNavigator(activity, containerId, fragmentManager)