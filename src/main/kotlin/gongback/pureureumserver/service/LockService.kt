package gongback.pureureumserver.service

interface LockService {
    fun lock(key: String): Boolean

    fun unlock(key: String)
}
