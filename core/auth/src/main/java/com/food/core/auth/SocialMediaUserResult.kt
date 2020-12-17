package com.food.core.auth

/**
 * Created by mikekojansow on 02/08/20.
 * Senior Android Developer
 */
data class SocialMediaUserResult(
    val name: String,
    val email: String,
    val socialMediaId: String,
    val metaData: String,
    val firstname: String,
    val lastname: String?
)