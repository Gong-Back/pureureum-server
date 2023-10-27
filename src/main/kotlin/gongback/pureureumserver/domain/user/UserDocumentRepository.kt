package gongback.pureureumserver.domain.user

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface UserDocumentRepository : ElasticsearchRepository<UserDocument, Long>
