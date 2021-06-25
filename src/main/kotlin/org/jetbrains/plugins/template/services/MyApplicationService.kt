package org.jetbrains.plugins.template.services

import com.intellij.openapi.util.Disposer
import org.jetbrains.plugins.template.MyBundle

class MyApplicationService {

    init {
        println(MyBundle.message("applicationService"))
    }

    /**
     * Sets the maximum allowed number of opened projects.
     */
    val MAX_OPEN_PRJ_LIMIT = 10;

    /**
     * the count of opened projects must always be >= 0
     */
    var myOpendProjectCount = 0;

    /**
     * 打开一个项目 记录打开的项目数量
     */
    fun incrProjectCount() {
        validateProjectCount()
        myOpendProjectCount ++
    }

    /**
     * 关闭一个项目 记录打开的项目数量
     */
    fun decrProjectCount() {
        myOpendProjectCount --
        validateProjectCount()
    }

    fun projectLimitExceeded() = myOpendProjectCount > MAX_OPEN_PRJ_LIMIT

    fun getProjectOpenedCount() = myOpendProjectCount

    /**
     * 确保 myOpendProjectCount 永远不会小于0
     */
    private fun validateProjectCount() {
        myOpendProjectCount = Math.max(myOpendProjectCount, 0)
    }
}
