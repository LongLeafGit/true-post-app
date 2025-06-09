package com.lithium.truepost.data.repository

import com.lithium.truepost.data.local.CourseProgressDao
import com.lithium.truepost.data.local.CourseProgressEntity
import com.lithium.truepost.data.model.CourseData
import com.lithium.truepost.data.raw.AllCourses

class CourseRepository(
    private val dao: CourseProgressDao,
) {
    private val baseCourses = AllCourses

    suspend fun getCourses(): List<CourseData> {
        val progressList = dao.getAllProgress()
        val progressMap = progressList.associateBy { it.courseId }

        return baseCourses.map { course ->
            course.copy(
                completed = progressMap[course.id]?.completed ?: false
            )
        }
    }

    suspend fun markCourseCompleted(courseId: String) {
        dao.insertOrUpdate(CourseProgressEntity(courseId, completed = true))
    }
}