package com.lithium.truepost.data.repository

import com.lithium.truepost.data.local.CourseProgressDao
import com.lithium.truepost.data.local.CourseProgressEntity
import com.lithium.truepost.data.model.CourseData
import com.lithium.truepost.data.raw.AllCourses

class CourseRepository(
    private val dao: CourseProgressDao,
) {
    private val courses = AllCourses

    suspend fun getCourses(): List<CourseData> {
        val progressList = dao.getAllProgress()
        val baseCourses = courses
        return baseCourses.map { course ->
            val completed = progressList.any { it.courseId == course.id && it.completed }
            course.copy(completed = completed)
        }
    }

    suspend fun markCourseCompleted(courseId: Int) {
        dao.insertOrUpdate(CourseProgressEntity(courseId = courseId, completed = true))
    }
}