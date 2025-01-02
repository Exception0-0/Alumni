package dev.than0s.aluminium.features.profile.presentation.screens.post

sealed class PostsEvents {
    data object LoadPosts : PostsEvents()
}