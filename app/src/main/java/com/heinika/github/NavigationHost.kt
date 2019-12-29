package com.heinika.github

import androidx.fragment.app.Fragment

interface NavigationHost {
    /**
     * Trigger a navigation to the specified fragment, optionally adding a transaction to the back
     * stack to make this navigation reversible.
     */
    fun navigateTo(fragment: Fragment, addToBackStack: Boolean)
}
