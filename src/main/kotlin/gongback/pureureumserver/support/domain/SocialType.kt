package gongback.pureureumserver.support.domain

enum class SocialType(
    private val description: String,
) {
    KAKAO("카카오 로그인"),
    GOOGLE("구글 로그인"),
    NAVER("네이버 로그인"),
}
