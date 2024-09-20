package dev.than0s.aluminium.core

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash")
    data object SignInScreen : Screen("sign_in")
    data object DemoScreen : Screen("demo")
    data object ProfileScreen : Screen("profile")
    data object RegistrationScreen : Screen("registration")
    data object RegistrationRequestsScreen : Screen("registration_requests")
    data object PostUploadScreen : Screen("post_upload_screen")
    data object MyPostsScreen : Screen("my_post_screen")
    data object SignOutScreen : Screen("sign_out")
    data object ForgotPasswordScreen : Screen("forgot_password")
}