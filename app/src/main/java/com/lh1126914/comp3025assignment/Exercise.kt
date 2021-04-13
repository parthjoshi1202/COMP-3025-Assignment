package com.lh1126914.comp3025assignment

//attributes for exercise collection in firebase
//also serves as model
data class Exercise (
        var exercise_id: String?=null,
        var exercise_name: String?=null,
        var repetitions: String?=null,
        var instructions: String?=null,
        var notes: String?=null,
        var media: String?=null
)