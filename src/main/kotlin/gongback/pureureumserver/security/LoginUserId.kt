package gongback.pureureumserver.security

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class LoginUserId(val required: Boolean = true)
