package com.evanisnor.gradle.semver.support

/**
 * Dependency injection module interface
 */
interface DependencyModule {

    companion object {
        /**
         * Reference map for singleton instances
         */
        private val instances: MutableMap<Any, Any> = mutableMapOf()
    }


    // region Singleton Control

    /**
     * Keep a reference to the instance to prevent it from being instantiated more than once.
     */
    fun <T : Any> singleton(item: T): T {
        if (instances.containsKey(item::class)) {
            @Suppress("UNCHECKED_CAST")
            return instances[item::class] as T
        }

        instances[item::class] = item
        return item
    }

    // endregion

}