package dev.than0s.aluminium

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash")
    data object SignInScreen : Screen("sign_in")
    data object SignUpScreen : Screen("sign_up")
    data object DemoScreen : Screen("demo")
    data object ProfileScreen : Screen("profile")
}