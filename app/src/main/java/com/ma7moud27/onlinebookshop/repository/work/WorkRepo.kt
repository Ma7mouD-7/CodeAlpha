package com.ma7moud27.onlinebookshop.repository.work

import com.ma7moud27.onlinebookshop.model.work.Work

interface WorkRepo {
    suspend fun getWork(workID:String): Work
}
